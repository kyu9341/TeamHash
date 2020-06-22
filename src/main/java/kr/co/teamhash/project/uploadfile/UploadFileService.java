package kr.co.teamhash.project.uploadfile;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.entity.UploadFile;
import kr.co.teamhash.domain.repository.UploadFileRepository;
import kr.co.teamhash.project.uploadfile.exception.FileStorageException;
import kr.co.teamhash.project.uploadfile.exception.MyFileNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UploadFileService{
    private final UploadFileRepository uploadFileRepository;


    // DB에 파일 저장
    // 어떤 프로젝트에서 업로드 됐는지와
    // 어떤 사람이 업로드 했는지의 정보도 함께 저장한다.
    public void storeFile(MultipartFile file, Long projectId, Account uploader){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try{
            if(fileName.contains("..")){
                throw new FileStorageException("invalid path : "+fileName );

            }

            UploadFile uploadFile = new UploadFile(projectId, uploader, fileName,
                                         file.getContentType(), file.getBytes());
            

            uploadFileRepository.save(uploadFile);
        } catch (IOException ex){
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public void deleteFile(Long fileId){

        uploadFileRepository.deleteById(fileId);

    }

    // 템플릿에 파일 리스트를 출력해주기 위한 매서드
    // 해당 프로젝트의 리스트만 출력해야 하기 때문에 projectId를 매개변수로 받는다.
    public List<UploadFile> getFileList(Long projectId){
       return uploadFileRepository.findByProjectId(projectId);

    }

    public UploadFile getFile(Long fileId) {
        return uploadFileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }


}
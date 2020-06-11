import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.co.teamhash.domain.entity.UploadFile;
import kr.co.teamhash.domain.repository.UploadFileRepository;
import kr.co.teamhash.project.uploadfile.exception.FileStorageException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UploadFileService{
    private final UploadFileRepository uploadFileRepository;

    public UploadFile storeFile(MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try{
            if(fileName.contains("..")){
                throw new FileStorageException("invalid path : "+fileName );

            }

            UploadFile uploadFile = new UploadFile(fileName,file.getContentType(),file.getBytes());
        }
    }
}
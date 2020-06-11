package kr.co.teamhash.project.uploadfile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


import kr.co.teamhash.account.CurrentUser;
import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.entity.UploadFile;
import kr.co.teamhash.project.uploadfile.exception.StorageFileNotFoundException;



@Slf4j
@Controller
@RequiredArgsConstructor
public class UploadFilecontroller {
    
    private final UploadFileService uploadFileService;


    // uploadFile main tamplete
    // 현재는 탬플릿만 반환하고 있으며 이후 해당 프로젝트에서 업로드한 파일의 리스트를
    // a태그에 담아 출력하게 수정할 것
   @GetMapping("/project/{nickname}/{title}/cloud")
   public String cloud(@PathVariable("nickname") String nickname, @PathVariable("title") String title,
                    Model model,  @CurrentUser Account account) throws IOException {
        
        //String[] files 
        
        // model.addAttribute("files", uploadFileService.loadAll().map(
        //     path -> MvcUriComponentsBuilder.fromMethodName(UploadFilecontroller.class,
        //             "serveFile", path.getFileName().toString()).build().toUri().toString())
        //     .collect(Collectors.toList()));

        return "project/cloud";
   }




   // fileupload form에 파일을 올린 뒤 전송하면 해당 위치에서 받아서 DB에 저장
   // 이후에 projectId와 저장하는 사람의 정보도 함께 저장할 것
	@PostMapping("/project/{nickname}/{title}/cloud/upload")
    public String handleFileUpload(@PathVariable("nickname") String nickname, @PathVariable("title") String title,
                @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        uploadFileService.storeFile(file);
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

       return "redirect:/project/"+nickname+"/"+title+"/cloud";
	}


    // 파일 다운로드 맵핑
    // 아래의 주소에 get요청을 보내면 파일 다운로드가 제공됨
    // 이후 파일의 리스트를 a태그에 담아 아래의 url을 담게 할 것
    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") String fileId) {
        // Load file from database
        
        UploadFile uploadFile = uploadFileService.getFile(Long.parseLong(fileId));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(uploadFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + uploadFile.getFileName() + "\"")
                .body(new ByteArrayResource(uploadFile.getData()));
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}


}
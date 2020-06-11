package kr.co.teamhash.project.uploadfile;


import lombok.*;

@Getter @Setter @AllArgsConstructor
public class UploadFileResponse {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
}
package kr.co.teamhash.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

// 프로젝트 관리 DB
@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class UploadFile{

    
    @Id @GeneratedValue
    private Long id;

    private String fileName;
    
    private String fileType;

    @Lob
    private byte[] data;

    public UploadFile(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }
}
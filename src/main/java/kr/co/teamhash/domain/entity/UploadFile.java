package kr.co.teamhash.domain.entity;

import lombok.*;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// 프로젝트 관리 DB
@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UploadFile{

    
    @Id @GeneratedValue
    private Long id;

    private Long projectId;

    @ManyToOne
    @JoinColumn(name ="account_id")
    private Account uploader;

    private String fileName;
    
    private String fileType;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime uploadDate;

    @Lob
    private byte[] data;

    public UploadFile(Long projectId, Account uploader, String fileName, String fileType, byte[] data) {
        this.projectId = projectId;
        this.uploader = uploader;
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
        
    }
}
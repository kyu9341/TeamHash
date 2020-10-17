
package kr.co.teamhash.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.teamhash.domain.entity.UploadFile;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {
    List<UploadFile> findByProjectId(Long projectId);
}
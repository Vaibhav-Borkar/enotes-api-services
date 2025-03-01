package com.enotes.notes;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Notes, Integer> {

	Page<Notes > findByCreatedBy(Integer userId,Pageable pageble);

	List<Notes> findByCreatedByAndIsDeletedTrue(Integer userId);

	Page<Notes> findByCreatedByAndIsDeletedFalse(Integer userId, Pageable pageable);

	List<Notes> findAllByIsDeletedAndDeletedOnBefore(boolean b, LocalDateTime cutOffDate);
	
	List<Notes> findByIsDeletedFalse ();

	List<Notes> findByCreatedByAndIsDeletedFalse(Integer userId);

	
}

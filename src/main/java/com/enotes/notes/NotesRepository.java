package com.enotes.notes;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotesRepository extends JpaRepository<Notes, Integer> {

	Page<Notes > findByCreatedBy(Integer userId,Pageable pageble);

	List<Notes> findByCreatedByAndIsDeletedTrue(Integer userId);

	Page<Notes> findByCreatedByAndIsDeletedFalse(Integer userId, Pageable pageable);

	List<Notes> findAllByIsDeletedAndDeletedOnBefore(boolean b, LocalDateTime cutOffDate);
	
	List<Notes> findByIsDeletedFalse ();

	List<Notes> findByCreatedByAndIsDeletedFalse(Integer userId);

//	@Query("select n from Notes n where lower(n.title) like lower(concat('%',:keyword,'%'))"
//			+ "lower(n.description) like lower(concat('%',:keyword,'%'))"
//			+ "lower(n.category.name) like lower(concat('%',:keyword,'%'))"
//			+ "and isDeleted=false"
//			+ "and createdBy=:userId")
//	Page<Notes> searchNotes(String keyword,Integer userId,Pageable pageable);
	
	@Query("SELECT n FROM Notes n WHERE " +
		       "(LOWER(n.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
		       "OR LOWER(n.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
		       "OR LOWER(n.category.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
		       "AND n.isDeleted = false " +
		       "AND n.createdBy = :userId")
		Page<Notes> searchNotes(@Param("keyword") String keyword,
		                        @Param("userId") Integer userId,
		                        Pageable pageable);

}

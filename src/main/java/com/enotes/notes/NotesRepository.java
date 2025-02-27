package com.enotes.notes;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Notes, Integer> {

	Page<Notes > findByCreatedBy(Integer userId,Pageable pageble);

}

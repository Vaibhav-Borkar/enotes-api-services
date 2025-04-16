package com.enotes.favourite;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouriteNoteRepository extends JpaRepository<FavouriteNote,Integer>{

	List <FavouriteNote> findByUserId(Integer userId);

	

}

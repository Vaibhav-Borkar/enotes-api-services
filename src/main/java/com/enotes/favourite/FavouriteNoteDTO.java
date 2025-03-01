package com.enotes.favourite;

import com.enotes.notes.NotesDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavouriteNoteDTO {

	private Integer id;

	private NotesDTO note;

	private Integer userId;
}

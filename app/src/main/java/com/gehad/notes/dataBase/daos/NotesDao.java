package com.gehad.notes.dataBase.daos;

import androidx.room.*;
import com.gehad.notes.dataBase.model.Note;

import java.util.List;

@Dao
public interface NotesDao {

    @Insert
    void insertNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Query("delete from note where id =:id")
    void deleteNoteById(int id);

    @Update
    void updateNote(Note note);

    @Query("select * from note")
    List<Note> getAllNotes();

    @Query("select * from note where description like :word")
    List<Note> searchNotesByDescription(String word);

    @Query("select * from note where id like :id")
    Note searchNotesById(int id);
}
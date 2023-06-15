package com.example.todolist.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.todolist.Note;

import java.util.List;

@Dao // Data access object
public interface NotesDao {

    @Query("SELECT * FROM notes") // Вытащить всё из таблицы Notes - мы пишем тут на языке SQL
    List<Note> getNotes(); // Лучше не указывать какую-либо конкретную коллекцию

    @Insert // Можно написать стратегию при конфликтной ситуации
    void add(Note note);

    @Query("DELETE FROM notes WHERE id = :id")
    void remove(int id);

}

package com.example.todolist.database;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.todolist.Note;
import com.example.todolist.interfaces.NotesDao;

@Database(entities = {Note.class}, version = 1)
// Entities - классы которые будут добавляется в базу данных, и версия базы данных (её нужно поднимать при каждом изменении БД)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance = null;
    private static final String DB_NAME = "notes.db";

    public static NoteDatabase getInstance(Application application) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            application,
                            NoteDatabase.class,
                            DB_NAME
                    ).build();
        }
        return instance;
    }

    public abstract NotesDao notesDao();

}

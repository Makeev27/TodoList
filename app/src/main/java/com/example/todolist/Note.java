package com.example.todolist;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")  // Переводим класс Note в таблицу для базы данных Room
public class Note {
    @PrimaryKey(autoGenerate = true) // Помечаем id как первичный ключ (Если мы попробуем добавить две заметки с одинаковым id - они обе добавлены не будут)
    private int id;
    private String text;
    private int priority;

    public Note(int id, String text, int priority) {
        this.id = id;
        this.text = text;
        this.priority = priority;
    }
    @Ignore // Так мы помечаем конструктор который не должен видить Room
    public Note(String text, int priority) {
        this.text = text;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getPriority() {
        return priority;
    }
}

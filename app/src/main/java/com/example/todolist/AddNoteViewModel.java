package com.example.todolist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todolist.database.NoteDatabase;
import com.example.todolist.interfaces.NotesDao;
import com.example.todolist.interfaces.NotesDao_Impl;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddNoteViewModel extends AndroidViewModel {

    private NotesDao notesDao;
    private MutableLiveData<Boolean> shouldCloseScreen = new MutableLiveData<>();

    public AddNoteViewModel(@NonNull Application application) {
        super(application);
        notesDao = NoteDatabase.getInstance(application).notesDao();
    }

    public LiveData<Boolean> getShouldCloseScreen() {
        return shouldCloseScreen;
    }

    public void saveNote(Note note) {
        notesDao.add(note)
                .subscribeOn(Schedulers.io()) // Подписываемся на поток Input/Output для добавления заметки в фоновом потоке
                .observeOn(AndroidSchedulers.mainThread()) // Закрываем экран в основном потоке
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {
                        shouldCloseScreen.setValue(true); // setValue - мы можем вызывать только из главного потока. postValue - мы можем вызывать из любого поток
                    }
                });
    }

}

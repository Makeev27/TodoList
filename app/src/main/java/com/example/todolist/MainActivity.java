package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolist.adapter.NotesAdapter;
import com.example.todolist.database.NoteDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewNotes;
    private FloatingActionButton buttonAddNote;
    private NotesAdapter notesAdapter;

    private MainViewModel viewModel;

//    private Handler handler = new Handler(Looper.getMainLooper()); // Handler хранит ссылку на MainLooper - главный поток

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new MainViewModel(getApplication());
        viewModel.getCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer count) {
                Toast.makeText(
                        MainActivity.this,
                        String.valueOf(count),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
        initViews();

        notesAdapter = new NotesAdapter();
        notesAdapter.setOnNoteClickListener(new NotesAdapter.onNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {

            }
        });
        recyclerViewNotes.setAdapter(notesAdapter); // set adapter to our RecyclerView

        viewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) { // Каждый раз когда будут приходить какие-либо изменения будет вызываться этот метод куда прилетят данные из таблицы
                notesAdapter.setNotes(notes);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(
                        0,
                        ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT
                ) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped( // Реализация такая же как просто при нажатии на элемент RecyclerView
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          int direction
                    ) {
                        int position = viewHolder.getAdapterPosition();
                        Note note = notesAdapter.getNotes().get(position);
                        viewModel.remove(note);
                    }
                });
        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);

        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddNoteActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        buttonAddNote = findViewById(R.id.buttonAddNote);
    }

//    private void showNotes() {
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                List<Note> notes = noteDatabase.notesDao().getNotes();
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        notesAdapter.setNotes(notes);
//                    }
//                });
//
//            }
//        });
//        thread.start();
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        showNotes();
//    }
}
package com.example.todolist.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.Note;
import com.example.todolist.R;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private List<Note> notes = new ArrayList<>();
    private onNoteClickListener onNoteClickListener;

    public void setOnNoteClickListener(NotesAdapter.onNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

    public List<Note> getNotes() {
        return new ArrayList<>(notes); // Возвращаем копию данной коллекции
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged(); // Метод показывает что данные изменились и следует перериросовать список
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // Метод создания "Держателя" View в котором мы получаем View из макета
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.note_item,
                parent,
                false); // Layout inflater создает из макета XML - View
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesViewHolder viewHolder, int position) { // Отвечает за контент который мы будем помещать в наше View
        Note note = notes.get(position);
        viewHolder.textViewNote.setText(note.getText()
        );

        int colorResId;
        switch (note.getPriority()) {
            case 0:
                colorResId = android.R.color.holo_green_light;
                break;
            case 1:
                colorResId = android.R.color.holo_orange_dark;
                break;
            default:
                colorResId = android.R.color.holo_red_dark;
        }
        int color = ContextCompat.getColor(viewHolder.itemView.getContext(), colorResId); // Получаем цвет из id
        viewHolder.textViewNote.setBackgroundColor(color);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // Когда мы создаем свои слушатели и вызываем у них определенные методы всегда необходимо добавлять проверку на null
                if (onNoteClickListener != null) {
                    onNoteClickListener.onNoteClick(note); // Никогда нельзя работать с базами данных в адаптере, адаптер отвечает только за работу с View элементами
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewNote;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNote = itemView.findViewById(R.id.textViewNote);
        }
    }

    public interface onNoteClickListener {

        void onNoteClick(Note note);
    }

}


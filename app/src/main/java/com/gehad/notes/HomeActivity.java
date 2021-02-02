package com.gehad.notes;

import android.content.Intent;
import android.os.Bundle;

import com.gehad.notes.adapter.NotesAdapter;
import com.gehad.notes.dataBase.MyDataBase;
import com.gehad.notes.dataBase.daos.NotesDao;
import com.gehad.notes.dataBase.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.List;


public class HomeActivity extends AppCompatActivity {

    RecyclerView notesRecyclerView;
    NotesAdapter adapter;
    List<Note> noteList;
    RecyclerView.LayoutManager layoutManager;
    NotesDao notesDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        notesRecyclerView = findViewById(R.id.notes_recycler_view);
        adapter = new NotesAdapter(noteList, HomeActivity.this);
        layoutManager = new LinearLayoutManager(this);
        notesRecyclerView.setAdapter(adapter);
        notesRecyclerView.setLayoutManager(layoutManager);

        notesDao = MyDataBase.getInstance(getApplicationContext()).notesDao();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this, AddNoteActivity.class);
                startActivity(intent);
            }
        });

        //delete by swipe recyclerView
        ItemTouchHelper.SimpleCallback simpleCallback
                = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView
                    , @NonNull RecyclerView.ViewHolder viewHolder
                    , @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Note note = adapter.removeNote(position);

                notesDao.deleteNote(note);
                Snackbar.make(notesRecyclerView, "delete note", Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                notesDao.insertNote(note);
                                adapter.addNote(position, note);
                            }
                        }).show();
            }
        };

        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(notesRecyclerView);

        adapter.onItemClickListener = new NotesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Note note) {
                startNoteDetailsActivity(note);
            }
        };
    }

    void startNoteDetailsActivity(Note note){
        Intent intent=new Intent(this,NoteDetailsActivity.class);
        intent.putExtra("ID",note.id);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        notesDao = MyDataBase.getInstance(getApplicationContext()).notesDao();
        List<Note> notes =notesDao.getAllNotes();

        adapter.changeData(notes);
    }
}
package com.gehad.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gehad.notes.dataBase.MyDataBase;
import com.gehad.notes.dataBase.daos.NotesDao;
import com.gehad.notes.dataBase.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NoteDetailsActivity extends AppCompatActivity {

    TextView date;
    EditText title ,description;
    FloatingActionButton save_btn;
    NotesDao notesDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        date = findViewById(R.id.date_tv);
        title = findViewById(R.id.title_note);
        description = findViewById(R.id.description_note);
        save_btn =findViewById(R.id.save);

        notesDao =MyDataBase.getInstance(getApplicationContext()).notesDao();

        showNoteDetails();
        save_btn.setOnClickListener(view -> {
            updateNote();
            Toast.makeText(NoteDetailsActivity.this,
                    R.string.Update_note, Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    void showNoteDetails(){

        Note note = getNote();
        Log.e("note",note.id+"  "+note.title);
        date.setText(note.getTime());
        title.setText(note.getTitle());
        description.setText(note.getDescription());

    }

    Note getNote() {
        int id=getIntent().getIntExtra("ID",-1);
        Note note = notesDao.searchNotesById(id);
        return note;
    }

    void updateNote(){

        Note note=getNote();
        String titleUpdate = title.getText().toString();
        String descriptionUpdate=description.getText().toString();

        note.setTitle(titleUpdate);
        note.setDescription(descriptionUpdate);
        note.setTime(getDate());

        notesDao.updateNote(note);
    }

    private String getDate(){
        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss  dd/MM/yyyy"
                , Locale.getDefault());

        String date=time.format(new Date());
        return date;
    }

}
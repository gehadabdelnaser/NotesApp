package com.gehad.notes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gehad.notes.dataBase.MyDataBase;
import com.gehad.notes.dataBase.daos.NotesDao;
import com.gehad.notes.dataBase.model.Note;

public class AddNoteActivity extends AppCompatActivity {
    EditText title ,description;
    Button add_btn;
    String titleNote ,descriptionNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        title = findViewById(R.id.title_note);
        description = findViewById(R.id.description_note);
        add_btn = findViewById(R.id.add_btn);


        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleNote = title.getText().toString();
                descriptionNote = description.getText().toString();

                Note note = new Note(titleNote, descriptionNote,getDate());

                NotesDao notesDao =MyDataBase.getInstance(getApplicationContext()).notesDao();
                notesDao.insertNote(note);
                showSuccessMessage();
            }
        });
    }

    private String getDate(){
        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss  dd/MM/yyyy"
                , Locale.getDefault());

        String date=time.format(new Date());
        return date;
    }

    void showSuccessMessage(){

       AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.note_created_successfully);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                finish();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
}
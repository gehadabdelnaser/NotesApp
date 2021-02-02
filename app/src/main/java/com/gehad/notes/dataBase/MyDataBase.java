package com.gehad.notes.dataBase;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.gehad.notes.dataBase.daos.NotesDao;
import com.gehad.notes.dataBase.model.Note;

@Database(entities = {Note.class} ,exportSchema = false ,version = 1)
public abstract class MyDataBase extends RoomDatabase {

    public abstract NotesDao notesDao();


    private static MyDataBase myDataBaseInstance;

    public static MyDataBase getInstance(Context context){
        if(myDataBaseInstance==null){
               myDataBaseInstance= Room.databaseBuilder(context,
                    MyDataBase.class,"NotesDataBase")
                       .fallbackToDestructiveMigration()
                       .allowMainThreadQueries()
                       .build();

        }
        return myDataBaseInstance;
    }
}
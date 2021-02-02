package com.gehad.notes.dataBase.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.awt.font.NumericShaper;

@Entity
public class Note {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name ="title")
    public String title;
    @ColumnInfo(name ="description")
    public String description;
    @ColumnInfo(name ="time")
    public String time;

    public Note(String title, String description, String time) {
        this.title = title;
        this.description = description;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }
}

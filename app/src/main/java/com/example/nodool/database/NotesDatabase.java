package com.example.nodool.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.nodool.dao.NoteDao;
import com.example.nodool.entities.Note;

@Database(entities = Note.class, version = 3, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {

    private static NotesDatabase notesDatabase;

    public static synchronized NotesDatabase getDatabase(Context context){
        if (notesDatabase == null){
            notesDatabase = Room.databaseBuilder(
                    context,
                    NotesDatabase.class,
                    "notes_db"
            ).fallbackToDestructiveMigration().build();
        }
        return notesDatabase;
    }

    public abstract NoteDao noteDao();

}

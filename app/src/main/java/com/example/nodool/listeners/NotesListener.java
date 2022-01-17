package com.example.nodool.listeners;

import com.example.nodool.entities.Note;

public interface NotesListener {
    void onNoteClicked(Note note, int position);
}

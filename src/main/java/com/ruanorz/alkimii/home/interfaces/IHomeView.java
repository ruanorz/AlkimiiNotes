package com.ruanorz.alkimii.home.interfaces;

import com.ruanorz.alkimii.models.Note;

import java.util.List;

public interface IHomeView {
    void showWait();
    void removeWait();
    void showNotes(List<Note> arraylist);
    void showError();
    void closeAddNoteView();
    void openAddNoteView();
}

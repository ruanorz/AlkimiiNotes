package com.ruanorz.alkimii.home.interfaces;

import com.ruanorz.alkimii.models.Note;

import java.util.List;

public interface IHomePresenter {
    void notesDownloadedOK(List<Note> arraylist);
    void notesDownloadedKO();
    void noteSavedOK();
    void noteSavedKO();
}

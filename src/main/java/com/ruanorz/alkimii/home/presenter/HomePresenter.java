package com.ruanorz.alkimii.home.presenter;

import android.content.Context;

import com.ruanorz.alkimii.home.interactor.HomeInteractor;
import com.ruanorz.alkimii.home.interfaces.IHomePresenter;
import com.ruanorz.alkimii.home.interfaces.IHomeView;
import com.ruanorz.alkimii.models.Note;

import java.util.List;
import java.util.Map;

public class HomePresenter implements IHomePresenter {

    Context context;
    IHomeView view;
    private HomeInteractor interactor = new HomeInteractor(context, this);

    public HomePresenter(Context context, IHomeView view){
        this.context = context;
        this.view = view;
    }

    public void getAllNotes(){

        interactor.getAllNotes();

    }

    public void saveNote(Map<String, Object> noteAux){

        interactor.saveNote(noteAux);

    }

    @Override
    public void notesDownloadedOK(List<Note> arraylist) {
        view.showNotes(arraylist);
        view.removeWait();
    }

    @Override
    public void notesDownloadedKO() {
        view.showError();
        view.removeWait();
    }

    @Override
    public void noteSavedOK() {
        view.closeAddNoteView();
    }

    @Override
    public void noteSavedKO() {
        view.closeAddNoteView();
        view.showError();
    }

    //    public void getUserLogedInInfo(){
//
//        User user = UserSession.getInstance().getUserInfo();
//
//        if (user!=null){
//            view.userInfoOK(user);
//        }else {
//            view.userInfoKO();
//        }
//
//    }
}

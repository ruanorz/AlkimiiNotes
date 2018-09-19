package com.ruanorz.alkimii.delete.presenter;

import android.content.Context;

import com.ruanorz.alkimii.delete.interactor.DeleteInteractor;
import com.ruanorz.alkimii.delete.interfaces.IDeletePresenter;
import com.ruanorz.alkimii.delete.interfaces.IDeleteView;

public class DeletePresenter implements IDeletePresenter {

    Context context;
    IDeleteView view;
    private DeleteInteractor interactor = new DeleteInteractor(context, this);

    public DeletePresenter(Context context, IDeleteView view){
        this.context = context;
        this.view = view;
    }

    public void deleteNote(String noteID){

        interactor.deleteNote(noteID);

    }

    @Override
    public void noteRemovedOK() {
        view.noteRemovedOK();
    }

    @Override
    public void noteRemovedKO() {
        view.noteRemovedKO();
    }
}

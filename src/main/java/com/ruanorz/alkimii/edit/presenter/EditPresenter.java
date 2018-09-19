package com.ruanorz.alkimii.edit.presenter;

import android.content.Context;

import com.ruanorz.alkimii.edit.interactor.EditInteractor;
import com.ruanorz.alkimii.edit.interfaces.IEditPresenter;
import com.ruanorz.alkimii.edit.interfaces.IEditView;

public class EditPresenter implements IEditPresenter{

    Context context;
    IEditView view;
    private EditInteractor interactor = new EditInteractor(context, this);

    public EditPresenter(Context context, IEditView view){
        this.context = context;
        this.view = view;
    }

    public void editNote(String id, String tv_title, String tv_content){
        interactor.editNote(id, tv_title, tv_content);
    }

    @Override
    public void noteModifiedOK() {
        view.closeActivity();
    }

    @Override
    public void noteModifiedKO() {
        view.showError();
    }
}

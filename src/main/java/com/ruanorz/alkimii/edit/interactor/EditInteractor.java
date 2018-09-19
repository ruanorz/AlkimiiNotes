package com.ruanorz.alkimii.edit.interactor;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ruanorz.alkimii.edit.interfaces.IEditPresenter;

import static android.content.Context.MODE_PRIVATE;

public class EditInteractor {

    Context context;
    IEditPresenter presenter;
    private FirebaseFirestore db;

    public EditInteractor(Context context, IEditPresenter presenter){
        this.context = context;
        this.presenter = presenter;
        db = FirebaseFirestore.getInstance();
    }

    public void editNote(String id, String tv_title, String tv_content){

        DocumentReference docRef = db.collection("notes").document(id);

        // Set the "isCapital" field of the city 'DC'
        docRef
            .update("title", tv_title+"", "content", tv_content+"")
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    presenter.noteModifiedOK();

                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    presenter.noteModifiedKO();

                }
            });
    }
}

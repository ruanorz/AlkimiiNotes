package com.ruanorz.alkimii.delete.interactor;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ruanorz.alkimii.delete.interfaces.IDeletePresenter;

public class DeleteInteractor {

    Context context;
    IDeletePresenter presenter;
    private FirebaseFirestore db;


    public DeleteInteractor(Context context, IDeletePresenter presenter){
        this.context = context;
        this.presenter = presenter;
        db = FirebaseFirestore.getInstance();
    }

    public void deleteNote(String noteID){

        db.collection("notes").document(noteID)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        presenter.noteRemovedOK();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        presenter.noteRemovedKO();

                    }
                });
    }
}

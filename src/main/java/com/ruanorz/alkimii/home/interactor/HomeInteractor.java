package com.ruanorz.alkimii.home.interactor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ruanorz.alkimii.home.interfaces.IHomePresenter;
import com.ruanorz.alkimii.models.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeInteractor {

    Context context;
    IHomePresenter presenter;
    private List<Note> noteList = new ArrayList<>();
    private FirebaseFirestore db;


    public HomeInteractor(Context context, IHomePresenter presenter){
        this.context = context;
        this.presenter = presenter;
        db = FirebaseFirestore.getInstance();
    }

    public void getAllNotes(){
        db.collection("notes")
            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.e("error", "db - "+document.getId() + " => " + document.getData() + " => " + document.getString("title"));
                    Note noteAux = new Note();
                    noteAux.setId(document.getId());
                    noteAux.setTitle(document.getString("title"));
                    noteAux.setContent(document.getString("content"));

                    noteList.add(noteAux);
                }

                presenter.notesDownloadedOK(noteList);

            } else {
                presenter.notesDownloadedKO();
            }
            }
        });
    }

    public void saveNote(Map<String, Object> noteAux){
        db.collection("notes")
            .add(noteAux)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    presenter.noteSavedOK();

                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    presenter.noteSavedKO();

                }
            });
    }
}

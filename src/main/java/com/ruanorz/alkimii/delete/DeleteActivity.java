package com.ruanorz.alkimii.delete;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ruanorz.alkimii.R;
import com.ruanorz.alkimii.delete.interfaces.IDeleteView;
import com.ruanorz.alkimii.delete.presenter.DeletePresenter;

public class DeleteActivity extends AppCompatActivity implements IDeleteView{

    private RelativeLayout btn_no, btn_yes;
    private TextView title_tv;

    private DeletePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        presenter = new DeletePresenter(getApplicationContext(), this);

        setLayout();


        Bundle extras = getIntent().getExtras();
        final String id = extras.getString("noteID");
        final String title = extras.getString("noteTITLE");
        final String content = extras.getString("noteCONTENT");

        title_tv.setText(title);

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                closeDialogActivity();

            }
        });



        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.deleteNote(id);
            }
        });
    }

    public void setLayout(){
        getSupportActionBar().hide();

        btn_no = findViewById(R.id.btn_no);
        btn_yes = findViewById(R.id.btn_yes);
        title_tv = findViewById(R.id.title_tv);
    }

    public void closeDialogActivity(){
        SharedPreferences.Editor editor = getSharedPreferences("mispreferencias", MODE_PRIVATE).edit();
        editor.putBoolean("recarga", false);
        editor.apply();

        supportFinishAfterTransition();
    }

    @Override
    public void noteRemovedOK() {
        SharedPreferences.Editor editor = getSharedPreferences("mispreferencias", MODE_PRIVATE).edit();
        editor.putBoolean("recarga", true);
        editor.apply();

        finish();
    }

    @Override
    public void noteRemovedKO() {
        SharedPreferences.Editor editor = getSharedPreferences("mispreferencias", MODE_PRIVATE).edit();
        editor.putBoolean("recarga", false);
        editor.apply();

        finish();
    }
}

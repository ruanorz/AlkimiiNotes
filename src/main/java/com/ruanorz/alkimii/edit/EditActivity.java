package com.ruanorz.alkimii.edit;

import android.animation.Animator;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ruanorz.alkimii.R;
import com.ruanorz.alkimii.edit.interfaces.IEditView;
import com.ruanorz.alkimii.edit.presenter.EditPresenter;

import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity implements IEditView{

    private TextView tv_title, tv_content;
    private ImageView back_arrow;
    private FloatingActionButton fab;
    private CoordinatorLayout parent_coordinator;

    private EditPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        presenter = new EditPresenter(getApplicationContext(), this);

        setLayout();


        Bundle extras = getIntent().getExtras();
        final String id = extras.getString("noteID");
        final String title = extras.getString("noteTITLE");
        final String content = extras.getString("noteCONTENT");

        tv_title.setText(title);
        tv_content.setText(content);

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                supportFinishAfterTransition();
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_title.getText().length()>=5 && tv_title.getText().length()<=10 && tv_content.getText().length()>=10 && tv_content.getText().length()<=55){


                    presenter.editNote(id, tv_title.getText().toString(), tv_content.getText().toString());



                }else {

                    showErrorInvalidData();

                }
            }
        });

    }

    public void setLayout(){
        getSupportActionBar().hide();

        tv_content = findViewById(R.id.tv_content);
        tv_title = findViewById(R.id.tv_title);
        back_arrow = findViewById(R.id.back_arrow);
        fab = findViewById(R.id.fab);
        parent_coordinator = findViewById(R.id.parent_coordinator);
    }

    @Override
    public void closeActivity() {
        SharedPreferences.Editor editor = getSharedPreferences("mispreferencias", MODE_PRIVATE).edit();
        editor.putBoolean("recarga", true);
        editor.apply();

        finish();
    }

    @Override
    public void showError() {
        Snackbar snack = Snackbar.make(parent_coordinator, "Error :(", Snackbar.LENGTH_LONG);
        View view3 = snack.getView();
        CoordinatorLayout.LayoutParams params=(CoordinatorLayout.LayoutParams)view3.getLayoutParams();
        params.gravity = Gravity.TOP;
        view3.setLayoutParams(params);
        snack.show();
    }

    public void showErrorInvalidData() {
        Snackbar snack = Snackbar.make(parent_coordinator, "Please enter valid data", Snackbar.LENGTH_LONG);
        View view3 = snack.getView();
        CoordinatorLayout.LayoutParams params=(CoordinatorLayout.LayoutParams)view3.getLayoutParams();
        params.gravity = Gravity.TOP;
        view3.setLayoutParams(params);
        snack.show();
    }
}

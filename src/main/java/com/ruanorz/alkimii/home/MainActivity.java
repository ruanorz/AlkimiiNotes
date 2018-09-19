package com.ruanorz.alkimii.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ruanorz.alkimii.R;
import com.ruanorz.alkimii.adapters.NotesAdapter;
import com.ruanorz.alkimii.home.interfaces.IHomeView;
import com.ruanorz.alkimii.home.presenter.HomePresenter;
import com.ruanorz.alkimii.models.Note;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.VISIBLE;


public class MainActivity extends AppCompatActivity implements IHomeView{

    private RecyclerView rv_notes;
    private List<Note> noteList = new ArrayList<>();
    private NotesAdapter notesAdapter;
    private FloatingActionButton fab;
    private RelativeLayout rl_add_note;
    private ImageView back_arrow;
    private EditText tv_title, tv_content;
    private CoordinatorLayout parent_coordinator;
    private RelativeLayout ly_cargando, ly_empty;

    private HomePresenter presenter;

    private float finalRadius;

    private boolean view_revealed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new HomePresenter(getApplicationContext(), this);

        setLayout();
        getNotes();
    }

    private void setLayout(){
        getSupportActionBar().hide();

        rv_notes = findViewById(R.id.rv_notes);
        fab = findViewById(R.id.fab);
        rl_add_note = findViewById(R.id.rl_add_note);
        back_arrow = findViewById(R.id.back_arrow);
        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);
        parent_coordinator = findViewById(R.id.parent_coordinator);
        ly_cargando = findViewById(R.id.ly_cargando);
        ly_empty = findViewById(R.id.ly_empty);

        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();  // deprecated
        int height = display.getHeight();

        final int cx = width;
        final int cy = height;

        finalRadius = (float) Math.max(cx, cy) * 1.3f;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addNoteClick();


            }
        });

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                closeAddNoteView();

            }
        });
    }

    private void getNotes(){

        noteList.clear();
        showWait();
        presenter.getAllNotes();

    }

    public void showNotes(List<Note> arraylist){
        noteList = arraylist;
        if (noteList.size()>0) {

            notesAdapter = new NotesAdapter(noteList, this);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            rv_notes.setLayoutManager(llm);
            rv_notes.setAdapter(notesAdapter);
            ly_empty.setVisibility(View.GONE);

        }else {

            ly_empty.setVisibility(VISIBLE);

        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = getSharedPreferences("mispreferencias", MODE_PRIVATE);
        Boolean regarga = prefs.getBoolean("recarga", false);
        if (regarga) {
            getNotes();

            SharedPreferences.Editor editor = getSharedPreferences("mispreferencias", MODE_PRIVATE).edit();
            editor.putBoolean("recarga", false);
            editor.apply();

        }

    }

    public void addNoteClick(){
        if (!view_revealed) {

            openAddNoteView();

        }else {

            if (tv_title.getText().length()>=5 && tv_title.getText().length()<=10 && tv_content.getText().length()>=10 && tv_content.getText().length()<=55){

                fab.setClickable(false);

                Map<String, Object> noteAux = new HashMap<>();
                noteAux.put("title", tv_title.getText()+"");
                noteAux.put("content", tv_content.getText()+"");

                presenter.saveNote(noteAux);


            }else {

                fab.setClickable(true);
                Snackbar snack = Snackbar.make(parent_coordinator, "Please enter valid data", Snackbar.LENGTH_LONG);
                View view3 = snack.getView();
                CoordinatorLayout.LayoutParams params=(CoordinatorLayout.LayoutParams)view3.getLayoutParams();
                params.gravity = Gravity.TOP;
                view3.setLayoutParams(params);
                snack.show();
            }

        }
    }

    @Override
    public void closeAddNoteView() {
        fab.setClickable(false);
        Animator revealAnimator = ViewAnimationUtils.createCircularReveal(rl_add_note, (int) fab.getX() + fab.getWidth() / 2, (int) fab.getY() + fab.getHeight() / 2, finalRadius, 0);

        revealAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view_revealed = false;
                fab.setClickable(true);

                fab.setImageDrawable(getDrawable(R.drawable.add_icon));
                fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));


                getNotes();

                tv_content.setText("");
                tv_title.setText("");

                rl_add_note.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        revealAnimator.setDuration(500);
        revealAnimator.start();
    }

    @Override
    public void openAddNoteView() {
        fab.setClickable(false);
        rl_add_note.setVisibility(VISIBLE);

        Animator animation = ViewAnimationUtils
                .createCircularReveal(rl_add_note, (int) fab.getX() + fab.getWidth() / 2, (int) fab.getY() + fab.getHeight() / 2, 0f, finalRadius);

        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view_revealed = true;

                fab.setImageDrawable(getDrawable(R.drawable.save_icon));
                fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.yellow)));

                fab.setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animation.setDuration(500);
        animation.start();
    }

    @Override
    public void showWait() {
        ly_cargando.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeWait() {
        ly_cargando.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        Log.e("error", "ERROR!");
    }
}

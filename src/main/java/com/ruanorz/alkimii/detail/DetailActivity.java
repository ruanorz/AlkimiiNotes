package com.ruanorz.alkimii.detail;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruanorz.alkimii.R;

public class DetailActivity extends AppCompatActivity {

    private TextView tv_title, tv_content;
    private ImageView back_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setLayout();


        Bundle extras = getIntent().getExtras();
        String id = extras.getString("noteID");
        String title = extras.getString("noteTITLE");
        String content = extras.getString("noteCONTENT");

        tv_title.setText(title);
        tv_content.setText(content);



        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                supportFinishAfterTransition();
            }
        });
    }

    public void setLayout(){

        getSupportActionBar().hide();

        tv_content = findViewById(R.id.tv_content);
        tv_title = findViewById(R.id.tv_title);
        back_arrow = findViewById(R.id.back_arrow);

    }
}

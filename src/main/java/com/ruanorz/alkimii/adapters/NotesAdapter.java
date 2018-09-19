package com.ruanorz.alkimii.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruanorz.alkimii.detail.DetailActivity;
import com.ruanorz.alkimii.edit.EditActivity;
import com.ruanorz.alkimii.delete.DeleteActivity;
import com.ruanorz.alkimii.home.MainActivity;
import com.ruanorz.alkimii.R;
import com.ruanorz.alkimii.models.Note;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {

    private List<Note> noteList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title, tv_content;
        public CardView parent_item;
        public ImageView edit_btn, delete_btn;

        public MyViewHolder(View view) {
            super(view);
            tv_title = view.findViewById(R.id.tv_title);
            tv_content = view.findViewById(R.id.tv_content);
            parent_item = view.findViewById(R.id.parent_item);
            edit_btn = view.findViewById(R.id.edit_btn);
            delete_btn = view.findViewById(R.id.delete_btn);
        }
    }


    public NotesAdapter(List<Note> noteList, Context mContext) {
        this.noteList = noteList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Note note = noteList.get(position);
        holder.tv_title.setText(note.getTitle());
        holder.tv_content.setText(note.getContent());


        holder.parent_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailActivity.class);
// Pass data object in the bundle and populate details activity.
                intent.putExtra("noteID", note.getId());
                intent.putExtra("noteTITLE", note.getTitle());
                intent.putExtra("noteCONTENT", note.getContent());

                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((MainActivity)mContext, (View)holder.tv_title, "title_shared");
                mContext.startActivity(intent, options.toBundle());
            }
        });


        holder.edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EditActivity.class);
// Pass data object in the bundle and populate details activity.
                intent.putExtra("noteID", note.getId());
                intent.putExtra("noteTITLE", note.getTitle());
                intent.putExtra("noteCONTENT", note.getContent());

                mContext.startActivity(intent);
            }
        });

        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DeleteActivity.class);
// Pass data object in the bundle and populate details activity.
                intent.putExtra("noteID", note.getId());
                intent.putExtra("noteTITLE", note.getTitle());
                intent.putExtra("noteCONTENT", note.getContent());

                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((MainActivity)mContext, (View)holder.tv_title, "title_shared");
                mContext.startActivity(intent, options.toBundle());
            }
        });


    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

}
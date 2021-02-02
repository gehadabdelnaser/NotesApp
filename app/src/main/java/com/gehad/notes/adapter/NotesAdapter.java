package com.gehad.notes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.gehad.notes.R;
import com.gehad.notes.dataBase.model.Note;
import java.util.List;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    List<Note> notes;
    Context context;

    public NotesAdapter(List<Note> notes, Context context) {
        this.notes = notes;
        this.context = context;
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {
        Note item = notes.get(position);
        holder.titleNote.setText(item.getTitle());
        holder.dateNote.setText(item.getTime());

        if(onItemClickListener != null){
            holder.itemView.setOnClickListener(view -> {
                onItemClickListener.onItemClick(position,item);
            });
        }
    }

    public void changeData(List<Note> notes){
        this.notes=notes;
        notifyDataSetChanged();
    }

    public Note removeNote(int position){
        Note note =notes.remove(position);
        notifyItemRemoved(position);
        return note;
    }

    public void addNote(int position, Note note){
        notes.add(position,note);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (notes == null) return 0;
        else return notes.size();
    }

    public OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(int position , Note note);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleNote, dateNote;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleNote = itemView.findViewById(R.id.title_note);
            dateNote = itemView.findViewById(R.id.date_tv);

        }
    }
}
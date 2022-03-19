package edu.quinnipiac.ser210.assignment2part2piano;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChordAdapter extends RecyclerView.Adapter<ChordAdapter.ViewHolder>{
    private ArrayList<Chord> mChordData;
    private Context mContext;

    public ChordAdapter(Context context, ArrayList<Chord> chordData){
        mChordData = chordData;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chord currentChord = mChordData.get(position);

        holder.bindTo(currentChord);
;    }

    @Override
    public int getItemCount() {
        return mChordData.size();
    }

    //Replaces data in recycle view with new data
    public void filterList(ArrayList<Chord> filteredList){
        mChordData = filteredList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mRootText;
        private TextView mNotesText;
        private TextView mIntervalsText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mRootText = itemView.findViewById(R.id.root);
            mNotesText = itemView.findViewById(R.id.notes);
            mIntervalsText = itemView.findViewById(R.id.intervals);
        }

        public void bindTo(Chord currentChord) {
            mRootText.setText("Name: " +currentChord.getRoot());

            String[] mNotesArray = currentChord.getNotes();
            String mNotes = "Notes: ";
            for(String i : mNotesArray){
                mNotes = mNotes + i + ", ";
            }
            mNotesText.setText(mNotes);

            String[] mIntervalsArray = currentChord.getIntervals();
            String mIntervals = "Intervals: ";
            for(String i : mIntervalsArray){
                mIntervals = mIntervals + i + ", ";
            }
            mIntervalsText.setText(mIntervals);
        }
    }
}

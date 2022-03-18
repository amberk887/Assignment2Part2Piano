package edu.quinnipiac.ser210.assignment2part2piano;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class ChordsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ArrayList<Chord> mChordData;
    private ChordAdapter mChordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chords);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mChordData = (ArrayList<Chord>) getIntent().getExtras().get("chords");
        mChordAdapter = new ChordAdapter(this, mChordData);
        mRecyclerView.setAdapter(mChordAdapter);
    }
}
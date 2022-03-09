package edu.quinnipiac.ser210.assignment2part2piano;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ChordsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chords);

        //supposed to get the notes of the chord and set the text view to those chords
        String chord = (String) getIntent().getExtras().get("notes");
        TextView textView = (TextView) findViewById(R.id.notes);
        textView.setText(chord);
    }

}
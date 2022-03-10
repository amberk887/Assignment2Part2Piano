package edu.quinnipiac.ser210.assignment2part2piano;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ChordsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chords);

        String notes = (String) getIntent().getExtras().get("notes");
        TextView textView = (TextView) findViewById(R.id.notes);
        textView.setText(notes);
    }
}
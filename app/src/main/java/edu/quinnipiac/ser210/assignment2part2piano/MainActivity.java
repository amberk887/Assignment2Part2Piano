package edu.quinnipiac.ser210.assignment2part2piano;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
        ChordHandler chordHandler = new ChordHandler();

        private String url1 = "https://piano-chords.p.rapidapi.com/chords/";
        private String LOG_TAG = MainActivity.class.getSimpleName();
        private String root;

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Spinner spinner = (Spinner)findViewById(R.id.spinner);

            ArrayAdapter<String> chordAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,chordHandler.chords);

            chordAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(chordAdapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    root = (String) parent.getItemAtPosition(position);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        public void onSubmit(View view) {
            if(root != null){
                new FetchNote().execute(root);
            }
        }

    class FetchNote extends AsyncTask<String,Void,ArrayList<Chord>>{
            @Override
            protected ArrayList<Chord> doInBackground(String... strings) {

                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;
                ArrayList<Chord> chords = null;
                try{
                    URL url = new URL(url1 + noteToURLConverter(strings[0]));
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("x-rapidapi-key", "61d3d3d5e3mshab3a9610c39fe9ap1d574djsn973568c7e356");
                    urlConnection.connect();

                    InputStream in = urlConnection.getInputStream();
                    if(in == null)
                        return null;

                    reader = new BufferedReader(new InputStreamReader(in));
                    chords = getChordsFromBuffer(reader, strings[0]);

                }catch(Exception e){
                    Log.e(LOG_TAG,"Error" + e.getMessage());
                    return null;
                }finally{
                    if (urlConnection != null){
                        urlConnection.disconnect();
                    }
                    if (reader != null){
                        try{
                            reader.close();
                        }catch (IOException e){
                            Log.e(LOG_TAG,"Error" + e.getMessage());
                            return null;
                        }
                    }
                }
                return chords;
            }

            @Override
            protected void onPostExecute(ArrayList<Chord> result) {
                if (result != null)
                    Log.d(LOG_TAG, result.toString());
                Intent intent = new Intent(MainActivity.this,ChordsActivity.class);
                intent.putExtra("chords", result);
                startActivity(intent);

            }
        }

        //Gets the chords from the BufferedReader
        private ArrayList<Chord> getChordsFromBuffer(BufferedReader bufferedReader, String root) {
            StringBuffer buffer = new StringBuffer();
            String line;

            if (bufferedReader != null) {
                try {
                    while ((line = bufferedReader.readLine()) != null) {
                        buffer.append(line + '\n');
                    }
                    bufferedReader.close();
                    return chordHandler.getChords(buffer.toString(), root);
                } catch (Exception e) {
                    Log.e("MainActivity", "Error" + e.getMessage());
                    return null;
                } finally {

                }
            }
            return null;
        }

        //Takes the input from the spinner and converts it such that the inputted note works with the api
        private String noteToURLConverter(String note){
            if(note.contains("#")){
                String res = note.charAt(0) + "sharp";
                return res.toLowerCase();
            }else{
                return note.toLowerCase();
            }
        }
    }







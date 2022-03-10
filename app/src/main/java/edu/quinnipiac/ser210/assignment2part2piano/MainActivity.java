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



    public class MainActivity extends AppCompatActivity {
        ChordHandler chordHandler = new ChordHandler();

        boolean userSelect = false;
        private String url1 = "https://piano-chords.p.rapidapi.com/chords/major";
        //private String url2= "/year?fragment=true&json=true";
        private String LOG_TAG = MainActivity.class.getSimpleName();

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
                    if (userSelect) {
                        final String chord = (String) parent.getItemAtPosition(position);
                        Log.i("onItemSelected :chord", chord);

                        //TODO : call of async subclass goes here
                        new FetchNote().execute(chord);
                        userSelect = false;
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        @Override
        public void onUserInteraction() {
            super.onUserInteraction();
            userSelect = true;
        }

        class FetchNote extends AsyncTask<String,Void,String>{
            @Override
            protected String doInBackground(String... strings) {

                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;
                String notes = null;
                //I'm not sure if this works but what should happen is it gets our url and then the string (in our case the cord) of what the user
                //selects it then is supposed to get the notes of the chord, i'm taking this in as a string I was unsure how to read it in as an array of notes
                try{
                    URL url = new URL(url1 + strings[0]);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("x-rapidapi-key", "2fb87833cdmsh42170d144acb09dp1dccb7jsn1dedc3031a74");
                    urlConnection.connect();

                    InputStream in = urlConnection.getInputStream();
                    if(in == null)
                        return null;

                    reader = new BufferedReader(new InputStreamReader(in));
                    notes = getStringFromBuffer(reader);

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
                return notes;
            }

            @Override
            protected void onPostExecute(String result) {
                if (result != null)
                    Log.d(LOG_TAG, result);
                Intent intent = new Intent(MainActivity.this,ChordsActivity.class);
                intent.putExtra("notes", result);
                startActivity(intent);

            }
        }

        private String getStringFromBuffer(BufferedReader bufferedReader) {
            StringBuffer buffer = new StringBuffer();
            String line;

            if (bufferedReader != null) {
                try {
                    while ((line = bufferedReader.readLine()) != null) {
                        buffer.append(line + '\n');
                    }
                    bufferedReader.close();
                    return chordHandler.getNotes(buffer.toString());
                } catch (Exception e) {
                    Log.e("MainActivity", "Error" + e.getMessage());
                    return null;
                } finally {

                }
            }
            return null;
        }
    }







package edu.quinnipiac.ser210.assignment2part2piano;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.Buffer;
import java.nio.channels.AsynchronousChannelGroup;

public class MainActivity extends AppCompatActivity {
    //we used one url ruby in her example used two we don't know why ***
    private String url1 = "https://piano-chords.p.rapidapi.com/chords/csharp";
    boolean userSelect = false;
    ChordHandler chordHandler = new ChordHandler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> chordsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ChordHandler.notes);
        chordsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(chordsAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                if (userSelect) {
                    final String chord = (String) parent.getItemAtPosition(position);
                    //we give user option to select chord then return notes in chord
                    Log.i("onItemSelected :chord", chord);

                    // here to fetch the notes in chord
                    userSelect = false;
                }
                // set the key for parsing the jason object
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    //when user chooses spinner this is true
    public void onUserInteraction() {
        super.onUserInteraction();
        userSelect = true;
    }

    class fetchNote extends AsyncTask<String, Void, String> {
        @SuppressLint("RestrictedApi")
        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String note = null;
            try {
                //she had two urls dont know why!!!
                URL url = new URL(url1);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("x-rapidapi-key", "2fb87833cdmsh42170d144acb09dp1dccb7jsn1dedc3031a74");
                urlConnection.connect();
                InputStream in = urlConnection.getInputStream();
                if (in == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(in));
                note = getStringFromBuffer(reader);

            } catch (Exception e) {
                Log.e(LOG_TAG, "error" + e.getMessage());
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "error" + e.getMessage());
                        return null;
                    }
                }
            }
            return note;
        }

        @SuppressLint("RestrictedApi")
        @Override
        protected void onPostExecute(String result) {
            if (result != null)
                Log.d(LOG_TAG, result);
            Intent intent = new Intent(MainActivity.this,ChordsActivity.class);
            intent.putExtra("Notes", result);
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






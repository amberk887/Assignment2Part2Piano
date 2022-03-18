package edu.quinnipiac.ser210.assignment2part2piano;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

public class ChordHandler {
    private static final int FIRSTCHORD = 1;
    private static final int ENDCHORD = 12;
    private String LOG_TAG = ChordHandler.class.getSimpleName();

    final public static String [] chords = new String[12];;

    public ChordHandler(){
        //populating the chords array, choices for the drop down menu in main acitivty
       chords[0] = "C";
       chords[1] = "C#";
       chords[2] = "D";
       chords[3] = "D#";
       chords[4] = "E";
       chords[5] = "F";
       chords[6] = "F#";
       chords[7] = "G";
       chords[8] = "G#";
       chords[9] = "A";
       chords[10] = "A#";
       chords[11] = "B";
    }

    public ArrayList<Chord> getChords(String noteJsonStr, String root) throws JSONException {
        ArrayList<Chord> chords = new ArrayList<Chord>();

        JSONObject listJSONObj = new JSONObject(noteJsonStr);
        JSONObject noteJSONObj = listJSONObj.getJSONObject(root);

        //Create an iterator in order to iterate through all of the chords for the root.
        Iterator keys = noteJSONObj.keys();
        while(keys.hasNext()){
            //Gets the current chord's key
            String currentKey = (String)keys.next();

            //Gets the current JSON Object for the current chord
            JSONObject currentChord = noteJSONObj.getJSONObject(currentKey);

            //Gets the name of the current chord
            String name = currentChord.getString("name");

            //Gets the notes that are in the current chord, converting them from a JSON Array to a String array
            JSONArray notesJSONArray = currentChord.getJSONArray("notes");
            String[] notes = new String[notesJSONArray.length()];
            for(int i = 0; i < notesJSONArray.length(); ++i){
                notes[i] = notesJSONArray.optString(i);
            }

            //Gets the intervals that are in the current chord, converting them from a JSON Array to a Sting array
            JSONArray intervalsJSONArray = currentChord.getJSONArray("intervals");
            String[] intervals = new String[intervalsJSONArray.length()];
            for(int i =0; i < intervalsJSONArray.length(); ++i){
                intervals[i] = intervalsJSONArray.optString(i);
            }

            chords.add(new Chord(name, notes, intervals));
        }
        return chords;
    }


}


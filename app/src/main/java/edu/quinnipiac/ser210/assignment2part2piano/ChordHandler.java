package edu.quinnipiac.ser210.assignment2part2piano;

import org.json.JSONException;
import org.json.JSONObject;

public class ChordHandler {
    private static final int FIRSTCHORD = 1;
    private static final int ENDCHORD = 12;

    public static final String YEAR_FACT = "YEAR_FACT";
    final public static String [] chords = new String[ENDCHORD - FIRSTCHORD];;

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

    public String getNotes(String noteJsonStr) throws JSONException {
        JSONObject noteJSONObj = new JSONObject(noteJsonStr);
        return noteJSONObj.getString("text");
    }


}


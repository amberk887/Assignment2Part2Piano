package edu.quinnipiac.ser210.assignment2part2piano;

import org.json.JSONException;
import org.json.JSONObject;

public class ChordHandler {

    public static String[] notes;
    final public static  String NOTES = "NOTES";

    public ChordHandler(){

    }

    //getting notes that make up the chord
    public String getNotes(String notesJsonStr) throws JSONException {
        JSONObject notesJSONObj = new JSONObject(notesJsonStr);
        return notesJSONObj.getString("text");
    }
}


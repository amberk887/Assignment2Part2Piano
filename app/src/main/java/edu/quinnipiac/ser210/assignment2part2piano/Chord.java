package edu.quinnipiac.ser210.assignment2part2piano;

import java.io.Serializable;

public class Chord implements Serializable {
    private String root;
    private String[] notes;
    private String[] intervals;

    public Chord(String root, String[] notes, String[] intervals){
        this.root = root;
        this.notes = notes;
        this.intervals = intervals;
    }

    public String getRoot(){
        return this.root;
    }

    public String[] getNotes(){
        return this.notes;
    }

    public String[] getIntervals(){
        return this.intervals;
    }
}

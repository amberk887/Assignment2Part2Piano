package edu.quinnipiac.ser210.assignment2part2piano;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

public class ChordsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Chord> mChordData;
    private ChordAdapter mChordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chords);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        mRecyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mChordData = (ArrayList<Chord>) getIntent().getExtras().get("chords");
        mChordAdapter = new ChordAdapter(this, mChordData);
        mRecyclerView.setAdapter(mChordAdapter);
    }

    //Gets the menu options from the menu xml and displays them on the tool bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        //Listener for the search view
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    //Listener for the buttons on the toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch(id){
            case R.id.action_shuffle:{
                shuffle();
                return true;
            }
            case R.id.action_random:{
                random();
                return true;
            }
            default:
                return false;
        }
    }

    //Searches for a chord using a given string from the Search View
    public void filter(String text){
        ArrayList<Chord> filteredList = new ArrayList<>();

        for(Chord item : mChordData){
            if(item.getRoot().toLowerCase().contains(text.toLowerCase(Locale.ROOT))){
                filteredList.add(item);
            }
        }

        if(filteredList.isEmpty()){
            Toast.makeText(this, "No Chords Found", Toast.LENGTH_SHORT).show();
        }else{
            mChordAdapter.filterList(filteredList);
        }
    }

    //Shuffles the recycle view
    public void shuffle(){
        Collections.shuffle(mChordData, new Random());
        mChordAdapter.filterList(mChordData);
    }

    //Scrolls to a random position in the recycle view, resulting a random chord being shown
    public void random(){
        int pos = (int) (Math.random() * mChordData.size());
        linearLayoutManager.scrollToPositionWithOffset(pos, 0);
    }
}
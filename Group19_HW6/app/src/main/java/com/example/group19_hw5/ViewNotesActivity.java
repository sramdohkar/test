package com.example.group19_hw5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
/**
 * Created by shashank on 07/03/2016.
 */
public class ViewNotesActivity extends AppCompatActivity {
    DatabaseDataManager dm;
    ArrayList<Note> noteList;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);
        noteList = new ArrayList<>();
        dm = new DatabaseDataManager(ViewNotesActivity.this);
        noteList = (ArrayList) dm.getAllNotes();
        lv = (ListView) findViewById(R.id.listView);

        NotesAdapter adapter = new NotesAdapter(ViewNotesActivity.this, R.layout.row_item2, noteList);
        lv.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ViewNotesActivity.this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}

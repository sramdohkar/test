package com.example.group19_hw5;
/**
 * Created by shashank on 07/03/2016.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddNotesActivity extends AppCompatActivity {
    String month, city, state;
    DatabaseDataManager dm;
    CityList c;
    int day;
    Note note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        Intent intentcs = getIntent();
        c = intentcs.getParcelableExtra("CityState");
        dm = new DatabaseDataManager(AddNotesActivity.this);
        day = getIntent().getIntExtra("day", 0);
        month = getIntent().getStringExtra("month");
        city = getIntent().getStringExtra("city");

        final TextView txtNote = (TextView) findViewById(R.id.edtNote);
        Button btnSave = (Button) findViewById(R.id.btnSaveNote);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtNote.getText().toString().trim().equals(""))
                {
                    Toast.makeText(AddNotesActivity.this, "Please add text", Toast.LENGTH_SHORT).show();
                }
                else if(txtNote.getText().toString().length() < 1 || txtNote.getText().toString().length() > 30)
                {
                    Toast.makeText(AddNotesActivity.this, "Enter note between 1 and 30 characters", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    note = new Note();
                    note.setCitykey(city);
                    note.setDate(String.valueOf(day) + " " + month);
                    note.setNote(txtNote.getText().toString().trim());
                    dm.saveNote(note);
                    Toast.makeText(AddNotesActivity.this, "Note added successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AddNotesActivity.this, AndroidTabLayoutActivity.class);
                    intent.putExtra("noteadded", "noteadded");
                    intent.putExtra("CityState", c);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(AddNotesActivity.this, AndroidTabLayoutActivity.class);
        intent.putExtra("noteadded", "noteadded");
        intent.putExtra("CityState", c);
        startActivity(intent);
        finish();
    }
}

/*
    Assignment HW4
    Group#19_HW04.zip
    Shashank Ramdohkar
 */

package com.example.group19_hw04;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText searchMovie = (EditText) findViewById(R.id.findMovies);
        Button btnSearch = (Button) findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchMovie.getText().toString().trim().equals(""))
                {
                    Toast toast = Toast.makeText(MainActivity.this, "Enter movie name", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    Intent intent = new Intent(MainActivity.this, SearchMovieActivity.class);
                    intent.putExtra("MovieName", searchMovie.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}

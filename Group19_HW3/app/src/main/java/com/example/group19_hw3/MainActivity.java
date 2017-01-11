/*
    Assignment HW3
    Group#19_HW03.zip
    Shashank Ramdohkar, James Budday, Jeffrey Snow
 */

package com.example.group19_hw3;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startQuiz = (Button) findViewById(R.id.startQuiz);

        new TimePass().execute();

        startQuiz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToWelcome();
            }
        });
    }

    private class TimePass extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            long start = System.currentTimeMillis();
            while(start > System.currentTimeMillis() - 8000)
            {
                //Log.d("seconds", (System.currentTimeMillis() - start)/1000.0 + "");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            goToWelcome();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            finish();
        }
    }

    private void goToWelcome() {
        Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
        startActivityForResult(intent, 0);
    }
}

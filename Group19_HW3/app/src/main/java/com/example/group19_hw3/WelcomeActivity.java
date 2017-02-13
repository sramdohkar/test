/*
    Assignment HW3
    Group#19_HW03.zip
    Shashank Ramdohkar
 */

package com.example.group19_hw3;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {
    ProgressBar progressBar;
    TextView progressText;
    Button startQuiz;
    ArrayList<QuizDetails> quizDetails = new ArrayList<QuizDetails>();
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        startQuiz = (Button) findViewById(R.id.startQuiz);
        Button exit = (Button) findViewById(R.id.exit);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        progressText = (TextView) findViewById(R.id.textViewProgress);

        if (isNetworkAvailable())
        {
            for (int i = 0; i <= 6; i ++) {
                new GetData().execute("http://dev.theappsdr.com/apis/spring_2016/hw3/index.php?qid=" + i);
                count++;
            }

            startQuiz.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(WelcomeActivity.this, QuizActivity.class);
                    intent.putParcelableArrayListExtra("QuizDetails", quizDetails);
                    startActivityForResult(intent, 1);
                }
            });
        }
        else
        {
            Toast.makeText(WelcomeActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
        }

        //exit button
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    private class GetData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String...params){
            BufferedReader reader = null;
            try {

                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                    String[] ques = {};
                    QuizDetails qd = new QuizDetails();
                    ques = line.split(";");
                    int j;
                    for (int i = 0; i < ques.length; i++) {
                        qd.quizId = Integer.parseInt(ques[0]);
                        qd.quizText = ques[1];
                        for(j = 2; j < ques.length - 1; j++)
                        {
                            qd.answerText.add(ques[j]);
                            j++;
                            qd.answerValue.add(ques[j]);
                        }
                        if (j == ques.length - 1)

                            qd.quizUrl = ques[ques.length - 1];

                        else
                            qd.quizUrl = ""; //if picture URL is not present in the quiz details we will keep it empty.
                        i = ques.length;
                    }
                    quizDetails.add(qd);
                }
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if(reader != null)
                        reader.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(quizDetails.size() == 7)
            {
                progressBar.setVisibility(View.INVISIBLE);
                progressText.setVisibility(View.INVISIBLE);
                startQuiz = (Button)findViewById(R.id.startQuiz);
                startQuiz.setEnabled(true);
            }
            if(s != null) {

            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {

        }
        else if(resultCode == RESULT_CANCELED)
            finish();
    }
}

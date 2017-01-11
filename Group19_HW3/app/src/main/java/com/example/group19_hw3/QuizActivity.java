/*
    Assignment HW3
    Group#19_HW03.zip
    Shashank Ramdohkar, James Budday, Jeffrey Snow
 */

package com.example.group19_hw3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    int score, count;
    ProgressBar progressBar;
    ImageView image;
    TextView questionText, questionNumber;
    ArrayList<QuizDetails> quizDetails;
    RadioGroup rg;
    QuizDetails question;
    boolean canAdvance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Button quit = (Button) findViewById(R.id.buttonQuit);
        Button next = (Button) findViewById(R.id.buttonNext);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        image = (ImageView) findViewById(R.id.imageViewQuiz);
        questionText = (TextView) findViewById(R.id.textViewQuestion);
        questionNumber = (TextView) findViewById(R.id.textViewQNum);
        rg = (RadioGroup) findViewById(R.id.radioGroup);
        canAdvance = false;

        //initial setup
        quizDetails = new ArrayList<QuizDetails>();
        quizDetails = (getIntent().getExtras().getParcelableArrayList("QuizDetails"));
        score = 0;
        count = 0;

        buildQuestion();

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizActivity.this, WelcomeActivity.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToScore();

                if (canAdvance) {
                    if (count == quizDetails.size()) {
                        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                        intent.putExtra("score", score);
                        startActivityForResult(intent, 2);
                    } else
                        buildQuestion();

                }
                canAdvance = false;
            }
        });
    }

    class GetImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {

            if(!params[0].equals("")) {
                InputStream in = null;
                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    in = con.getInputStream();

                    Bitmap image = BitmapFactory.decodeStream(in);


                    return image;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            if(!quizDetails.get(count).quizUrl.equals(""))
                progressBar.setVisibility(View.VISIBLE);
            image.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPostExecute(Bitmap b) {
            progressBar.setVisibility(View.INVISIBLE);
            if(b != null) {
                image.setImageBitmap(b);
                image.setVisibility(View.VISIBLE);
            }
        }
    }

    private void buildQuestion() {

        question = quizDetails.get(count);

        //clear radio buttons
        rg.removeAllViews();
        questionNumber.setText(getString(R.string.q) + (count + 1));

        if(isNetworkAvailable()) {
            new GetImage().execute(question.quizUrl);
        }

        questionText.setText(question.quizText);

        //seed shuffle with same seed to keep parallelism of arraylists
        long seed = System.nanoTime();
        Collections.shuffle(question.answerText, new Random(seed));
        Collections.shuffle(question.answerValue, new Random(seed));

        //populate radio buttons
        for(int i = 0 ; i < question.answerText.size() ; i++) {
            RadioButton rb = new RadioButton(this);
            rb.setText(question.answerText.get(i));
            rg.addView(rb);
        }

        count++;
    }

    private void addToScore() {
        if (rg.getCheckedRadioButtonId() != -1) {
            int checkedIndex = rg.indexOfChild(rg.findViewById(rg.getCheckedRadioButtonId()));
            score += Integer.parseInt(question.answerValue.get(checkedIndex));

            rg.clearCheck();
            canAdvance = true;
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
            finish();
        } else if (resultCode == RESULT_CANCELED) {
            count = 0;
            score = 0;
            buildQuestion();
        }
    }
}

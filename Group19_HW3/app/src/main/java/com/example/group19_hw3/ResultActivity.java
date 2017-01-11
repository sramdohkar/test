/*
    Assignment HW3
    Group#19_HW03.zip
    Shashank Ramdohkar, James Budday, Jeffrey Snow
 */

package com.example.group19_hw3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        final TextView geekType = (TextView) findViewById(R.id.geekType);
        final TextView geekdesc = (TextView) findViewById(R.id.geekDesc);
        final ImageView geekPic = (ImageView) findViewById(R.id.geekPic);
        Button quit = (Button) findViewById(R.id.quit);
        Button tryAgain = (Button) findViewById(R.id.tryAgain);

        int geekScore = getIntent().getIntExtra("score", 0);

        if(geekScore >= 51)
        {
            geekType.setText(getString(R.string.uber));
            geekdesc.setText(getString(R.string.uberDesc));
            geekPic.setImageResource(R.drawable.uber_geek);

        }
        else if(geekScore >= 11 && geekScore <= 50)
        {
            geekType.setText(getString(R.string.semi));
            geekdesc.setText(getString(R.string.semiDesc));
            geekPic.setImageResource(R.drawable.semi_geek);

        }
        else if(geekScore >= 0 && geekScore <= 10)
        {
            geekType.setText(getString(R.string.non));
            geekdesc.setText(getString(R.string.nonDesc));
            geekPic.setImageResource(R.drawable.non_geek);

        }

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                Intent intent = new Intent(ResultActivity.this, QuizActivity.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                Intent intent = new Intent(ResultActivity.this, QuizActivity.class);
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
    }
}
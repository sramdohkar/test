/*
    Assignment HW1
    Group#19HW01.zip
    Shashank Ramdohkar, James Budday, Jeffrey Snow
 */

package com.example.group19_hw01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PublicKey;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        final EditText budgetValue = (EditText) findViewById(R.id.budgetValue);
        final RadioGroup memoryOptions = (RadioGroup) findViewById(R.id.memoryOptions);
        final RadioGroup storageOptions = (RadioGroup) findViewById(R.id.storageOptions);
        final Switch deliverySwitch = (Switch) findViewById(R.id.deliverySwitch);
        final SeekBar percentageBar = (SeekBar) findViewById(R.id.seekBar);
        final CheckBox mouse = (CheckBox) findViewById(R.id.mouse);
        final CheckBox flashDrive = (CheckBox) findViewById(R.id.flashDrive);
        final CheckBox coolingPad = (CheckBox) findViewById(R.id.coolingPad);
        final CheckBox carryingCase = (CheckBox) findViewById(R.id.carryingCase);
        final TextView percentage = (TextView) findViewById(R.id.percentage);
        Button calulate = (Button) findViewById(R.id.calculate);
        Button reset = (Button) findViewById(R.id.reset);
        final TextView totalCost = (TextView) findViewById(R.id.totalCost);
        final TextView budgetStatus = (TextView) findViewById(R.id.budgetStatus);

        percentageBar.setMax(250);
        percentageBar.setProgress(120);
        percentageBar.incrementProgressBy(50);

        percentageBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress / 50;
                progress = progress * 50;
                percentage.setText(String.valueOf(progress/10) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                budgetValue.setText("");
                memoryOptions.check(R.id.gb2);
                storageOptions.check(R.id.gb250);
                mouse.setChecked(false);
                flashDrive.setChecked(false);
                coolingPad.setChecked(false);
                carryingCase.setChecked(false);
                totalCost.setText(R.string.zeroDollar);
                budgetStatus.setText("");
                budgetStatus.setBackgroundColor(0xffffff);
                deliverySwitch.setChecked(true);
                percentageBar.setProgress(170);
                budgetValue.setError(null);
            }
        });

        calulate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(budgetValue.getText().toString().trim().equalsIgnoreCase("") ||
                        budgetValue.getText().toString().equalsIgnoreCase("."))
                    budgetValue.setError("Enter a dollar amount");
                else
                {
                    double memorySize, storageSize, cost, bdgValue;
                    int noOfAccessories = 0, tipPercent = 0;
                    boolean isDeliver = deliverySwitch.isChecked();
                    budgetValue.setError(null);
                    bdgValue = Double.parseDouble(budgetValue.getText().toString());
                    tipPercent = Integer.parseInt(percentage.getText().toString().split("%")[0]);
                    if (memoryOptions.getCheckedRadioButtonId() == R.id.gb2)
                    {
                        memorySize = 2;
                    }
                    else if (memoryOptions.getCheckedRadioButtonId() == R.id.gb4)
                    {
                        memorySize = 4;
                    }
                    else if (memoryOptions.getCheckedRadioButtonId() == R.id.gb8)
                    {
                        memorySize = 8;
                    }
                    else
                    {
                        memorySize = 16;
                    }

                    if (storageOptions.getCheckedRadioButtonId() == R.id.gb250)
                    {
                        storageSize = 250;
                    }
                    else if (storageOptions.getCheckedRadioButtonId() == R.id.gb500)
                    {
                        storageSize = 500;
                    }
                    else if (storageOptions.getCheckedRadioButtonId() == R.id.gb750)
                    {
                        storageSize = 750;
                    }
                    else
                    {
                        storageSize = 1024;
                    }

                    noOfAccessories = mouse.isChecked() ? noOfAccessories + 1 : noOfAccessories;
                    noOfAccessories = flashDrive.isChecked() ? noOfAccessories + 1 : noOfAccessories;
                    noOfAccessories = coolingPad.isChecked() ? noOfAccessories + 1 : noOfAccessories;
                    noOfAccessories = carryingCase.isChecked() ? noOfAccessories + 1 : noOfAccessories;

                    if(isDeliver)
                        cost = ((10*memorySize + .75*storageSize + 20*noOfAccessories) * (1 + (double)tipPercent/100)) + 5.95;
                    else
                        cost = ((10*memorySize + .75*storageSize + 20*noOfAccessories) * (1 + (double)tipPercent/100));

                    totalCost.setText("$" + String.format("%.2f",cost));

                    if(bdgValue >= cost)
                    {
                        budgetStatus.setText(R.string.withinBudget);
                        budgetStatus.setBackgroundColor(0xff99cc00);
                    }
                    else
                    {
                        budgetStatus.setText(R.string.overBudget);
                        budgetStatus.setBackgroundColor(0xffff4444);
                    }
                }

            }
        });
    }
}

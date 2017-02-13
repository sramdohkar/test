/**
 * Created by shashank on 07/03/2016.
 */
package com.example.group19_hw5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int REQ_CODE = 100;
    ArrayList<CityList> cityList;
    ArrayList<Note> noteList;
    ArrayList<CityList> cl;
    DatabaseDataManager dm;
    ArrayList<String> tempArray;
    String key = "1b38864edb9165f5";
    String url;
    public static final String ADD_CITY = "AddCity";
    ListView lv;
    CityAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityList = new ArrayList<CityList>();
        noteList = new ArrayList<>();
        dm = new DatabaseDataManager(MainActivity.this);
        cityList = (ArrayList) dm.getAllCities();
        noteList = (ArrayList) dm.getAllNotes();
        tempArray = new ArrayList<>();
        final TextView txtNoCity = (TextView) findViewById(R.id.txtAddCity);
        final RelativeLayout rl1 = (RelativeLayout) findViewById(R.id.rl1);
        final RelativeLayout rl2 = (RelativeLayout) findViewById(R.id.rl2);
        lv = (ListView) findViewById(R.id.listView);
        if(cityList.size() > 0) {
            rl1.setVisibility(View.INVISIBLE);
            rl2.setVisibility(View.VISIBLE);

            adapter = new CityAdapter(MainActivity.this, R.layout.row_item1, cityList);
            lv.setAdapter(adapter);
            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
            {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view,
                                               int position, long arg3) {

                    dm.deleteCity(cityList.get(position).id);
                    adapter.remove(cityList.get(position));
                    adapter.notifyDataSetChanged();
                    if(cityList.size() > 0) {
                        rl1.setVisibility(View.INVISIBLE);
                        rl2.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        rl1.setVisibility(View.VISIBLE);
                        rl2.setVisibility(View.INVISIBLE);
                    }
                    return false;
                }

            });

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Intent intent = new Intent(MainActivity.this, HourlyDataActivity.class);
                    Intent intent = new Intent(MainActivity.this, AndroidTabLayoutActivity.class);
                    intent.putExtra("CityState", cityList.get(position));
                    startActivity(intent);
                    finish();
                }
            });
        }
        else
        {
            rl1.setVisibility(View.VISIBLE);
            rl2.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.default_activity_button:
            {
                Intent intent = new Intent(MainActivity.this, AddCityActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.default_activity_button1:
            {
                dm.deleteAllCity();
                dm.deleteAllNotes();
                Toast toast = Toast.makeText(MainActivity.this, "Data Deleted Successfully", Toast.LENGTH_SHORT);
                toast.show();

                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

                break;
            }
            case R.id.default_activity_button2:
            {
                if(cityList.size() == 0)
                {
                    Toast toast = Toast.makeText(MainActivity.this, "Add city ", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(noteList.size() == 0){
                    Toast toast = Toast.makeText(MainActivity.this, "No saved notes", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    Intent intent = new Intent(MainActivity.this, ViewNotesActivity.class);
                    startActivity(intent);
                    finish();
                }

                break;
            }
            //return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
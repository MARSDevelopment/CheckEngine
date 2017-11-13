package com.example.action_laptop.checkengine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

public class GarageActivity extends AppCompatActivity {

    private ExpandableListView expandableListViewGarage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        GlobalValues globalValues = new GlobalValues(this);

        //TODO update to handle multiple cars
        CarInfo carInfo = new CarInfoDBHandler(this, null).GetCarInfoFromCarName(globalValues.Get(GlobalValues.CarInfo.CAR_NAME.toString()));
        CarInfo carInfo1 = new CarInfo();
        CarInfo carInfo2 = new CarInfo();
        final List<CarInfo> carList = new ArrayList<>();
        carList.add(carInfo);
        carList.add(carInfo1);
        carList.add(carInfo2);

        expandableListViewGarage = (ExpandableListView) findViewById(R.id.expandableListViewGarage);
        expandableListViewGarage.setAdapter(new CarInfoExpandableListAdapter(this, carList));
        expandableListViewGarage.setOnGroupClickListener(new android.widget.ExpandableListView.OnGroupClickListener(){
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //TODO Toast?
//                Toast.makeText(getBaseContext(), "Gathering Data..", Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    //region App Dropdown Menu Overrides
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        MainMenu.ActivitySwitchboard(this, item, new Intent());
        return super.onOptionsItemSelected(item);
    }
    //endregion

    //region Helper Methods
    private void ExpandAllGroups(){
        int count = expandableListViewGarage.getCount();
        for (int i = 0; i < count; i++){
            expandableListViewGarage.expandGroup(i);
        }
    }
    //endregion
}

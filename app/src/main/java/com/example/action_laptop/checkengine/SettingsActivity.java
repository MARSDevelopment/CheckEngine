package com.example.action_laptop.checkengine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ListView;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    private ArrayList<String> carItemHeaderList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        generateSettingItems();
        ListView listView = (ListView)findViewById(R.id.listViewCarSettings);
        listView.setAdapter(new CarItemArrayAdapter(this, R.layout.car_list_item, carItemHeaderList));
    }

    private void generateSettingItems(){
        for(Enum item : CarValues.CarItems.values()){
            carItemHeaderList.add(item.toString());
        }
    }

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
        Intent intent;

//        public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);

        switch (item.getItemId()){
            case R.id.action_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_notifications:
                // TODO Handle Notification Menu Item
                intent = new Intent(this, NotificationsActivity.class);
                startActivity(intent);
                break;
            default:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

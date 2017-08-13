package com.example.action_laptop.checkengine;

import android.content.Context;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {
    //Incoming Commit
    private ArrayList<String> carItemHeaderList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        generateSettingItems();
        ListView listView = (ListView)findViewById(R.id.listViewCarSettings);
        listView.setAdapter(new CarListAdapter(this, R.layout.car_list_item, carItemHeaderList));
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
//                intent = new Intent(this, SettingsActivity.class);
//                startActivity(intent);
                break;
            default:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private class CarListAdapter extends ArrayAdapter<String> {
        private int carItemLayout;

        public CarListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
            carItemLayout = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            CarListItemHolder mainCarListItemHolder;
            CarXMLHandler carXMLHandler = new CarXMLHandler();
            XmlResourceParser xmlResourceParser = getResources().getXml(R.xml.custom);
            CarValues carValues = carXMLHandler.ParseCarXMLHanlder(xmlResourceParser);


            //populate ListView with instances of car_list_item.xml
            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(carItemLayout, parent, false);

                    final CarListItemHolder carListItemHolder = new CarListItemHolder();
                    carListItemHolder.itemHeader = (TextView)convertView.findViewById(R.id.txtCarItemHeader);
                    carListItemHolder.itemValue = (TextView)convertView.findViewById(R.id.txtCarItemValue);
                    carListItemHolder.itemHeader.setText(carValues.carItemsHasMap.keySet().toArray()[position].toString());
                    carListItemHolder.itemValue.setText(carValues.carItemsHasMap.values().toArray()[position].toString());
                    carListItemHolder.itemValue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        //Defines behavior of car value inputs cursor behavior
                        {
                            carListItemHolder.itemValue.setCursorVisible(true);
                            carListItemHolder.itemValue.setFocusableInTouchMode(true);
                            carListItemHolder.itemValue.setInputType(InputType.TYPE_CLASS_NUMBER);
                            carListItemHolder.itemValue.requestFocus();
                        }
                    });

                convertView.setTag(carListItemHolder);
            } else {
                //keeps the position of the ListView
                mainCarListItemHolder = (CarListItemHolder) convertView.getTag();
                mainCarListItemHolder.itemHeader.setText(carValues.carItemsHasMap.keySet().toArray()[position].toString());
                mainCarListItemHolder.itemValue.setText(carValues.carItemsHasMap.values().toArray()[position].toString());
            }

            return convertView;
        }

        //Contains the components that'll be in each lis
        public class CarListItemHolder {
            TextView itemHeader;
            TextView itemValue;
        }
    }
}

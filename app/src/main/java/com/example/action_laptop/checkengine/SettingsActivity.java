package com.example.action_laptop.checkengine;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

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
            final String carItemHeader = GetCarHeaderByPosition(position);
            final String carItemValue = GetCarValueByPosition(position);

            //populate ListView with instances of car_list_item.xml
            if(convertView == null){
                //initialize view components
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(carItemLayout, parent, false);
                CarListItemHolder carListItemHolder = new CarListItemHolder();
                carListItemHolder.itemContainer = (TableRow)convertView.findViewById(R.id.tblRowCarItemContainer);
                carListItemHolder.itemHeader = (TextView)convertView.findViewById(R.id.txtCarItemHeader);
                carListItemHolder.itemValue = (TextView)convertView.findViewById(R.id.txtViewCarItemValue);

                //set component properties
                carListItemHolder.itemHeader.setText(carItemHeader);
                carListItemHolder.itemValue.setText(carItemValue);
                carListItemHolder.itemContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        //initialize dialog box components
                        v = LayoutInflater.from(SettingsActivity.this).inflate(R.layout.car_item_input_dialog, null);
                        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                        final CarInputDialog carInputDialog = new CarInputDialog();
                        carInputDialog.carInputHeader = (TextView) v.findViewById(R.id.txtViewCarDialogItemHeader);
                        carInputDialog.carInputValue = (EditText) v.findViewById(R.id.txtEditCarDialogItemValue);

                        //set dialog box components' properties and behaviors
                        carInputDialog.carInputHeader.setText(carItemHeader);
                        carInputDialog.carInputValue.setText(carItemValue);
                        builder.setMessage("Edit Mileage Value")
                                .setView(v)
                                .setPositiveButton(R.string.global_save ,new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which){
                                        //set behavior for when Save is clicked
                                        UpdateCarXMLFile(carItemHeader, carInputDialog.carInputValue.getText());

                                    }
                                })
                                .setNegativeButton(R.string.global_cancel, null);
                        AlertDialog carInputAlertDialog = builder.create();
                        carInputAlertDialog.show();
                    }
                });



//                    carListItemHolder.itemValue.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v)
//                        //Defines behavior of car value inputs cursor behavior
//                        {
////                            carListItemHolder.itemValue.setInputType(InputType.TYPE_CLASS_NUMBER);
//
////                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
////                            imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
//
////                                carListItemHolder.itemValue.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
//                            carListItemHolder.itemValue.setCursorVisible(true);
//                            carListItemHolder.itemValue.setFocusableInTouchMode(true);
//                            carListItemHolder.itemValue.requestFocus();
////                            carListItemHolder.itemValue.setRawInputType(Configuration.KEYBOARD_QWERTY);
////                            carListItemHolder.itemValue.setRawInputType(InputType.TYPE_CLASS_NUMBER);
//                        }
//                    });

                convertView.setTag(carListItemHolder);
            } else {
                //keeps the position of the ListView items
                mainCarListItemHolder = (CarListItemHolder) convertView.getTag();
                mainCarListItemHolder.itemHeader.setText(carItemHeader);
                mainCarListItemHolder.itemValue.setText(carItemValue);
            }

            return convertView;
        }

        //Contains the components that'll be in each ListView item
        private class CarListItemHolder {
            TableRow itemContainer;
            TextView itemHeader;
            TextView itemValue;
            TextView itemExtension;
        }

        private class CarInputDialog {
            TextView carInputHeader;
            EditText carInputValue;
        }
    }

    //TODO Refactor to grab the xml file based on selected configuration
    private void UpdateCarXMLFile(String header, Editable value) {
        if(Validator.ValidateNumericInput(value.toString())){
            XmlResourceParser xmlResourceParser = getResources().getXml(R.xml.custom);
            new CarXMLHandler().UpdateCarXMLFile(xmlResourceParser, header, Integer.parseInt(value.toString()));
        }
    }



    //TODO Refactor into CarXMLHandler
    public String GetCarHeaderByPosition(int position){
        CarXMLHandler carXMLHandler = new CarXMLHandler();
        XmlResourceParser xmlResourceParser = getResources().getXml(R.xml.custom);
        CarValues carValues = carXMLHandler.ParseCarXMLHandler(xmlResourceParser);
        return carValues.carItemsHasMap.keySet().toArray()[position].toString();
    }

    //TODO Refactor into CarXMLHandler
    public String GetCarValueByPosition(int position){
        CarXMLHandler carXMLHandler = new CarXMLHandler();
        XmlResourceParser xmlResourceParser = getResources().getXml(R.xml.custom);
        CarValues carValues = carXMLHandler.ParseCarXMLHandler(xmlResourceParser);
        return carValues.carItemsHasMap.values().toArray()[position].toString();
    }
}

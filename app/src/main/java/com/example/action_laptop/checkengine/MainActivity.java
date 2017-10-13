package com.example.action_laptop.checkengine;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //populates upcoming repairs
        ListView listView = (ListView)findViewById(R.id.listViewUpcomingRepairs);
        listView.setAdapter(new CarItemArrayAdapter(this, R.layout.car_list_item, CarValues.GetCarItemList(), RepairScheduleTable.TABLE_NAME, RepairScheduleTable.TableColumns.NAME_COLUMN.toString(), "Default"));

        //dialog pop up for updating current mileage
        LinearLayout linearLayoutCurrentMileageContainer = (LinearLayout) findViewById(R.id.linearLayoutCurrentMilageContainer);
        linearLayoutCurrentMileageContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialize dialog box components
                v = LayoutInflater.from(MainActivity.this).inflate(R.layout.car_item_input_dialog, null);
                final ComponentContainers.CarInputDialog carInputDialog = new ComponentContainers.CarInputDialog();
                carInputDialog.carInputHeader = (TextView) v.findViewById(R.id.txtViewCarDialogItemHeader);
                carInputDialog.carInputValue = (EditText) v.findViewById(R.id.txtEditCarDialogItemValue);

                //set dialog box components' properties and behaviors
                carInputDialog.carInputHeader.setText(Html.fromHtml("<u>"+getResources().getString(R.string.home_current_mileage)+"</u>"));
                carInputDialog.carInputValue.setText("50000");
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(v)
                        .setPositiveButton(R.string.global_save, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                //TODO set behavior for when Save is clicked
                            }
                        })
                        .setNegativeButton(R.string.global_cancel, null);
                AlertDialog carInputAlertDialog = builder.create();
                carInputAlertDialog.show();
            }
        });
    }

    //region App Dropdown Overrides

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

    //endregion  Overrides
}

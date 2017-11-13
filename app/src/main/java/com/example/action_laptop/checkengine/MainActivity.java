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
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextSwitcher;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private CarInfoDBHandler carInfoDBHandler;
    private GlobalValues globalValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        carInfoDBHandler = new CarInfoDBHandler(MainActivity.this, null);
        globalValues = new GlobalValues(MainActivity.this);

        //region Current Mileage Section
        //add animation events and set text
        final TextSwitcher txtSwitcherCurrentMileage = (TextSwitcher) findViewById(R.id.txtSwitcherHomeCurrentMileageValue);
        txtSwitcherCurrentMileage.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        txtSwitcherCurrentMileage.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
        txtSwitcherCurrentMileage.setText(globalValues.Get(GlobalValues.CarInfo.CURRENT_MILEAGE.toString()));

        //puts event listener on section that contains the current mileage.
        LinearLayout linearLayoutCurrentMileageContainer = (LinearLayout) findViewById(R.id.linearLayoutCurrentMilageContainer);
        linearLayoutCurrentMileageContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialize dialog box
                v = LayoutInflater.from(MainActivity.this).inflate(R.layout.car_item_input_dialog, null);
                //set dialog box components' properties and behaviors
                final ComponentContainers.InputDialog inputDialog = new ComponentContainers.InputDialog(v);
                inputDialog.txtViewInputHeader.setText(Html.fromHtml("<u>"+getResources().getString(R.string.home_current_mileage)+"</u>"));
                inputDialog.editViewInputValue.setText(globalValues.Get(GlobalValues.CarInfo.CURRENT_MILEAGE.toString()));
                //pop up dialog behavior for updating current mileage
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(v)
                        .setPositiveButton(R.string.global_save, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                            //behavior for when Save is clicked
                                if(Validator.TryParseToInt(inputDialog.editViewInputValue.getText().toString())){
                                    UpdateCarMileage(Integer.parseInt(inputDialog.editViewInputValue.getText().toString()));
                                } else {
                                    //TODO behavior for if the text doesn't parse as int
                                }
                            }
                        })
                        .setNegativeButton(R.string.global_cancel, null)
                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            //TODO works for now, but may need to start converting Activities to run on threads so UI updates can happen more fluidly
                            @Override
                            public void onDismiss(final DialogInterface arg0) {
                                try {
                                    //delay animation
                                    Thread.sleep(500);
                                    txtSwitcherCurrentMileage.setText(globalValues.Get(GlobalValues.CarInfo.CURRENT_MILEAGE.toString()));
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });;
                AlertDialog carInputAlertDialog = builder.create();
                carInputAlertDialog.show();
            }
        });
        //endregion

        //region Upcoming Repairs
        //populates upcoming repairs
        ListView listView = (ListView)findViewById(R.id.listViewUpcomingRepairs);
        listView.setAdapter(new CarItemArrayAdapter(this, R.layout.car_list_item, CarValues.GetCarItemList(), RepairScheduleTable.TABLE_NAME, RepairScheduleTable.TableColumns.NAME_COLUMN.toString(),
                globalValues.Get(GlobalValues.CarInfo.CAR_NAME.toString())));
        //endregion
    }

    //region Dropdown Menu Overrides
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
    public void UpdateCarMileage(int currentMileage){
        carInfoDBHandler.UpdateCarCurrentMileage(new GlobalValues(this).Get(GlobalValues.CarInfo.CAR_NAME.toString()), currentMileage);
        globalValues.Set(GlobalValues.CarInfo.CURRENT_MILEAGE.toString(), String.valueOf(currentMileage));
    }

    //endregion
}

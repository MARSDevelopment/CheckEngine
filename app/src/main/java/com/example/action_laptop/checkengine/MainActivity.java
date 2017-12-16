package com.example.action_laptop.checkengine;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextSwitcher;

import java.util.LinkedHashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private CarInfoDBHandler carInfoDBHandler;
    private GlobalValues globalValues;
    private ListView upcomingRepairListView;
    private RecyclerView upcomingRepairRcyView;
    private UpcomingRepairArrayAdapter upcomingRepairArrayAdapter;
    private UpcomingRepairRecycleViewAdapter upcomingRepairRecycleViewAdapter;
    private CarValuesDBHandler carValuesDBHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        carInfoDBHandler = new CarInfoDBHandler(MainActivity.this, null);
        globalValues = new GlobalValues(MainActivity.this);
        carValuesDBHandler = new CarValuesDBHandler(MainActivity.this, null);

        //region Current Mileage Section
        //add animation events and set text
        final TextSwitcher txtSwitcherCurrentMileage = (TextSwitcher) findViewById(R.id.txtSwitcherHomeCurrentMileageValue);
        txtSwitcherCurrentMileage.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        txtSwitcherCurrentMileage.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
        txtSwitcherCurrentMileage.setText(globalValues.Get(GlobalValues.CarInfo.CURRENT_MILEAGE.toString()));

        //puts event listener on section that contains the current mileage.
        LinearLayout linLayoutCurrentMileageContainer = (LinearLayout) findViewById(R.id.linLayoutCurrentMileageContainer);
        linLayoutCurrentMileageContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialize dialog box then set dialog box components' properties and behaviors
                v = LayoutInflater.from(MainActivity.this).inflate(R.layout.car_item_input_dialog, null);
                final ComponentContainers.SingleInputDialog singleInputDialog = new ComponentContainers.SingleInputDialog(v);
                singleInputDialog.inputHeader.setText(Html.fromHtml("<u>"+getResources().getString(R.string.home_current_mileage)+"</u>"));
                singleInputDialog.inputValue.setText(globalValues.Get(GlobalValues.CarInfo.CURRENT_MILEAGE.toString()));
                //pop up dialog behavior for updating current mileage
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final Handler handler = new Handler();
                builder.setView(v)
                        .setPositiveButton(R.string.global_save, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                            //behavior for when Save is clicked
                                if(Validator.TryParseToInt(singleInputDialog.inputValue.getText().toString())){
                                    UpdateCarMileage(Integer.parseInt(singleInputDialog.inputValue.getText().toString()));
                                    txtSwitcherCurrentMileage.setText(globalValues.Get(GlobalValues.CarInfo.CURRENT_MILEAGE.toString()));

//                                    upcomingRepairRecycleViewAdapter.ClearItems();

//                                    try {
//                                        for(int i = upcomingRepairRcyView.getChildCount() - 1; i >= 0; i--) {
//                                        while (upcomingRepairListView.getChildCount() != 0) {
////                                            handler.postDelayed(new Runnable() {
////                                                @Override
////                                                public void run() {
////                                            v.animate().setDuration(500).x(-view.getWidth()).alpha(0f);
////                                                    upcomingRepairArrayAdapter.remove(upcomingRepairArrayAdapter.getItem(0));
                                                        upcomingRepairRecycleViewAdapter.RemoveItem(0);
//
////                                                    upcomingRepairArrayAdapter.notifyDataSetChanged();
////                                                }
////                                            }, 500);
//                                        }
//                                    } catch (Exception e){
//                                        Exception ex;
//                                        ex = e;
//                                        //TODO remove when done
//                                    }

                                    if(upcomingRepairRcyView != null)
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                SetUpcomingRepairList();
                                            }
                                        }, 5000);
                                } else {
                                    //TODO behavior for if the text doesn't parse as int
                                }
                            }
                        })
                        .setNegativeButton(R.string.global_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                txtSwitcherCurrentMileage.setText(globalValues.Get(GlobalValues.CarInfo.CURRENT_MILEAGE.toString()));
                            }
                        })
                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            //TODO works for now, but may need to start converting Activities to run on threads so UI updates can happen more fluidly
                            @Override
                            public void onDismiss(final DialogInterface arg0) {    }
                        });
                AlertDialog carInputAlertDialog = builder.create();
                carInputAlertDialog.show();
            }
        });
        //endregion

        SetUpcomingRepairList();
    }

    private void SetUpcomingRepairList(){
        //region Upcoming Repairs

        //region Algorithm to determine which repairs to flag
        Map<String, Integer> upcomingRepairs = new LinkedHashMap<>();
        Map<Enum, Object> lastRepaired = (carValuesDBHandler.GetRowFrom(LastRepairedTable.TABLE_NAME, LastRepairedTable.TableColumns.RELATED_REPAIR_SCHEDULE_NAME_COLUMN.toString(), globalValues.Get(GlobalValues.CarInfo.CAR_NAME.toString()))).carItemsHashMap;
        Map<Enum, Object> repairSchedule = (carValuesDBHandler.GetRowFrom(RepairScheduleTable.TABLE_NAME, RepairScheduleTable.TableColumns.NAME_COLUMN.toString(), globalValues.Get(GlobalValues.CarInfo.CAR_NAME.toString()))).carItemsHashMap;
        int notificationThreshold = Integer.parseInt(globalValues.Get(NotificationsTable.TableColumns.MILEAGE_THRESHOLD_COLUMN.toString()));
        int currentMileage = Integer.parseInt(globalValues.Get(GlobalValues.CarInfo.CURRENT_MILEAGE.toString()));

        for (Map.Entry<Enum, Object> lastRepairedEntry : lastRepaired.entrySet()){
            int repairScheduleValue = (int)repairSchedule.get(lastRepairedEntry.getKey());
            int lastRepairedValue = (int)lastRepairedEntry.getValue();
            int remainingMileage = (lastRepairedValue + repairScheduleValue) - currentMileage;

            if (remainingMileage <= notificationThreshold)
                upcomingRepairs.put(lastRepairedEntry.getKey().toString(), remainingMileage);
        }
        //endregion

        //TODO remove CarItemArrayAdapter when sure it's no longer useful (check RecyclerView api usage)
//        upcomingRepairListView = (ListView) findViewById(R.id.listViewUpcomingRepairs);
//        upcomingRepairArrayAdapter = new UpcomingRepairArrayAdapter(this, R.layout.upcoming_repair_item, new ArrayList<>(upcomingRepairs.keySet()), globalValues.Get(GlobalValues.CarInfo.CAR_NAME.toString()),
//                upcomingRepairs);
//        upcomingRepairListView.setAdapter(upcomingRepairArrayAdapter);

        upcomingRepairRcyView = (RecyclerView) findViewById(R.id.rcyViewUpcomingRepairs);
        upcomingRepairRecycleViewAdapter = new UpcomingRepairRecycleViewAdapter(this, upcomingRepairs);
        UpcomingRepairRecyclerViewAnimator animator1 = new UpcomingRepairRecyclerViewAnimator(this);
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(2000);
        animator.setRemoveDuration(2000);
        upcomingRepairRcyView.setLayoutManager(new LinearLayoutManager(this));
        upcomingRepairRcyView.setAdapter(upcomingRepairRecycleViewAdapter);
        upcomingRepairRcyView.setItemAnimator(animator1);


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

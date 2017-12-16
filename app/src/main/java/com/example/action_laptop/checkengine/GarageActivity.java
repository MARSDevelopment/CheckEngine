package com.example.action_laptop.checkengine;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;

public class GarageActivity extends AppCompatActivity {
    //region Variables
    private ExpandableListView garageELView;
    private CarInfoDBHandler carInfoDBHandler;
    private List<CarInfo> carList;
    private CarInfoExpandableListAdapter carInfoELAdapter;
    private enum Mode {CREATE, UPDATE, DELETE}
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //region Add A Ride
        TableRow tblrAddCar = (TableRow) findViewById(R.id.tblrAddCar);
        tblrAddCar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) { ShowAddEditDialog(null); }});
        //endregion

        //region Populate Car List
        carInfoDBHandler = new CarInfoDBHandler(this, null);
        carList = new ArrayList<>();
        List<String> carNames = carInfoDBHandler.GetAllCarNamesInDatabase();
        for(String name : carNames)
            carList.add(carInfoDBHandler.GetCarInfoFromCarName(name));

        //the list item behaviors are handled in the list adapter.
        //editing and deleting cars handled in long click listener
        garageELView = (ExpandableListView) findViewById(R.id.expandableListViewGarage);
        carInfoELAdapter = new CarInfoExpandableListAdapter(this, carList);
        garageELView.setAdapter(carInfoELAdapter);
//        garageELView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                if (garageELView.isGroupExpanded(groupPosition)){
//                    garageELView.collapseGroup(groupPosition);
//                }else {
//                    garageELView.expandGroup(groupPosition, false);
//                    Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
//                    garageELView.getChildAt(groupPosition).setAnimation(slide_down);
//                }
//                return true;
//            }
//@Override
//public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//    // We call collapseGroupWithAnimation(int) and
//    // expandGroupWithAnimation(int) to animate group
//    // expansion/collapse.
//    if (listView.isGroupExpanded(groupPosition)) {
//        listView.collapseGroupWithAnimation(groupPosition);
//        previousGroup=-1;
//    } else {
//        listView.expandGroupWithAnimation(groupPosition);
//        if(previousGroup!=-1){
//            listView.collapseGroupWithAnimation(previousGroup);
//        }
//        previousGroup=groupPosition;
//    }
//
//    return true;
//}
//        });

        garageELView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //detect long click on group(parent) item and return true to prevent other listeners from firing
                if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    view = LayoutInflater.from(GarageActivity.this).inflate(R.layout.car_info_edit_delete_popup, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(GarageActivity.this);
                    builder.setView(view);
                    final AlertDialog carInfoInputAlertDialog = builder.create();
                    carInfoInputAlertDialog.show();

                    ComponentContainers.CarInfoEditDeletePopup editDeleteInputDialog = new ComponentContainers.CarInfoEditDeletePopup(view);
                    editDeleteInputDialog.title.setText(carList.get(position).getName());
                    editDeleteInputDialog.deleteBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            carInfoInputAlertDialog.dismiss();

                            if (carList.size() > 1) {
                                if(!carList.get(position).getInUse()) {
                                    //region Deletion Confirmation Dialog
                                    //TODO cosmetics
                                    AlertDialog.Builder builder = new AlertDialog.Builder(GarageActivity.this);
                                    builder.setTitle("Are You Sure?")
                                            .setPositiveButton(R.string.global_delete, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //remove all data from database associate with the car
                                                    PerformTaskOnCarInfo(carList.get(position), Mode.DELETE, new Callable() {
                                                        @Override
                                                        public Object call() throws Exception {
                                                            carInfoDBHandler.DeleteAllAssociatedWithCarName(carList.get(position).getName());
                                                            return null;
                                                        }
                                                    });
                                                }
                                            })
                                            .setNegativeButton(R.string.global_cancel, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            });
                                    builder.create().show();
                                    //endregion
                                } else {
                                    //Cannot delete car in use - App can't handle it currently
                                    Toast restrictDeletionToast = Toast.makeText(GarageActivity.this, "Cannot delete cars in use", Toast.LENGTH_LONG);
                                    restrictDeletionToast.setGravity(Gravity.CENTER, 0, 0);
                                    restrictDeletionToast.show();
                                }
                            } else {
                                //Cannot delete when only one car left - App can't handle it currently
                                Toast restrictDeletionToast = Toast.makeText(GarageActivity.this, "Cannot Delete. \n One car must always be in the garage.", Toast.LENGTH_LONG);
                                restrictDeletionToast.setGravity(Gravity.CENTER, 0, 0);
                                restrictDeletionToast.show();
                            }
                        }
                    });
                    editDeleteInputDialog.editBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            carInfoInputAlertDialog.dismiss();

                            //region Edit Ride Dialog
                            ShowAddEditDialog(carList.get(position));
                            //endregion
                        }
                    });
                    //Again - return true to prevent other listeners from firing
                    return true;
                }
                return false;
            }
        });
        //endregion
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
    //execute actions relevant to the car info and mode provided. Notify user with toasts about process
    private void PerformTaskOnCarInfo(@NonNull final CarInfo carInfo, @NonNull final Mode mode, @NonNull Callable CarInfoFunction) {
        final String operation;

        switch (mode){
            case CREATE: operation = "Creat"; break;
            case UPDATE: operation = "Updat"; break;
            case DELETE: operation = "Delet"; break;
            default: throw new IllegalArgumentException();
        }

        try {
            Toast.makeText(GarageActivity.this, String.format("%sing...", operation), Toast.LENGTH_SHORT).show();
            CarInfoFunction.call();

            if(mode == Mode.CREATE) carList.add(carInfo);
            if(mode == Mode.UPDATE) {
                for (int i = 0; i < carList.size(); i++)
                    if (carList.get(i).getName().equals(carInfo.getName()))
                        carList.set(i, carInfo);
            }
            if(mode == Mode.DELETE) carList.remove(carInfo);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(GarageActivity.this, String.format("%s Successfully %sed!", carInfo.getName(), operation), Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            carInfoELAdapter.notifyDataSetChanged();
                        }
                    }, 1500);
                }
            }, 1000);
        } catch (Exception e) {
            //TODO Handle exception
            e.printStackTrace();
        }
    }

    private void ExpandAllGroups(){
        int count = garageELView.getCount();
        for (int i = 0; i < count; i++){
            garageELView.expandGroup(i);
        }
    }

    private void DeleteRide(@NonNull String carName){

    }
    //endregion

    //region Add/Edit Ride Dialog
    private void ShowAddEditDialog(CarInfo carInfo){
        View v = LayoutInflater.from(GarageActivity.this).inflate(R.layout.new_car_info_input_dialog, null);
        ComponentContainers.CarInfoInputDialog carInfoInputDialogBody = new ComponentContainers.CarInfoInputDialog(v);

        if(carInfo != null){
            //set input field values with car info and hide car name row to prevent editing
            carInfoInputDialogBody.carNameValue.setText(carInfo.getName());
            carInfoInputDialogBody.carNameValue.setFocusable(false);
            carInfoInputDialogBody.carNameContainer.setVisibility(View.GONE);
            carInfoInputDialogBody.carMileageValue.setText(String.valueOf(carInfo.getCurrentMileage()));
            carInfoInputDialogBody.carMakeValue.setText(carInfo.getMake());
            carInfoInputDialogBody.carModelValue.setText(carInfo.getModel());
            carInfoInputDialogBody.carYearValue.setText(String.valueOf(carInfo.getYear()));
        }

        //Initialize Add/Edit Dialog
        //IMPORTANT - Validation and dialog behavior handled in CarInfoDialogSaveListener
        AlertDialog.Builder builder = new AlertDialog.Builder(GarageActivity.this);
        builder.setView(v)
                .setPositiveButton(R.string.global_save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {  }})
                .setNegativeButton(R.string.global_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog carInputAlertDialog = builder.create();
        carInputAlertDialog.show();
        //set CarInfoDialogSaveListener as positive button click event
        Button positiveBtn = carInputAlertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveBtn.setOnClickListener(new CarInfoDialogSaveListener(carInputAlertDialog, carInfoInputDialogBody, carInfo == null ? null : carInfo.getName(), carInfo == null));
    }
    //endregion

    //region Custom OnClickListener class designed specifically to validate and handle the Add/Edit Ride Dialog
    private class CarInfoDialogSaveListener implements View.OnClickListener {
        //region Variables
        private Dialog dialog;
        private ComponentContainers.CarInfoInputDialog inputDialogBody;
        private boolean isNew;
        private String oldCarName;
        //endregion

        //region Constructors
        private CarInfoDialogSaveListener(@NonNull Dialog dialog, @NonNull ComponentContainers.CarInfoInputDialog inputDialogBody, String oldCarName, boolean isNew) {
            this.dialog = dialog;
            this.inputDialogBody = inputDialogBody;
            this.isNew = isNew;
            this.oldCarName = oldCarName;
        }
        //endregion

        @Override
        public void onClick(View v) {
            //region Validates input fields from dialog containing info to add new car
            String carName = inputDialogBody.carNameValue.getText().toString();
            String carMileage = inputDialogBody.carMileageValue.getText().toString();
            String carMake = inputDialogBody.carMakeValue.getText().toString();
            String carModel = inputDialogBody.carModelValue.getText().toString();
            String carYear = inputDialogBody.carYearValue.getText().toString();
            boolean error = false;

            if(carName.length() == 0) {
                inputDialogBody.carNameValue.setError("Field cannot be blank");
                error = true;
            }else if(carName.length() > 24){
                inputDialogBody.carNameValue.setError("Cannot contain more than 24 characters");
                error = true;
            }else if(isNew && !IsNameInList(carName, carInfoDBHandler.GetAllCarNamesInDatabase())){
                inputDialogBody.carNameValue.setError("This name is already in use. (Ignores Case)");
                error = true;
            }else if(!isNew && !oldCarName.equals(carMake)){
                inputDialogBody.carNameValue.setError("Car name must remain the same when editing.");
            }

            if(carMileage.length() == 0){
                inputDialogBody.carMileageValue.setError("Field cannot be blank");
                error = true;
            }else if(!Validator.TryParseToInt(carMileage)){
                inputDialogBody.carMileageValue.setError("This field can only contain numbers");
                error = true;
            }else if(carMileage.length() > 7){
                inputDialogBody.carMileageValue.setError("Your mileage cannot exceed 9,999,999");
                error = true;
            }

            if(carMake.length() == 0){
                inputDialogBody.carMakeValue.setError("Field cannot be blank");
                error = true;
            }else if(carMake.length() > 24){
                inputDialogBody.carMakeValue.setError("Cannot contain more than 24 characters");
                error = true;
            }

            if(carModel.length() == 0){
                inputDialogBody.carModelValue.setError("Field cannot be blank");
                error = true;
            }else if(carModel.length() > 24){
                inputDialogBody.carModelValue.setError("Cannot contain more than 24 characters");
                error = true;
            }

            if(carYear.length() == 0){
                inputDialogBody.carYearValue.setError("Field cannot be blank");
                error = true;
            }else if(!Validator.TryParseToInt(carYear)) {
                inputDialogBody.carYearValue.setError("This field can only contain numbers");
                error = true;
            }else if(carYear.length() != 4){
                inputDialogBody.carYearValue.setError("Please enter a four digit year. Ex: 2007");
                error = true;
            }else if(Integer.valueOf(carYear) < 1885){
                inputDialogBody.carYearValue.setError("Cars weren't really around then");
                error = true;
            }else if(Integer.valueOf(carYear) > Calendar.getInstance().get(Calendar.YEAR) + 1){
                inputDialogBody.carYearValue.setError("You do not have a future car");
                error = true;
            }
            //endregion

            //process new ride
            if(!error){
                final CarInfo carInfo = new CarInfo();
                carInfo.setName(inputDialogBody.carNameValue.getText().toString());
                carInfo.setCurrentMileage(Integer.valueOf(inputDialogBody.carMileageValue.getText().toString()));
                carInfo.setMake(inputDialogBody.carMakeValue.getText().toString());
                carInfo.setModel(inputDialogBody.carModelValue.getText().toString());
                carInfo.setYear(Integer.valueOf(inputDialogBody.carYearValue.getText().toString()));

                if(isNew)
                    PerformTaskOnCarInfo(carInfo, Mode.CREATE, new Callable() {
                        @Override
                        public Object call() throws Exception {
                            carInfoDBHandler.CreateNewCarWithDefaultRepairs(carInfo);
                            return null;
                        }
                    });
                else
                    PerformTaskOnCarInfo(carInfo, Mode.UPDATE, new Callable() {
                        @Override
                        public Object call() throws Exception {
                            carInfoDBHandler.UpdateAllCarInfo(carInfo);
                            return null;
                        }
                    });

                dialog.dismiss();
            }
        }

        private boolean IsNameInList(String nameToFind, List<String> names) {
            for(String name : names){
                if(name.equalsIgnoreCase(nameToFind))
                    return false;
            }
            return true;
        }
    }
    //endregion
}

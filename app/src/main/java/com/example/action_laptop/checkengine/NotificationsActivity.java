package com.example.action_laptop.checkengine;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextSwitcher;

import java.util.List;

public class NotificationsActivity extends AppCompatActivity {

    private NotificationsDBHandler notificationsDBHandler;
    private GlobalValues globalValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            notificationsDBHandler = new NotificationsDBHandler(this);
            globalValues = new GlobalValues(NotificationsActivity.this);

            //region Notification Threshold
            //add animation events and set text to mileage threshold
            final TextSwitcher txtSwitcherMileageThresholdValue = (TextSwitcher) findViewById(R.id.txtSwitcherMileageThresholdValue);
            txtSwitcherMileageThresholdValue.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
            txtSwitcherMileageThresholdValue.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
            txtSwitcherMileageThresholdValue.setText(globalValues.Get(NotificationsTable.TableColumns.MILEAGE_THRESHOLD_COLUMN.toString()));
            txtSwitcherMileageThresholdValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //initialize dialog box
                    v = LayoutInflater.from(NotificationsActivity.this).inflate(R.layout.car_item_input_dialog, null);
                    //set dialog box components' properties and behaviors
                    final ComponentContainers.SingleInputDialog singleInputDialogBody = new ComponentContainers.SingleInputDialog(v);
                    singleInputDialogBody.inputHeader.setText(Html.fromHtml("<u>"+getResources().getString(R.string.notification_frequency)+"</u>"));
                    singleInputDialogBody.inputValue.setText(globalValues.Get(NotificationsTable.TableColumns.MILEAGE_THRESHOLD_COLUMN.toString()));
                    //pop up dialog behavior for updating current mileage
                    AlertDialog.Builder builder = new AlertDialog.Builder(NotificationsActivity.this);
                    builder.setView(v)
                            .setPositiveButton(R.string.global_save, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    //IMPORTANT - Validation and dialog behavior handled in MileageThresholdSaveListener
                                }
                            })
                            .setNegativeButton(R.string.global_cancel, null)
                            .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(final DialogInterface arg0) {
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            //delay for dramatic effect
                                            txtSwitcherMileageThresholdValue.setText(globalValues.Get(NotificationsTable.TableColumns.MILEAGE_THRESHOLD_COLUMN.toString()));
                                        }
                                    }, 500);
                                }
                            });
                    AlertDialog carInputAlertDialog = builder.create();
                    carInputAlertDialog.show();
                    //set MileageThresholdSaveListener as positive button click event
                    Button positiveBtn = carInputAlertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    positiveBtn.setOnClickListener(new MileageThresholdSaveListener(carInputAlertDialog, singleInputDialogBody));
                }
            });
            //endregion

            //region Notification Frequency
            //set notification frequency dropdown list. this uses two different custom xml layouts. the array adapter layout is used during its static state, the other layout is used for the dropdown.
            final Spinner spinnerFrequencyNotification = (Spinner) findViewById(R.id.spinnerFrequencyNotification);
            List<String> notificationFrequencyItems = NotificationValues.GetNotificationFrequencyList();
            ArrayAdapter<String> notificationFrequencyAdapter = new ArrayAdapter<>(this, R.layout.notification_frequency_spinner_item_header, notificationFrequencyItems);
            notificationFrequencyAdapter.setDropDownViewResource(R.layout.notification_frequency_spinner_item);
            spinnerFrequencyNotification.setAdapter(notificationFrequencyAdapter);
            spinnerFrequencyNotification.setSelection(Integer.parseInt(globalValues.Get(NotificationsTable.TableColumns.FREQUENCY_COLUMN.toString())));
            //.post() added to ensure the spinner is tapped before firing the listener. previously, listener fired after being instantiated for some reason
            spinnerFrequencyNotification.post(new Runnable() {
                @Override
                public void run() {
                    spinnerFrequencyNotification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            SaveNotificationValue(NotificationsTable.TableColumns.FREQUENCY_COLUMN.toString(), position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) { }
                    });
                }
            });

            //endregion

            //region Lock Screen Notification
            CheckBox chkBoxLockScreenNotifications = (CheckBox) findViewById(R.id.chkBoxLockScreenNotifications);
            chkBoxLockScreenNotifications.setChecked(Boolean.valueOf(globalValues.Get(NotificationsTable.TableColumns.LOCK_SCREEN_COLUMN.toString())));
            chkBoxLockScreenNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    SaveNotificationValue(NotificationsTable.TableColumns.LOCK_SCREEN_COLUMN.toString(), isChecked);
                }
            });
            //endregion

            //region Android Notification Bar
            CheckBox chkBoxAndroidNotificationBar = (CheckBox) findViewById(R.id.chkBoxAndroidNotificationBar);
            chkBoxAndroidNotificationBar.setChecked(Boolean.valueOf(globalValues.Get(NotificationsTable.TableColumns.ANDROID_BAR_COLUMN.toString())));
            chkBoxAndroidNotificationBar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    SaveNotificationValue(NotificationsTable.TableColumns.ANDROID_BAR_COLUMN.toString(), isChecked);
                }
            });
            //endregion

        } catch (Exception ex){
            //TODO Handle Exception
            Exception e = ex;
        }
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
    private void SaveNotificationValue(String tableColumn, int value){
        notificationsDBHandler.UpdateNotificationSetting(tableColumn, value);
        globalValues.Set(tableColumn, String.valueOf(value));
    }

    private void SaveNotificationValue(String tableColumn, boolean value){
        notificationsDBHandler.UpdateNotificationSetting(tableColumn, value);
        globalValues.Set(tableColumn, String.valueOf(value));
    }


    //endregion

    //region Custom OnClickListener class designed specifically to validate and handle the Mileage Threshold dialog pop up
    private class MileageThresholdSaveListener implements View.OnClickListener {
        private final Dialog dialog;
        private final ComponentContainers.SingleInputDialog singleInputDialogBody;

        public MileageThresholdSaveListener(Dialog dialog, ComponentContainers.SingleInputDialog singleInputDialogBody) {
            this.dialog = dialog;
            this.singleInputDialogBody = singleInputDialogBody;
        }

        @Override
        public void onClick(View v) {
            String mileageThreshold = singleInputDialogBody.inputValue.getText().toString();
            boolean errors = false;

            //region Dialog Input Validation
            if(mileageThreshold.length() == 0) {
                singleInputDialogBody.inputValue.setError("Please enter a value");
                errors = true;
            }else if(!Validator.TryParseToInt(mileageThreshold)) {
                singleInputDialogBody.inputValue.setError("This field can only contain numbers");
                errors = true;
            }else if(mileageThreshold.length() > 6) {
                singleInputDialogBody.inputValue.setError("Please enter a value below 999,999");
                errors = true;
            }
            //endregion

            if(!errors){
                SaveNotificationValue(NotificationsTable.TableColumns.MILEAGE_THRESHOLD_COLUMN.toString(), Integer.parseInt(singleInputDialogBody.inputValue.getText().toString()));
                dialog.dismiss();
            }
        }
    }
    //endregion
}

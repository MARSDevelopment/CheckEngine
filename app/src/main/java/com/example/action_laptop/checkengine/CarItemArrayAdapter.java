package com.example.action_laptop.checkengine;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextSwitcher;
import android.widget.TextView;

import java.util.List;

import static com.example.action_laptop.checkengine.ComponentContainers.*;

/**
 * Created by Action-Laptop on 9/13/2017.
 */

class CarItemArrayAdapter extends ArrayAdapter<String> {

    //region Variables
    private String tableName;
    private String carName;
    private int carItemLayout;
    private Context context;
    private CarValues carValues;
    //endregion

    //region Constructors
    CarItemArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects, String tableName, String tableRowCarName, String carName) {
        super(context, resource, objects);
        carItemLayout = resource;
        this.context = context;
        this.tableName = tableName;
        this.carName = carName;
        carValues = new CarValuesDBHandler(context, null).GetRowFrom(tableName, tableRowCarName, carName);
    }
    //endregion

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CarListItemHolder mainCarListItemHolder;
        final String carItemHeader = carValues.carItemsHashMap.keySet().toArray()[position].toString();
        final String carItemValue = carValues.carItemsHashMap.values().toArray()[position].toString();

        //populate ListView with instances of car_list_item.xml
        if (convertView == null) {
            //initialize view components
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(carItemLayout, parent, false);

            //set component properties
            final CarListItemHolder carListItemHolder = new CarListItemHolder(convertView);
            final View finalConvertView = convertView;
            carListItemHolder.itemHeader.setText(carItemHeader);
            carListItemHolder.itemValue.setText(carItemValue);
            carListItemHolder.itemContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //initialize dialog box components
                    v = LayoutInflater.from(context).inflate(R.layout.car_item_input_dialog, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    //set dialog box components' properties and behaviors
                    final InputDialog inputDialog = new InputDialog(v);
                    inputDialog.txtViewInputHeader.setText(carItemHeader);
                    inputDialog.editViewInputValue.setText(carItemValue);
                    builder.setView(v)
                            .setPositiveButton(R.string.global_save, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    if(Validator.TryParseToInt(inputDialog.editViewInputValue.getText().toString()))
                                        SaveValue(position, Integer.parseInt(inputDialog.editViewInputValue.getText().toString()));
                                    else {
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
                                        carListItemHolder.itemValue.setText(inputDialog.editViewInputValue.getText().toString());
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                    AlertDialog carInputAlertDialog = builder.create();
                    carInputAlertDialog.show();
                }
            });

            convertView.setTag(carListItemHolder);
        } else {
            //keeps the position of the ListView items
            mainCarListItemHolder = (CarListItemHolder) convertView.getTag();
            mainCarListItemHolder.itemHeader.setText(carItemHeader);
            mainCarListItemHolder.itemValue.setText(carItemValue);
        }

        return convertView;
    }

    //region Helper Methods

    private void SaveValue(int position, int value){
        String tableColumn = carValues.carItemsHashMap.keySet().toArray()[position].toString();
        String tableRowWithCarName;

        if (context instanceof LastRepairedActivity)
            tableRowWithCarName = LastRepairedTable.TableColumns.RELATED_REPAIR_SCHEDULE_NAME_COLUMN.toString();
        else if (context instanceof RepairScheduleActivity)
            tableRowWithCarName = RepairScheduleTable.TableColumns.NAME_COLUMN.toString();
        else
            throw new IllegalArgumentException("Could not find the table column name associated with the activity provided.");

        carValues.carItemsHashMap.values().toArray()[position] = value;
        new CarValuesDBHandler(context, null).UpdateTableColumn(tableName, carName, tableRowWithCarName, tableColumn, value);
    }

    //endregion
}

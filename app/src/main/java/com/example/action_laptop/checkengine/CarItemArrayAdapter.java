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
    private String tableRowCarName;
    private Object[] itemHeaders;
    private Object[] itemValues;
    //endregion

    //region Constructors
    public CarItemArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects, String tableName, String tableRowCarName, String carName) {
        super(context, resource, objects);

        carItemLayout = resource;
        this.context = context;
        this.tableName = tableName;
        this.carName = carName;
        this.tableRowCarName = tableRowCarName;
        CarValues carValues = new CarValuesDBHandler(context, null).GetRowFrom(tableName, tableRowCarName, carName);
        itemHeaders = carValues.carItemsHashMap.keySet().toArray();
        itemValues = carValues.carItemsHashMap.values().toArray();
    }
    //endregion

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Component Container
        final CarListItemHolder carListItemHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(carItemLayout, parent, false);
            carListItemHolder = new CarListItemHolder(convertView);
        } else {
            //keeps the position of the ListView items
            carListItemHolder = (CarListItemHolder) convertView.getTag();
        }

        //populate ListView with instances of car_list_item.xml
        carListItemHolder.itemHeader.setText(itemHeaders[position].toString());
        carListItemHolder.itemValue.setText(itemValues[position].toString());
        carListItemHolder.itemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialize dialog box components
                v = LayoutInflater.from(context).inflate(R.layout.car_item_input_dialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                //set dialog box components' properties and behaviors
                final SingleInputDialog singleInputDialog = new SingleInputDialog(v);
                singleInputDialog.inputHeader.setText(itemHeaders[position].toString());
                singleInputDialog.inputValue.setText(itemValues[position].toString());
                builder.setView(v)
                        .setPositiveButton(R.string.global_save, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                if(Validator.TryParseToInt(singleInputDialog.inputValue.getText().toString())) {
                                    itemValues[position] = Integer.valueOf(singleInputDialog.inputValue.getText().toString());
                                    new CarValuesDBHandler(context, null).UpdateTableColumn(tableName, carName, tableRowCarName, itemHeaders[position].toString(),
                                            Integer.valueOf(singleInputDialog.inputValue.getText().toString()));
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
                                    itemValues[position] = Integer.valueOf(singleInputDialog.inputValue.getText().toString());
                                    carListItemHolder.itemValue.setText(singleInputDialog.inputValue.getText().toString());
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
        return convertView;
    }

    //region Helper Methods


    //endregion
}

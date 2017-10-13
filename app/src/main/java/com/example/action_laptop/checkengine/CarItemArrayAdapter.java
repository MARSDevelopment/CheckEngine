package com.example.action_laptop.checkengine;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.XmlResourceParser;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import static com.example.action_laptop.checkengine.ComponentContainers.*;

/**
 * Created by Action-Laptop on 9/13/2017.
 */

class CarItemArrayAdapter extends ArrayAdapter<String> {

    //region Variables
    private int carItemLayout;
    private Context context;
    private CarValues carValues;
    //endregion

    //region Constructors
    CarItemArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects, String tableName, String tableRowName, String tableRowNameValue) {
        super(context, resource, objects);
        carItemLayout = resource;
        this.context = context;
        carValues = new CarValuesDBHandler(context, null).GetRowFrom(tableName, tableRowName, tableRowNameValue);
    }
    //endregion

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CarListItemHolder mainCarListItemHolder;
        final String carItemHeader = GetCarHeaderByPosition(position);
        final String carItemValue = GetCarValueByPosition(position);

        //populate ListView with instances of car_list_item.xml
        if (convertView == null) {
            //initialize view components
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(carItemLayout, parent, false);
            CarListItemHolder carListItemHolder = new CarListItemHolder();
            carListItemHolder.itemContainer = (TableRow) convertView.findViewById(R.id.tblRowCarItemContainer);
            carListItemHolder.itemHeader = (TextView) convertView.findViewById(R.id.txtCarItemHeader);
            carListItemHolder.itemValue = (TextView) convertView.findViewById(R.id.txtViewCarItemValue);

            //set component properties
            carListItemHolder.itemHeader.setText(carItemHeader);
            carListItemHolder.itemValue.setText(carItemValue);
            if(context instanceof RepairScheduleActivity) {
                carListItemHolder.itemContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //initialize dialog box components
                        v = LayoutInflater.from(context).inflate(R.layout.car_item_input_dialog, null);
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        final CarInputDialog carInputDialog = new CarInputDialog();
                        carInputDialog.carInputHeader = (TextView) v.findViewById(R.id.txtViewCarDialogItemHeader);
                        carInputDialog.carInputValue = (EditText) v.findViewById(R.id.txtEditCarDialogItemValue);

                        //set dialog box components' properties and behaviors
                        carInputDialog.carInputHeader.setText(carItemHeader);
                        carInputDialog.carInputValue.setText(carItemValue);
                        builder.setView(v)
                                .setPositiveButton(R.string.global_save, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        //TODO add value update behavior

                                    }
                                })
                                .setNegativeButton(R.string.global_cancel, null);
                        AlertDialog carInputAlertDialog = builder.create();
                        carInputAlertDialog.show();
                    }
                });
            }

            convertView.setTag(carListItemHolder);
        } else {
            //keeps the position of the ListView items
            mainCarListItemHolder = (CarListItemHolder) convertView.getTag();
            mainCarListItemHolder.itemHeader.setText(carItemHeader);
            mainCarListItemHolder.itemValue.setText(carItemValue);
        }

        return convertView;
    }

    //region Internal(private) Methods

    private String GetCarHeaderByPosition(int position) {
        return carValues.carItemsHasMap.keySet().toArray()[position].toString();
    }

    private String GetCarValueByPosition(int position) {
        return carValues.carItemsHasMap.values().toArray()[position].toString();
    }

    //endregion
}

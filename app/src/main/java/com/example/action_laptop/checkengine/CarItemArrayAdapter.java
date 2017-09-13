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

/**
 * Created by Action-Laptop on 9/13/2017.
 */

class CarItemArrayAdapter extends ArrayAdapter<String> {
    private int carItemLayout;
    private Context context;

    CarItemArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        carItemLayout = resource;
        this.context = context;
    }

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
                    builder.setMessage("Edit Mileage Value")
                            .setView(v)
                            .setPositiveButton(R.string.global_save, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    //set behavior for when Save is clicked
                                    UpdateCarXMLFile(carItemHeader, carInputDialog.carInputValue.getText());

                                }
                            })
                            .setNegativeButton(R.string.global_cancel, null);
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

    //TODO Refactor to grab the xml file based on selected configuration
    private void UpdateCarXMLFile(String header, Editable value) {
        if (Validator.ValidateNumericInput(value.toString())) {
            XmlResourceParser xmlResourceParser = context.getResources().getXml(R.xml.custom);
            CarXMLHandler.UpdateCarXMLFile(xmlResourceParser, header, Integer.parseInt(value.toString()));
        }
    }

    //TODO Refactor with below?
    private String GetCarHeaderByPosition(int position) {
        CarXMLHandler carXMLHandler = new CarXMLHandler();
        XmlResourceParser xmlResourceParser = context.getResources().getXml(GetCarXMLList());
        CarValues carValues = carXMLHandler.ParseCarXMLHandler(xmlResourceParser);
        return carValues.carItemsHasMap.keySet().toArray()[position].toString();
    }

    //TODO Refactor with above?
    private String GetCarValueByPosition(int position) {
        CarXMLHandler carXMLHandler = new CarXMLHandler();
        XmlResourceParser xmlResourceParser = context.getResources().getXml(GetCarXMLList());
        CarValues carValues = carXMLHandler.ParseCarXMLHandler(xmlResourceParser);
        return carValues.carItemsHasMap.values().toArray()[position].toString();
    }

    private int GetCarXMLList(){
            if (context instanceof MainActivity) {
                return R.xml.last_repiar;
            } else if (context instanceof SettingsActivity) {
                return R.xml.custom;
            } else{
                //TODO replace with blank list or throw exception
                return R.xml.custom;
               //throw new Exception();
            }
    }
}

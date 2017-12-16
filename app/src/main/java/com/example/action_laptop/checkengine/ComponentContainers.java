package com.example.action_laptop.checkengine;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextSwitcher;
import android.widget.TextView;

/**
 * Created by Action-Laptop on 9/14/2017.
 */

public class ComponentContainers {
    //used in conjunction with car_list_item.xml on Last Repaired and Repair Schedule Activities that contains
    //each ListView item's components
    public static class CarListItemHolder{
        TableRow itemContainer;
        TextView itemHeader;
        TextSwitcher itemValue;
        TextView itemExtension;

        public CarListItemHolder(@NonNull View v) {
            itemContainer = (TableRow) v.findViewById(R.id.tblRowCarItemContainer);
            itemHeader = (TextView) v.findViewById(R.id.txtViewCarItemHeader);
            itemValue = (TextSwitcher) v.findViewById(R.id.txtSwitcherCarItemValue);
            itemExtension = (TextView) v.findViewById(R.id.txtMileAbbreviation);
            itemValue.setInAnimation(AnimationUtils.loadAnimation(v.getContext(), android.R.anim.fade_in));
            itemValue.setOutAnimation(AnimationUtils.loadAnimation(v.getContext(), android.R.anim.fade_out));
        }
    }

    //used in conjunction with upcoming_repair_item.xml on Main Activity that contains
    //each ListView item's components
    public static class UpcomingRepairItemHolder{
        TableRow itemContainer;
        TextView itemHeader;
        TextSwitcher itemValue;
        TextView itemExtension;

        public UpcomingRepairItemHolder(@NonNull View v) {
            itemContainer = (TableRow) v.findViewById(R.id.tblRowUpcomingRepairItemHeader);
            itemHeader = (TextView) v.findViewById(R.id.txtUpcomingRepairItemHeader);
            itemValue = (TextSwitcher) v.findViewById(R.id.txtSwitcherUpcomingRepairItemValue);
            itemExtension = (TextView) v.findViewById(R.id.txtMileAbbreviation);
            itemValue.setInAnimation(AnimationUtils.loadAnimation(v.getContext(), android.R.anim.fade_in));
            itemValue.setOutAnimation(AnimationUtils.loadAnimation(v.getContext(), android.R.anim.fade_out));
        }
    }


    //used in conjunction with car_item_input_dialog.xml for single input dialog boxes found around the app
    public static class SingleInputDialog {
        TextView inputHeader;
        EditText inputValue;

        public SingleInputDialog(@NonNull View v){
            inputHeader = (TextView) v.findViewById(R.id.txtViewCarDialogItemHeader);;
            inputValue = (EditText) v.findViewById(R.id.txtEditCarDialogItemValue);
        }
    }

    //used in conjunction with new_car_info_input_dialog.xml on the Garage Activity
    //for setting up new rides to the app
    public static class CarInfoInputDialog {
        TableRow carNameContainer;
        TextView carNameHeader;
        EditText carNameValue;

        TableRow carMileageContainer;
        TextView carMileageHeader;
        EditText carMileageValue;

        TableRow carMakeContainer;
        TextView carMakeHeader;
        EditText carMakeValue;

        TableRow carModelContainer;
        TextView carModelHeader;
        EditText carModelValue;

        TableRow carYearContainer;
        TextView carYearHeader;
        EditText carYearValue;

        public CarInfoInputDialog(@NonNull View v){
            carNameContainer = (TableRow) v.findViewById(R.id.tblrCarNameInput);
            carNameHeader = (TextView) v.findViewById(R.id.txtCarNameInputHeader);
            carNameValue = (EditText) v.findViewById(R.id.edTxtCarNameInputValue);

            carMileageContainer = (TableRow) v.findViewById(R.id.tblrCarMileageInput);
            carMileageHeader = (TextView) v.findViewById(R.id.txtCarMileageInputHeader);
            carMileageValue = (EditText) v.findViewById(R.id.edTxtCarMileageInputValue);

            carMakeContainer = (TableRow) v.findViewById(R.id.tblrCarMakeInput);
            carMakeHeader = (TextView) v.findViewById(R.id.txtCarMakeInputHeader);
            carMakeValue = (EditText) v.findViewById(R.id.edTxtCarMakeInputValue);

            carModelContainer = (TableRow) v.findViewById(R.id.tblrCarModelInput);
            carModelHeader = (TextView) v.findViewById(R.id.txtCarModelInputHeader);
            carModelValue = (EditText) v.findViewById(R.id.edTxtCarModelInputValue);

            carYearContainer = (TableRow) v.findViewById(R.id.tblrCarYearInput);
            carYearHeader = (TextView) v.findViewById(R.id.txtCarYearInputHeader);
            carYearValue = (EditText) v.findViewById(R.id.edTxtCarYearInputValue);
        }
    }

    //used in conjunction with car_info_parent_item.xml on the Garage Activity that contains
    //each ExpandableListView item's parent components
    public static class CarInfoListItemParent {
        TableRow itemContainer;
        CheckBox itemInUse;
        TextView itemName;
        TextView itemMileage;
        TextView itemExt;

        public CarInfoListItemParent(@NonNull View v){
            itemContainer = (TableRow) v.findViewById(R.id.tblrCarInfoParent);
            itemInUse = (CheckBox) v.findViewById(R.id.chkCarInfoInUse);
            itemName = (TextView) v.findViewById(R.id.txtCarInfoName);
            itemMileage = (TextView) v.findViewById(R.id.txtCarInfoCurrentMileage);
            itemExt = (TextView) v.findViewById(R.id.txtMileageAbbreviation);
        }
    }

    //used in conjunction with car_info_child_item.xml on the Garage Activity that contains
    //each ExpandableListView item's child components
    public static class CarInfoListItemChild {
        TableRow carMakeContainer;
        TextView carMakeHeader;
        TextView carMakeValue;

        TableRow carModelContainer;
        TextView carModelHeader;
        TextView carModelValue;

        TableRow carYearContainer;
        TextView carYearHeader;
        TextView carYearValue;

        public CarInfoListItemChild(@NonNull View v){
            carMakeContainer = (TableRow) v.findViewById(R.id.tblrCarInfoMake);
            carMakeHeader = (TextView) v.findViewById(R.id.txtCarInfoMakeHeader);
            carMakeValue = (TextView) v.findViewById(R.id.txtCarInfoMakeValue);

            carModelContainer = (TableRow) v.findViewById(R.id.tblrCarInfoModel);
            carModelHeader = (TextView) v.findViewById(R.id.txtCarInfoModelHeader);
            carModelValue = (TextView) v.findViewById(R.id.txtCarInfoModelValue);

            carYearContainer = (TableRow) v.findViewById(R.id.tblrCarInfoYear);
            carYearHeader = (TextView) v.findViewById(R.id.txtCarInfoYearHeader);
            carYearValue = (TextView) v.findViewById(R.id.txtCarInfoYearValue);
        }
    }

    //used in conjunction with car_info_edit_delete_popup.xml on the Garage Activity that contains
    //the components in a popup dialog
    public static class CarInfoEditDeletePopup {
        TextView title;
        TextView message;
        TableRow btnContainer;
        Button editBtn;
        Button deleteBtn;

        public CarInfoEditDeletePopup(@NonNull View v){
            title = (TextView) v.findViewById(R.id.txtEditDeleteDialogHeader);
            message = (TextView) v.findViewById(R.id.txtEditDeleteDialogMessage);
            btnContainer = (TableRow) v.findViewById(R.id.tblrEditDeleteDialogBtnContainer);
            editBtn = (Button) v.findViewById(R.id.btnDeleteEditDialogEdit);
            deleteBtn = (Button) v.findViewById(R.id.btnEditDeleteDialogDelete);
        }
    }
}

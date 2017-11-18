package com.example.action_laptop.checkengine;

import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextSwitcher;
import android.widget.TextView;

/**
 * Created by Action-Laptop on 9/14/2017.
 */

public class ComponentContainers {
    //Contains the components that'll be in each ListView item
    //Currently used in conjunction with car_list_item.xml
    public static class CarListItemHolder{
        TableRow itemContainer;
        TextView itemHeader;
        TextSwitcher itemValue;
        TextView itemExtension;

        public CarListItemHolder(View v) {
            itemContainer = (TableRow) v.findViewById(R.id.tblRowCarItemContainer);
            itemHeader = (TextView) v.findViewById(R.id.txtViewCarItemHeader);
            itemValue = (TextSwitcher) v.findViewById(R.id.txtSwitcherCarItemValue);
            itemExtension = (TextView) v.findViewById(R.id.txtViewMileAbbreviation);
            itemValue.setInAnimation(AnimationUtils.loadAnimation(v.getContext(), android.R.anim.fade_in));
            itemValue.setOutAnimation(AnimationUtils.loadAnimation(v.getContext(), android.R.anim.fade_out));
        }
    }

    //Contains the components that'll be in each ListView item
    //Currently used in conjunction with upcoming_repair_item.xml
    public static class UpcomingRepairItemHolder{
        TableRow itemContainer;
        TextView itemHeader;
        TextSwitcher itemValue;
        TextView itemExtension;

        public UpcomingRepairItemHolder(View v) {
            itemContainer = (TableRow) v.findViewById(R.id.tblRowUpcomingRepairItemHeader);
            itemHeader = (TextView) v.findViewById(R.id.txtUpcomingRepairItemHeader);
            itemValue = (TextSwitcher) v.findViewById(R.id.txtSwitcherUpcomingRepairItemValue);
            itemExtension = (TextView) v.findViewById(R.id.txtViewMileAbbreviation);
            itemValue.setInAnimation(AnimationUtils.loadAnimation(v.getContext(), android.R.anim.fade_in));
            itemValue.setOutAnimation(AnimationUtils.loadAnimation(v.getContext(), android.R.anim.fade_out));
        }
    }


    //Container with a header and value. Mainly used for Input Dialog Boxes
    //Currently used in conjunction with car_item_input_dialog.xml
    public static class InputDialog {
        TextView txtViewInputHeader;
        EditText editViewInputValue;

        public InputDialog(View v){
            txtViewInputHeader = (TextView) v.findViewById(R.id.txtViewCarDialogItemHeader);;
            editViewInputValue = (EditText) v.findViewById(R.id.txtEditCarDialogItemValue);
        }
    }

//    public static

    public static class CarInfoParent {

    }

    public static class CarInfoChild {

    }
}

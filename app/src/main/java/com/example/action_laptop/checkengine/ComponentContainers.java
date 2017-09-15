package com.example.action_laptop.checkengine;

import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by Action-Laptop on 9/14/2017.
 */

public class ComponentContainers {
    //Contains the components that'll be in each ListView item
    //Currently used in conjunction with car_list_item.xml
    public static class CarListItemHolder {
        TableRow itemContainer;
        TextView itemHeader;
        TextView itemValue;
        TextView itemExtension;
    }

    //Container with a header and value. Mainly used for Input Dialog Boxes
    //Currently used in conjunction with car_item_input_dialog.xml
    public static class CarInputDialog {
        TextView carInputHeader;
        EditText carInputValue;
    }
}

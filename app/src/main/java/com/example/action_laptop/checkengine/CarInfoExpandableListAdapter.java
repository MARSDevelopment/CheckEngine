package com.example.action_laptop.checkengine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Action-Laptop on 10/19/2017.
 */

public class CarInfoExpandableListAdapter extends BaseExpandableListAdapter {

    //region Private Variables
    private Context context;
    private List<CarInfo> carList;
    private CarInfoDBHandler carInfoDBHandler;
    //endregion

    //region Constructors
    public CarInfoExpandableListAdapter(Context context, List<CarInfo> carList){
        this.context = context;
        this.carList = carList;
        carInfoDBHandler = new CarInfoDBHandler(context, null);
    }
    //endregion

    //region Override Methods
    @Override
    public int getGroupCount() {
        return carList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        //since only one child (that contains all the information we want in the dropdown) is used per parent
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return carList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return carList.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.car_info_parent_item, null);
        }

        try{
            CarInfo carInfo = (CarInfo) getGroup(groupPosition);
            TextView txtName = (TextView) convertView.findViewById(R.id.txtCarInfoName);
            TextView txtCurrentMileage = (TextView) convertView.findViewById(R.id.txtCarInfoCurrentMileage);
            CheckBox chkBoxInUse = (CheckBox) convertView.findViewById(R.id.chkBoxCarInfoInUse);

            txtName.setText(carInfo.getName());
            txtCurrentMileage.setText(String.valueOf(carInfo.getCurrentMileage()));
            //below allows for only one checkbox to be checked at a time
            chkBoxInUse.setOnCheckedChangeListener(null);
            chkBoxInUse.setChecked(carInfo.getInUse());
            chkBoxInUse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    UnsetPreviousCarInUse();
                    SetNewCarInUse(groupPosition);
                    //refresh adapter
                    notifyDataSetChanged();
                }
            });
        }catch (Exception e){
            Exception ex = e;
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
       if (convertView == null){
           LayoutInflater inflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           convertView = inflater.inflate(R.layout.car_info_child_item, null);
       }

       try{
            CarInfo carInfo = (CarInfo) getChild(groupPosition, childPosition);
            TextView txtMakeValue = (TextView) convertView.findViewById(R.id.txtCarInfoMakeValue);
            TextView txtModelValue = (TextView) convertView.findViewById(R.id.txtCarInfoModelValue);
            TextView txtYearValue = (TextView) convertView.findViewById(R.id.txtCarInfoYearValue);

            txtMakeValue.setText(carInfo.getMake());
            txtModelValue.setText(carInfo.getModel());
            txtYearValue.setText(String.valueOf(carInfo.getYear()));
       } catch (Exception e){
           Exception ex = e;
       }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    //endregion

    //region Helper Methods
    private void UnsetPreviousCarInUse() {
        //loop through the list and remove check mark on the one currently selected
        for(CarInfo car : carList) {
            if(car.getInUse()) {
                car.setInUse(false);
                carInfoDBHandler.UpdateCarInUse(car.getName(), false);
            }
        }
    }

    private void SetNewCarInUse(int position) {
        CarInfo carInfo = carList.get(position);
        GlobalValues globalValues = new GlobalValues(context);
        carInfo.setInUse(true);
        carInfoDBHandler.UpdateCarInUse(carList.get(position).getName(), true);
        globalValues.Set(GlobalValues.CarInfo.CAR_NAME.toString(), carInfo.getName());
        globalValues.Set(GlobalValues.CarInfo.CURRENT_MILEAGE.toString(), String.valueOf(carInfo.getCurrentMileage()));
    }
    //endregion
}

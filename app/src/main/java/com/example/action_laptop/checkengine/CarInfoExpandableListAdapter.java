package com.example.action_laptop.checkengine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;

import java.util.List;

/**
 * Created by Action-Laptop on 10/19/2017.
 */

public class CarInfoExpandableListAdapter extends BaseExpandableListAdapter {

    //region Variables
    private Context context;
    private List<CarInfo> carList;
    private CarInfoDBHandler carInfoDBHandler;
    private enum Mode {EXPANDED, COLLAPSED}
    private Enum modeHolder;
    //endregion

    //region Constructors
    public CarInfoExpandableListAdapter(Context context, List<CarInfo> carList){
        this.context = context;
        this.carList = carList;
        carInfoDBHandler = new CarInfoDBHandler(context, null);
        modeHolder = Mode.COLLAPSED;
    }
    //endregion

    //region Override Methods
    @Override
    public int getGroupCount() {
        return carList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        //since only one child is used per parent (it contains all the information we want in the dropdown)
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

        CarInfo carInfo = (CarInfo) getGroup(groupPosition);
        ComponentContainers.CarInfoListItemParent listItemParent = new ComponentContainers.CarInfoListItemParent(convertView);

        listItemParent.itemName.setText(carInfo.getName());
        listItemParent.itemMileage.setText(String.valueOf(carInfo.getCurrentMileage()));
        //below allows for only one checkbox to be checked at a time
        listItemParent.itemInUse.setOnCheckedChangeListener(null);
        listItemParent.itemInUse.setChecked(carInfo.getInUse());
        listItemParent.itemInUse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                UnsetPreviousCarInUse();
                SetNewCarInUse(groupPosition);
                //refresh adapter to update UI
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null){
           LayoutInflater inflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           convertView = inflater.inflate(R.layout.car_info_child_item, null);
        }

        CarInfo carInfo = (CarInfo) getChild(groupPosition, childPosition);
        ComponentContainers.CarInfoListItemChild listItemChild = new ComponentContainers.CarInfoListItemChild(convertView);

        listItemChild.carMakeValue.setText(carInfo.getMake());
        listItemChild.carModelValue.setText(carInfo.getModel());
        listItemChild.carYearValue.setText(String.valueOf(carInfo.getYear()));
//        TranslateAnimation animation;
//
//        if(modeHolder == Mode.COLLAPSED){
//            //expand
//            animation = new TranslateAnimation(0.0f, 0.0f, -convertView.getHeight(), 0.0f);
//            convertView.setVisibility(View.VISIBLE);
//        }else{
//            //collapse
//            animation = new TranslateAnimation(0.0f, 0.0f, 0.0f, -convertView.getHeight());
//            final View finalConvertView = convertView;
//            Animation.AnimationListener collapseListener = new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    finalConvertView.setVisibility(View.GONE);
//                }
//            };
//
//            animation.setAnimationListener(collapseListener);
//        }
//
//
//        animation.setDuration(300);
//        animation.setInterpolator(new AccelerateInterpolator(0.5f));
//        convertView.setAnimation(animation);

//        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_down);
//        animation.setDuration(1500);
//        convertView.setAnimation(animation);
        return convertView;
    }




    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    //endregion

    //region Helper Methods
    public void ExpandOrCollapse(final View v, Mode mode) {

    }

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

        try{
            carInfoDBHandler.UpdateCarInUse(carList.get(position).getName(), true);
            globalValues.Set(GlobalValues.CarInfo.CAR_NAME.toString(), carInfo.getName());
            globalValues.Set(GlobalValues.CarInfo.CURRENT_MILEAGE.toString(), String.valueOf(carInfo.getCurrentMileage()));
        }catch (Exception e){
            //TODO handle exceptions
            Exception ex = e;
        }
    }
    //endregion
}

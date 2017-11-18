package com.example.action_laptop.checkengine;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.example.action_laptop.checkengine.ComponentContainers.*;


/**
 * Created by Action-Laptop on 11/13/2017.
 */

class UpcomingRepairArrayAdapter extends ArrayAdapter<String> {

    //region Variables
    private CarValuesDBHandler carValuesDBHandler;
    private GlobalValues globalValues;
    private String CarName;
    private Map<String, Integer> upcomingRepairsMap;
    private int resource;
    private Context context;
    //endregion

    public UpcomingRepairArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects, @NonNull String CarName, Map<String, Integer> upcomingRepairsMap) {
        super(context, resource, objects);

        carValuesDBHandler = new CarValuesDBHandler(context, null);
        globalValues = new GlobalValues(context);
        this.resource = resource;
        this.context = context;
        this.CarName = CarName;
        this.upcomingRepairsMap = upcomingRepairsMap;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Component Container
        final UpcomingRepairItemHolder upcomingRepairItemHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(resource, parent, false);

            //set component properties
            upcomingRepairItemHolder = new UpcomingRepairItemHolder(convertView);
        } else {
            //keeps the position of the ListView items
            upcomingRepairItemHolder = (UpcomingRepairItemHolder) convertView.getTag();
        }

        upcomingRepairItemHolder.itemHeader.setText(upcomingRepairsMap.keySet().toArray()[position].toString());
        upcomingRepairItemHolder.itemValue.setText(upcomingRepairsMap.values().toArray()[position].toString());
        upcomingRepairItemHolder.itemContainer.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //initialize dialog box components
                        v = LayoutInflater.from(context).inflate(R.layout.car_item_input_dialog, null);
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    }
                }
        );

        convertView.setTag(upcomingRepairItemHolder);
        return convertView;
    }

    public void Refresh(){
        if(true)
            notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        upcomingRepairsMap = new LinkedHashMap<>();
        Map<Enum, Object> lastRepaired = (carValuesDBHandler.GetRowFrom(LastRepairedTable.TABLE_NAME, LastRepairedTable.TableColumns.RELATED_REPAIR_SCHEDULE_NAME_COLUMN.toString(), globalValues.Get(GlobalValues.CarInfo.CAR_NAME.toString()))).carItemsHashMap;
        Map<Enum, Object> repairSchedule = (carValuesDBHandler.GetRowFrom(RepairScheduleTable.TABLE_NAME, RepairScheduleTable.TableColumns.NAME_COLUMN.toString(), globalValues.Get(GlobalValues.CarInfo.CAR_NAME.toString()))).carItemsHashMap;
        int notificationThreshold = Integer.parseInt(globalValues.Get(NotificationsTable.TableColumns.MILEAGE_THRESHOLD_COLUMN.toString()));
        int currentMileage = Integer.parseInt(globalValues.Get(GlobalValues.CarInfo.CURRENT_MILEAGE.toString()));

        for (Map.Entry<Enum, Object> lastRepairedEntry : lastRepaired.entrySet()){
            int repairScheduleValue = (int)repairSchedule.get(lastRepairedEntry.getKey());
            int lastRepairedValue = (int)lastRepairedEntry.getValue();

            int nextRepairMileage = lastRepairedValue + repairScheduleValue;
            int remainingMileage = nextRepairMileage - currentMileage;

            if (remainingMileage <= notificationThreshold)
                upcomingRepairsMap.put(lastRepairedEntry.getKey().toString(), remainingMileage);
        }
        super.notifyDataSetChanged();
    }
}

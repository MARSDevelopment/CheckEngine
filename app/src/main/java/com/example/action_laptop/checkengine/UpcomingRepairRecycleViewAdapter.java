package com.example.action_laptop.checkengine;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Action-Laptop on 11/18/2017.
 */

public class UpcomingRepairRecycleViewAdapter extends RecyclerView.Adapter<UpcomingRepairRecycleViewAdapter.UpcomingRepairRecyclerViewHolder> {

    //region Variables
    private LinkedHashMap upcomingRepairsMap;
    private final LayoutInflater inflater;
    private final Context context;
    private CarValuesDBHandler carValuesDBHandler;
    // Allows to remember the last item shown on screen
    private int lastPosition = -1;
    //endregion

    public UpcomingRepairRecycleViewAdapter(Context context, Map<String, Integer> upcomingRepairsMap) {
        inflater = LayoutInflater.from(context);

        carValuesDBHandler = new CarValuesDBHandler(context, null);
        this.upcomingRepairsMap = (LinkedHashMap) upcomingRepairsMap;
        this.context = context;
    }


    @Override
    public UpcomingRepairRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.upcoming_repair_item, parent, false);
        UpcomingRepairRecyclerViewHolder upcomingRepairViewHolder = new UpcomingRepairRecyclerViewHolder(view);
        return upcomingRepairViewHolder;
    }

    @Override
    public void onBindViewHolder(UpcomingRepairRecyclerViewHolder holder, int position) {
        holder.upcomingRepairItemHolder.itemHeader.setText(upcomingRepairsMap.keySet().toArray()[position].toString());
        holder.upcomingRepairItemHolder.itemValue.setText(upcomingRepairsMap.values().toArray()[position].toString());
        //Animation
//        if (position > lastPosition)
//        {
//            Animation fadeInAnimation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
//            fadeInAnimation.setDuration(2000);
//            holder.itemView.startAnimation(fadeInAnimation);
//
//
//
//            lastPosition = position;
//        }
    }

    @Override
    public int getItemCount() {
        return upcomingRepairsMap.size();
    }

    //region Helper Methods
    public void RemoveItem(int position){
        upcomingRepairsMap.remove(upcomingRepairsMap.keySet().toArray()[position]);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, upcomingRepairsMap.size());
    }

    public void ClearItems(){
        upcomingRepairsMap.clear();
        notifyDataSetChanged();
    }
    //endregion

    //region View Holder Class used w/Adapter
    class UpcomingRepairRecyclerViewHolder extends RecyclerView.ViewHolder{

        ComponentContainers.UpcomingRepairItemHolder upcomingRepairItemHolder;

        public UpcomingRepairRecyclerViewHolder(View itemView) {
            super(itemView);

            upcomingRepairItemHolder = new ComponentContainers.UpcomingRepairItemHolder(itemView);
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
        }
    }
    //endregion
}

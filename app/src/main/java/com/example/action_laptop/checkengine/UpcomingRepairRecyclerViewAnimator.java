package com.example.action_laptop.checkengine;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;

/**
 * Created by Action-Laptop on 11/21/2017.
 */

public class UpcomingRepairRecyclerViewAnimator extends RecyclerView.ItemAnimator {

    private Context context;

    public UpcomingRepairRecyclerViewAnimator(Context context){
        this.context = context;
    }

    @Override
    public boolean animateDisappearance(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo preLayoutInfo, @Nullable RecyclerView.ItemAnimator.ItemHolderInfo postLayoutInfo) {
        Animation set = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
        set.setInterpolator(new BounceInterpolator());
        set.setDuration(2000);
        viewHolder.itemView.setAnimation(set);
        set.start();

        return true;
    }

    @Override
    public boolean animateAppearance(@NonNull RecyclerView.ViewHolder viewHolder, @Nullable RecyclerView.ItemAnimator.ItemHolderInfo preLayoutInfo, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo postLayoutInfo) {
        Animation set = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        set.setInterpolator(new BounceInterpolator());
        set.setDuration(2000);
        viewHolder.itemView.setAnimation(set);
        set.start();

        return true;
    }

    @Override
    public boolean animatePersistence(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
        return false;
    }

    @Override
    public boolean animateChange(@NonNull RecyclerView.ViewHolder oldHolder, @NonNull RecyclerView.ViewHolder newHolder, @NonNull ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
        return false;
    }

    @Override
    public void runPendingAnimations() {

    }

    @Override
    public void endAnimation(RecyclerView.ViewHolder viewHolder) {
        viewHolder.itemView.clearAnimation();
    }

    @Override
    public void endAnimations() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }
}

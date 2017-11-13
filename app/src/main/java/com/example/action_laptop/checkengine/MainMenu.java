package com.example.action_laptop.checkengine;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

/**
 * Created by Action-Laptop on 9/14/2017.
 */

public class MainMenu {

    public static void ActivitySwitchboard(Context context, MenuItem item, Intent intent){
        switch (item.getItemId()){
            case R.id.action_repair_schedule:
                intent = new Intent(context, RepairScheduleActivity.class);
                context.startActivity(intent);
                break;
            case R.id.action_last_repaired:
                intent = new Intent(context, LastRepairedActivity.class);
                context.startActivity(intent);
                break;
            case R.id.action_notifications:
                intent = new Intent(context, NotificationsActivity.class);
                context.startActivity(intent);
                break;
            case R.id.action_garage:
                intent = new Intent(context, GarageActivity.class);
                context.startActivity(intent);
                break;
            default:
                //defaults to home page
                intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                break;
        }
    }
}

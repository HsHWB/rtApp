package com.example.sliding.mainview.Utils;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/25.
 */
public class ActivityClass {
    public static List<Activity> activityList = new LinkedList<Activity>();

    // 遍历所有Activity finish
    public static void delete()
    {
        for (Activity activity : activityList)
        {
            activity.finish();
        }
        if (activityList.size() == 0)
            activityList.clear();
    }
}

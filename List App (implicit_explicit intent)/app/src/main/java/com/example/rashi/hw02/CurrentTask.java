package com.example.rashi.hw02;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Rashi on 1/31/2018.
 */

public class CurrentTask  implements Serializable,Comparator<CurrentTask> {
    public static int taskCount=0;
    private String title;
    private String date;
    private String time;
    private String priority;
    public CurrentTask(){}
    public CurrentTask(String title, String date, String time, String priority) {
        taskCount++;
        this.title = title;
        this.date = date;
        this.time = time;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getPriority() {
        return priority;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public int compare(CurrentTask currentTaskt1, CurrentTask currentTaskt2) {
        int value1 = currentTaskt1.getDate().compareTo(currentTaskt2.getDate());
        if (value1 != 0) {
            return value1;
        }
        else
        {
            return currentTaskt1.getTime().compareTo(currentTaskt2.getTime());
        }
            /*int value2 = currentTaskt1.getTime().compareTo(currentTaskt2.getTime());
            if (value2 == 0) {
                return o1.building.compareTo(o2.building);
            } else {
                return value2;
            }
        }*/
        //return value1;

    }
}

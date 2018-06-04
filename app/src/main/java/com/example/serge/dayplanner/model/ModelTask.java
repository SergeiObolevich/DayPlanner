package com.example.serge.dayplanner.model;

import com.example.serge.dayplanner.R;

import java.util.Date;

public class ModelTask implements Item {
    private static final int PRIORITY_LOW = 0;
    private static final int PRIORITY_NORMAL = 1;
    private static final int PRIORITY_HIGH = 2;

    public static final String[] PRIORITY_LEVELS = {"Low Priority", "Normal Priority", "High Priority"};

    public static final int STATUS_OVERDUE = 0;
    public static final int STATUS_CURRENT = 1;
    public static final int STATUS_DONE = 2;

    private String title;
    private long date;
    private int priority;
    private int status;
    private long timeStamp;
    private int dateStatus;

    public ModelTask() {
        this.status = -1;
        this.timeStamp = new Date().getTime();
    }

    public ModelTask(String title, long date, int priority, int status, long timeStamp) {
        this.title = title;
        this.date = date;
        this.priority = priority;
        this.status = status;
        this.timeStamp = timeStamp;
    }

    public int getPriorityColor() {
        switch (getPriority()) {
            case PRIORITY_HIGH: {
                if(getStatus() == STATUS_CURRENT || getStatus() == STATUS_OVERDUE) {
                    return R.color.priority_high;
                }
                else {
                    return R.color.priority_high_selected;
                }
            }
            case PRIORITY_NORMAL: {
                if(getStatus() == STATUS_CURRENT || getStatus() == STATUS_OVERDUE) {
                    return R.color.priority_normal;
                }
                else {
                    return R.color.priority_normal_selected;
                }
            }
            case PRIORITY_LOW: {
                if(getStatus() == STATUS_CURRENT || getStatus() == STATUS_OVERDUE) {
                    return R.color.priority_low;
                }
                else {
                    return R.color.priority_low_selected;
                }
            }
            default: {
                return 0;
            }
        }
    }
    @Override
    public boolean isTask() {
        return true;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public int getDateStatus() {
        return dateStatus;
    }

    public void setDateStatus(int dateStatus) {
        this.dateStatus = dateStatus;
    }
}
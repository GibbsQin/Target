package com.gibbs.tarket;

public class TargetInfo {
    private long rowId;
    public int icon;
    public String name;
    public String content;
    public int completed;
    public int progress;
    public int max;
    public int bgColor;

    public TargetInfo() {}

    public TargetInfo(int icon,String name,String content,int completed,
                      int progress,int max,int bgColor) {
        this.icon = icon;
        this.name = name;
        this.content = content;
        this.completed = completed;
        this.progress = progress;
        this.max = max;
        this.bgColor = bgColor;
    }

    public TargetInfo(long rowId,int icon,String name,String content,int completed,
                      int progress,int max,int bgColor) {
        this.rowId = rowId;
        this.icon = icon;
        this.name = name;
        this.content = content;
        this.completed = completed;
        this.progress = progress;
        this.max = max;
        this.bgColor = bgColor;
    }

    public void setRowId(long id) {
        rowId = id;
    }

    public long getRowId() {
        return rowId;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public int getCompleted() {
        return completed;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getProgress() {
        return progress;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public void setBgColor(int color) {
        this.bgColor = color;
    }

    public int getBgColor() {
        return bgColor;
    }

    public String toString() {
        String result = "";
        result += icon + " ";
        result += name + " ";
        result += content + " ";
        result += completed + " ";
        result += progress + " ";
        result += max + " ";
        result += bgColor + " ";

        return result;
    }
}

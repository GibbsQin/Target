package com.gibbs.target;

import androidx.annotation.NonNull;

public class TargetInfo {
    private long rowId;
    private String name;
    private String content;
    private int completed;
    private int progress;
    private int max;

    public TargetInfo() {
    }

    public TargetInfo(String name, String content, int completed, int progress, int max) {
        this.name = name;
        this.content = content;
        this.completed = completed;
        this.progress = progress;
        this.max = max;
    }

    public TargetInfo(long rowId, String name, String content, int completed, int progress, int max) {
        this(name, content, completed, progress, max);
        this.rowId = rowId;
    }

    public void setRowId(long id) {
        rowId = id;
    }

    public long getRowId() {
        return rowId;
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

    @NonNull
    @Override
    public String toString() {
        return "TargetInfo{" +
                "rowId=" + rowId +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", completed=" + completed +
                ", progress=" + progress +
                ", max=" + max +
                '}';
    }
}

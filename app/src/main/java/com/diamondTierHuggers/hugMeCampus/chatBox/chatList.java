package com.diamondTierHuggers.hugMeCampus.chatBox;

public class chatList {
    private String uid, name, message, date, time;

    public chatList(String uid, String name, String message, String date, String time) {
        this.uid = uid;
        this.name = name;
        this.message = message;
        this.date = date;
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}

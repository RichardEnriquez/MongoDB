package com.lucaxiang.models;

import javafx.scene.text.HitInfo;
import org.bson.Document;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class History {

    String username;
    String dateTime;
    String type;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public Document toDocument()
    {
        Date date = null;
        try
        {
            date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(this.getDateTime());
        }
        catch (ParseException ignored) {
        }
        return new Document("username",getUsername())
                    .append("dateTime",date)
                    .append("type", getType());
    }
    public static History fromDocument(Document document)
    {
        History history = new History();
        history.setUsername(document.getString("username"));
        Date   date = document.getDate("dateTime");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        history.setDateTime(dateFormat.format(date));
        history.setType(document.getString("type"));
        return history;
    }
}

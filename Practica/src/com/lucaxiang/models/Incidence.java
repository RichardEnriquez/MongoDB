package com.lucaxiang.models;

import com.lucaxiang.Utils;
import org.bson.Document;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Incidence {

    private String _id;
    private String createUser;
    private String origin;
    private String destin;
    private String description;
    private String type;
    private String createDate;

    public Incidence()
    {

    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestin() {
        return destin;
    }

    public void setDestin(String destin) {
        this.destin = destin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Document toDocument() {
        Date date = null;
        try
        {
           date = new SimpleDateFormat("dd/MM/yyyy").parse(this.getCreateDate());
        }
        catch (ParseException ignored) {

        }
        return new Document("origin",       this.getOrigin())
                    .append("destin",       this.getDestin())
                    .append("description",  this.getDescription())
                    .append("createDate",   date)
                    .append("createUser",   this.getCreateUser())
                    .append("type",         this.getType());
    }
    public static Incidence fromDocument(Document document) {

        Incidence incidence = new Incidence();
        incidence.setOrigin(document.getString("origin"));
        incidence.setDestin(document.getString("destin"));
        incidence.setDescription(document.getString("description"));
        Date   date = document.getDate("createDate");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        incidence.setCreateDate(dateFormat.format(date));
        incidence.setCreateUser(document.getString("createUser"));
        incidence.setType(document.getString("type"));
        incidence.set_id(document.getObjectId("_id").toString());
        return  incidence;
    }
}

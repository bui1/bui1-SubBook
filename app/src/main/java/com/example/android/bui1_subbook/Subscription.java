package com.example.android.bui1_subbook;

import java.util.Date;

/**
 * Created by MonicaB on 2018-01-20.
 */

public class Subscription {
    private String name;        // Name of the subscription
    private String date;        // Subscription start date
    private double charge;      // Monthly charge in $CAD for subscription
    private String comment;     // (Optional) comment about the subscription


    // Constructor in case of no comment
    public Subscription(String date, String name, double charge) {
        this(date, name, charge, "");
    }

    // Constructor in case a comment is provided
    public Subscription(String name, String date, double charge, String comment) {
        this.name = name;
        this.date = date;
        this.charge = charge;
        this.comment = comment;
    }

    // Get subscription start date
    public String getDate()  {
        return this.date;
    }

    // Set subscription date
    public void setDate(String date) {
        this.date = date;
    }

    // Get subscription name
    public String getName() {
        return this.name;
    }

    // Set subscription name
    public void setName(String name) {
        this.name = name;
    }

    // Get charge
    public double getCharge() {
        return this.charge;
    }

    // Set charge
    public void setCharge(Double charge) {
        this.charge = charge;
    }

    // Get comment
    public String getComment() {
        return this.comment;
    }

    // Set comment
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return this.name + ". " + this.date + " $" + this.charge + " " + this.comment;
    }

}

package com.example.android.bui1_subbook;

import java.io.Serializable;

/**
 * Created by MonicaB on 2018-01-20.
 */
// Implemented serializable interface
// https://stackoverflow.com/questions/2736389/how-to-pass-an-object-from-one-activity-to-another-on-android
// 2018-01-26

public class Subscription implements Serializable{
    // Class: Template for the subscription object
    // Design: It implements serializable so we can pass this object between activities
    // The design itself creates a Subscription based on if the user inputs a comment or not.
    // Usual getter and setter methods mostly used for debugging.
    // Issues: Formatting Subscription string so it is more intuitive for the user to see which parts are name,date,charge, and comment.

    private String name;        // Name of the subscription
    private String date;        // Subscription start date
    private double charge;      // Monthly charge in $CAD for subscription
    private String comment;     // (Optional) comment about the subscription


    // Constructor where user does not provide comment
    public Subscription(String name, String date, double charge) {
        this(name, date, charge, "");
    }

    // Constructor if all parameters are used
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

    // String method to print the object out
    @Override
    public String toString() {
        if ((this.comment).equals("")) {
            return this.name + "," +
                    this.date + "," +
                    this.charge;
        } else {
            return this.name + "," +
                    this.date + "," +
                    this.charge+ "," +
                    this.comment;
        }
    }



}

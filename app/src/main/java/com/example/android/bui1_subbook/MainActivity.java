package com.example.android.bui1_subbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/** Class: Show subscription contents on the screen for the user.
 * Design: Simple design that contains a list view for the objects and an add button if the user wishes to create another subscription
 * I also decided that it was intuitive for the user to see a subscription details by immediately clicking on the item itself
 *
 * Toast Widget code citation for all classes:
 * Template From Lab 3 Code on GSON
 * 2018-01-26
 */
public class MainActivity extends AppCompatActivity {
    private final int ADD_CODE = 1;                        // code for moving to addSub activity
    private final int DETAIL_CODE = 2;                      // code for moving to editSub activity
    private final String FILENAME = "subs.sav";             // file for saving subscriptions to

    private ArrayList<Subscription> items;                  // array that contains a list of subscriptions
    private ListView mainView;                              // main screen view
    private ArrayAdapter<Subscription> adapter;             // list view adapter
    private TextView price;                                 // the view that shows total price

    private int index;                                      // to find position of which subscription object the user clicked on

    // Function that is called when the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainView = (ListView) findViewById(R.id.subList); // define our list view

        price = (TextView)findViewById(R.id.totalprice); // define our text view

        mainView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // if we click on a subscription item, go to the editSub activity so we can view the sub's details
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;                                           // save the position where the user clicked
                String item = ((TextView)view).getText().toString();
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("item", item);                        // take subscription we clicked on and store it for the next activity
                startActivityForResult(intent,DETAIL_CODE);
            }
        });


        Button addBtn = (Button)findViewById(R.id.goToAdd); // define add button
        addBtn.setOnClickListener(new View.OnClickListener(){  // if clicked, go to add a new subscription activity
            public void onClick (View v) {
                Intent intent = new Intent(MainActivity.this, AddSub.class);
                startActivityForResult(intent,ADD_CODE);
            }
        });

    }


    // Function that calculates the total price of all the subscription items
    private void setTotal(){
        double totalPrice = 0;  // total price of all subscriptions
        for (Subscription sub:items){
            totalPrice = totalPrice + sub.getCharge();
        }
        // String variable that hold the double price in string format
        String strPrice = "Total Price of Subscriptions: $" + String.valueOf(totalPrice);
        price.setText(strPrice); // set textview content to total price
    }

    // Function that is called when the activity starts up
    // Template From Lab 3 Code on GSON
    // 2018-01-26
    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile(); // grab any previously saved items
        adapter = new ArrayAdapter<Subscription>(this, android.R.layout.simple_list_item_1, items);
        mainView.setAdapter(adapter);
        setTotal(); // set total price
    }

    // Function that grabs results from a previously called activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == ADD_CODE && resultCode == RESULT_OK) {
            /* if we are coming from adding subscription activity, add new sub to the listview, update file, and price.
             *
             * Passing Subscription object to another activity
             * 2018-01-26
             * https://stackoverflow.com/questions/2736389/how-to-pass-an-object-from-one-activity-to-another-on-android
             */

            Subscription newSub = (Subscription)intent.getSerializableExtra("newSub");
            items.add(newSub);
            saveInFile();
            setTotal();
            adapter.notifyDataSetChanged();
        }
        else if (requestCode == DETAIL_CODE && resultCode == RESULT_OK){// if we are viewing details of subscription
            String code = intent.getStringExtra("code");
            if (code.equals("0")){       // Delete Subscription, update screen, file, and total price
                items.remove(index);
                saveInFile();
                setTotal();
                adapter.notifyDataSetChanged();
            }
            else if (code.equals("1")){  // Update Contents of Subscription, update screen file, and total price
                Subscription newSub = (Subscription)intent.getSerializableExtra("editedSub");
                items.set(index,newSub);
                saveInFile();
                setTotal();
                adapter.notifyDataSetChanged();

            }
        }
    }

    // Grab contents of file is the app is forced closed
    // From Lab 3 Code on GSON
    // 2018-01-26
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Subscription>>() {}.getType();
            items = new ArrayList<Subscription>();
            items = gson.fromJson(in,listType); // grab contents from file
        } catch (FileNotFoundException e) {
            items = new ArrayList<Subscription>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // Save contents of new data into the file
    // From Lab 3 Code on GSON
    // 2018-01-26
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(items,out); // write contents to file
            out.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}

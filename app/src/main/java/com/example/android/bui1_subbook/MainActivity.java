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


public class MainActivity extends AppCompatActivity {
    // Class: Show subscription contents on the screen for the user.
    // Design: Simple design that contains a list view for the objects and an add button if the user wishes to create another subscription
    // I also decided that it was intuitive for the user to see a subscription details by immediately clicking on the item itself

    private ArrayList<Subscription> items; // array that contains a list of subscriptions
    private ListView mainView; // main screen view
    private ArrayAdapter<Subscription> adapter;

    private final int INIT_CODE = 1; // code for moving to addSub activity
    private final int INIT_CODE_2 = 2; // code for moving to editSub activity

    private final String FILENAME = "subs.sav"; // file for saving subscriptions to

    private int index; // to find the position of which subscription object the user clicked on

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Function that is called when the activity is created

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainView = (ListView) findViewById(R.id.sub_list); // define our list view

        mainView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // if we click on a subscription item, go to the editSub activity so we can view the sub's details
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index = position; // save the position where the user clicked
                String item = ((TextView)view).getText().toString();
                Intent intent = new Intent(MainActivity.this, editActivity.class);
                intent.putExtra("item", item); // take subscription we clicked on and store it for the next activity
                startActivityForResult(intent,INIT_CODE_2);
            }
        });


        Button addBtn = (Button)findViewById(R.id.goToAdd); // define add button
        addBtn.setOnClickListener(new View.OnClickListener(){ // if clicked, go to add a new subscription activity
            public void onClick (View v) {
                Intent intent = new Intent(MainActivity.this, addSub.class);
                startActivityForResult(intent,INIT_CODE);
            }
        });
    }

    @Override
    protected void onStart() {
        // Function that is called when the activity starts up
        // Template From Lab 3 Code on GSON
        // 2018-01-26
        super.onStart();
        loadFromFile(); // grab any previously saved items
        adapter = new ArrayAdapter<Subscription>(this, android.R.layout.simple_list_item_1, items);
        mainView.setAdapter(adapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        // grabbing results from a previously called activity

            super.onActivityResult(requestCode, resultCode, intent);

            if (requestCode == INIT_CODE && resultCode == RESULT_OK) { // if we are coming from adding a subscription activity
                // Passing Subscription object to another activity
                // 2018-01-26
                //https://stackoverflow.com/questions/2736389/how-to-pass-an-object-from-one-activity-to-another-on-android
                Subscription newSub = (Subscription)intent.getSerializableExtra("newSub");
                items.add(newSub); // add new object to the array list and update the screen, and file
                saveInFile();
                adapter.notifyDataSetChanged();

            }
            else if (requestCode == INIT_CODE_2 && resultCode == RESULT_OK){ // if we are viewing details of subscription
                String code = intent.getStringExtra("code");
                if (code.equals("0")){ // delete the subscription
                    adapter.remove(adapter.getItem(index)); // get item that was clicked on and remove it
                    saveInFile();// save contents to file
                    adapter.notifyDataSetChanged(); // update the screen
                }
                else if (code.equals("1")){ //
                    Subscription newSub = (Subscription)intent.getSerializableExtra("editedSub");
                    items.set(index,newSub); // update the contents of the subscription at the correct position
                    saveInFile(); // save contents to file
                    adapter.notifyDataSetChanged();// update the screen
                }
            }
    }


    private void loadFromFile() {
        // Grab contents of file is the app is forced closed
        // From Lab 3 Code on GSON
        // 2018-01-26
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            // Taken from stack overflow gson convert to json to type array list
            // 2018-01-23
            Type listType = new TypeToken<ArrayList<Subscription>>() {}.getType();

            items = new ArrayList<Subscription>();
            items = gson.fromJson(in,listType); // grab contents from file

        } catch (FileNotFoundException e) {
            items = new ArrayList<Subscription>();
        } catch (IOException e) {

            throw new RuntimeException(e);
        }

    }

    private void saveInFile() {
        // Save contents of new data into the file
        // From Lab 3 Code on GSON
        // 2018-01-26
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
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

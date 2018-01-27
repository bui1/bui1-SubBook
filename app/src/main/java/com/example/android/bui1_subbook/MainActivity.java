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

    ArrayList<Subscription> items;
    ListView mainView;
    ArrayAdapter<Subscription> adapter;

    final int INIT_CODE = 1;
    final int INIT_CODE_2 = 2;
    public static int EDIT_CODE = 3;

    final String FILENAME = "subs.sav";

    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainView = (ListView) findViewById(R.id.sub_list);

        //Date today = new Date();
        //SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");
        //items.add(new Subscription("Milk",dateFormat.format(today), 21.50));
        //items.add(new Subscription("Milk",dateFormat.format(today), 21.50));

        mainView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;
                String item = ((TextView)view).getText().toString();
//                Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, detailsActivity.class);
                intent.putExtra("item", item);
                startActivityForResult(intent,INIT_CODE_2);
            }
        });


        Button addBtn = (Button)findViewById(R.id.goToAdd);
        addBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v) {
                Intent intent = new Intent(MainActivity.this, addSub.class);
                startActivityForResult(intent,INIT_CODE);
            }
        });
    }

    @Override
    protected void onStart() {
        // Template From Lab 3 Code on GSON
        // 2018-01-26
        super.onStart();
        loadFromFile();
        adapter = new ArrayAdapter<Subscription>(this, android.R.layout.simple_list_item_1, items);
        mainView.setAdapter(adapter);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
            super.onActivityResult(requestCode, resultCode, intent);

            if (requestCode == INIT_CODE && resultCode == RESULT_OK) {
                // Passing Subscription object to another activity
                //https://stackoverflow.com/questions/2736389/how-to-pass-an-object-from-one-activity-to-another-on-android
                Subscription newSub = (Subscription)intent.getSerializableExtra("newSub");

                items.add(newSub);
                saveInFile();
                adapter.notifyDataSetChanged();

            }
            else if (requestCode == INIT_CODE_2 && resultCode == RESULT_OK){
                adapter.remove(adapter.getItem(index));
                saveInFile();
                adapter.notifyDataSetChanged();
            }

//            else if (requestCode == EDIT_CODE && resultCode == RESULT_OK){
//                continue;
//            }



    }


    //TODO: EDIT ENTRIES



    private void loadFromFile() {
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
            items = gson.fromJson(in,listType);

        } catch (FileNotFoundException e) {

            items = new ArrayList<Subscription>();
        } catch (IOException e) {

            throw new RuntimeException(e);
        }

    }

    private void saveInFile() {
        // From Lab 3 Code on GSON
        // 2018-01-26
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(items,out);
            out.flush();

        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }



}

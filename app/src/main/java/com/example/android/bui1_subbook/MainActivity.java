package com.example.android.bui1_subbook;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    ArrayList<Subscription> items;
    ListView mainView;
    ArrayAdapter<Subscription> adapter;

    // Codes for startActivityResult()
    final int INIT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = new ArrayList<Subscription>();
        mainView = (ListView) findViewById(R.id.sub_list);
        adapter = new ArrayAdapter<Subscription>(this, android.R.layout.simple_list_item_1, items);

        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");


        items.add(new Subscription(dateFormat.format(today),"Milk", 21.50));
        items.add(new Subscription(dateFormat.format(today),"Milk", 21.50));


        mainView.setAdapter(adapter);

        mainView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = ((TextView)view).getText().toString();
                Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();

            }
        });


        Button addBtn = (Button)findViewById(R.id.goToAdd);
        addBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v) {
                //put your intent code here
                Intent intent = new Intent(MainActivity.this, addSub.class);
                startActivityForResult(intent,INIT_CODE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
            super.onActivityResult(requestCode, resultCode, intent);

            if (requestCode == INIT_CODE && resultCode == RESULT_OK) {
                Subscription newSub = (Subscription)intent.getSerializableExtra("newSub");
                items.add(newSub);
                adapter.notifyDataSetChanged();

            }

    }



}

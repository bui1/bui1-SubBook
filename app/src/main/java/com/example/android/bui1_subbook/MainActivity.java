package com.example.android.bui1_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView mainView = (ListView) findViewById(R.id.sub_list);

        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");

        Subscription[] items = {
                new Subscription(dateFormat.format(today),"Milk", 21.50),
                new Subscription(dateFormat.format(today),"Butter", 15.99),
                new Subscription(dateFormat.format(today),"Yogurt", 14.90),
                new Subscription(dateFormat.format(today),"Toothpaste", 7.99),
                new Subscription(dateFormat.format(today),"Ice Cream", 10.00,"asdjisdj"),
        };

        ArrayAdapter<Subscription> adapter = new ArrayAdapter<Subscription>(this,
                android.R.layout.simple_list_item_1, items);

        mainView.setAdapter(adapter);

        mainView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = ((TextView)view).getText().toString();
                Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();

            }
        });

    }

    public void toAdd (View v) {
        //put your intent code here
        Button addBtn = (Button)findViewById(R.id.add_button);
        Intent intent = new Intent(MainActivity.this, addSub.class);
        startActivity(intent);
    }
}

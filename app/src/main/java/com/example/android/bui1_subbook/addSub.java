package com.example.android.bui1_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class addSub extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub);



        final EditText name = (EditText) findViewById(R.id.Name);
        final EditText charge = (EditText) findViewById(R.id.Charge);
        final EditText date = (EditText) findViewById(R.id.Date);
        final EditText comment = (EditText) findViewById(R.id.Comment);
        Button addBtn = (Button)findViewById(R.id.add_button);
        final Intent intent = this.getIntent();

        addBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v) {
                String nameValue = name.getText().toString().trim();
                if (nameValue.length() > 20 || nameValue.length() < 1){
                    Toast.makeText(getApplicationContext(),"Name must be between 1 and 20 characters ",Toast.LENGTH_SHORT)
                            .show();
                    return;
                }


                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dateValue = date.getText().toString().trim();
                dateFormat.setLenient(false);
                try{
                    dateFormat.parse(dateValue);

                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Invalid date input. Should be yyyy-MM-dd ",Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                Double chargeValue = Double.parseDouble(charge.getText().toString().trim());

                try{
                    if (chargeValue < 0){
                        Toast.makeText(getApplicationContext(),"Charge value should be positive ",Toast.LENGTH_SHORT)
                                .show();
                        return;
                    }
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Charge value should be positive ",Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                String commentValue = comment.getText().toString().trim();
                Subscription newSub = new Subscription(nameValue,dateValue,chargeValue,commentValue);
                intent.putExtra("newSub", newSub);
                setResult(RESULT_OK,intent);
                finish();

            }
        });

    }


}
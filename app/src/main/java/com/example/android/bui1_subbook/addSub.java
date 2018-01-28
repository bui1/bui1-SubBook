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
    // Class: Purpose is to create a Subscription object based on what the user inputs
    // Design: Took care of input validation by using restricted text views such as the user
    // only being able to input numbers for charge and date. Then had conditionals to check for proper format.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub);

        // defining my views and button on the screen
        final EditText name = (EditText) findViewById(R.id.Name);
        final EditText charge = (EditText) findViewById(R.id.Charge);
        final EditText date = (EditText) findViewById(R.id.Date);
        final EditText comment = (EditText) findViewById(R.id.Comment);
        Button addBtn = (Button)findViewById(R.id.add_button);

        final Intent intent = this.getIntent();     // getting the intent that called this activity

        addBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v) {
                String nameValue = name.getText().toString().trim(); // grab name from edit text input
                if (nameValue.length() > 20 || nameValue.length() < 1){ // validating name input length
                    Toast.makeText(getApplicationContext(),"Name must be between 1 and 20 characters ",Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                // 2018-01-21
                // https://stackoverflow.com/questions/9277747/android-simpledateformat-how-to-use-it
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // date format we want
                String dateValue = date.getText().toString().trim(); // grab date from edit text input
                dateFormat.setLenient(false);

                try{ // validating user input date based on format we want
                    dateFormat.parse(dateValue);
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Invalid date input. Should be yyyy-MM-dd ",Toast.LENGTH_SHORT)
                            .show();
                    return;
                }


                String inputCharge = charge.getText().toString().trim(); // grab charge from edit text input
                if (inputCharge.length() == 0){ // check if user did input a charge value
                    Toast.makeText(getApplicationContext(),"Charge value cannot be blank ",Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                Double chargeValue = Double.parseDouble(inputCharge); // convert string input to double output
                if (chargeValue < 0){ // input validate charge value
                    Toast.makeText(getApplicationContext(),"Charge value should be positive ",Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                String commentValue = comment.getText().toString().trim(); // grab comment from edit text input
                Subscription newSub = new Subscription(nameValue,dateValue,chargeValue,commentValue);
                intent.putExtra("newSub", newSub); // store new object for the next activity
                setResult(RESULT_OK,intent);
                finish();

            }
        });

    }


}

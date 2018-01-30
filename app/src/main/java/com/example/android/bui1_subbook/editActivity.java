package com.example.android.bui1_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class editActivity extends AppCompatActivity {
    // Purpose: Let the user see the details of the subscription object in a cleaner slate. They have an option to delete or edit the object.
    // Design: It was better to have directly have the option to edit so the user doesn't have to switch between even more activities for this,
    // then can click the button to confirm their option.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        final Intent intent = this.getIntent(); // get the intent that called this activity
        String sub = intent.getStringExtra("item"); // grab the subscription object the user clicked on previously


        String[] parts = sub.split(","); // split the string into the subscription's attributes


        // define our views and buttons on screen
        final EditText name = (EditText) findViewById(R.id.NameEdit);
        final EditText charge = (EditText) findViewById(R.id.ChargeEdit);
        final EditText date = (EditText) findViewById(R.id.DateEdit);
        final EditText comment = (EditText) findViewById(R.id.CommentEdit);
        Button changeBtn = (Button)findViewById(R.id.change);
        Button deleteBtn = (Button) findViewById(R.id.delete);

        // place appropriate subscription contents int their respective text views
        name.setText(parts[0]);
        date.setText(parts[1]);
        charge.setText(parts[2]);

        do { // set the comment field to what the user inputted previously otherwise leave it blank
            try {
                comment.setText(parts[3]);
                break;

            } catch (Exception e) {

            }
        }
        while (false); // only need to check once if the user inputted a comment or not

        deleteBtn.setOnClickListener(new View.OnClickListener() { // if user click delete button
            @Override
            public void onClick(View view) {
                intent.putExtra("deletion",intent.getStringExtra("item"));
                intent.putExtra("code","0"); // letting the main activity know we want to delete object
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        changeBtn.setOnClickListener(new View.OnClickListener(){ // if user click the change subscription button
            public void onClick (View v) {
                String nameValue = name.getText().toString().trim(); // grab name from edit text input
                if (nameValue.length() > 20 || nameValue.length() < 1){  // validate the name input
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
                if (chargeValue <= 0){ // input validate charge value
                    Toast.makeText(getApplicationContext(),"Charge value should be positive ",Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                String commentValue = comment.getText().toString().trim(); // grab comment from edit text input
                if (commentValue.length() == 0 || commentValue.length() <= 30){ // if comment is blank or is correctly inputted
                    Subscription newSub = new Subscription(nameValue,dateValue,chargeValue,commentValue);
                    intent.putExtra("code","1"); // let main activity know we edited the object
                    intent.putExtra("editedSub", newSub); // store contents of new object for next activity
                    setResult(RESULT_OK,intent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Comment is too long, needs to be less than 30 characters",Toast.LENGTH_SHORT)
                            .show();
                    return;
                }


            }
        });

   }


}

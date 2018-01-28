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
    // Design: It was better to have directly have the option to edit so the user doesn't have to switch between even more activities for this.

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

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("deletion",intent.getStringExtra("item"));
                intent.putExtra("code","0"); // letting the main activity know we want to delete object
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        changeBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v) {
                String nameValue = name.getText().toString().trim();
                if (nameValue.length() > 20 || nameValue.length() < 1){  // validate the name input
                    Toast.makeText(getApplicationContext(),"Name must be between 1 and 20 characters ",Toast.LENGTH_SHORT)
                            .show();
                    return;
                }


                // 2018-01-21
                // https://stackoverflow.com/questions/9277747/android-simpledateformat-how-to-use-it
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // date format we want
                String dateValue = date.getText().toString().trim();
                dateFormat.setLenient(false);

                try{ // validating user input date based on format we want
                    dateFormat.parse(dateValue);
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Invalid date input. Should be yyyy-MM-dd ",Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                Double chargeValue = Double.parseDouble(charge.getText().toString().trim()); // convert string input to double

                if (chargeValue < 0){ // validating charge input
                        Toast.makeText(getApplicationContext(),"Charge value should be positive ",Toast.LENGTH_SHORT)
                                .show();
                        return;
                    }

//                try{
//                    if (chargeValue < 0){
//                        Toast.makeText(getApplicationContext(),"Charge value should be positive ",Toast.LENGTH_SHORT)
//                                .show();
//                        return;
//                    }
//                }
//                catch(Exception e){
//                    Toast.makeText(getApplicationContext(),"Charge value should be positive ",Toast.LENGTH_SHORT)
//                            .show();
//                    return;
//                }

                String commentValue = comment.getText().toString().trim();

                Subscription newSub = new Subscription(nameValue,dateValue,chargeValue,commentValue);
                //Intent intent = new Intent(editActivity.this , MainActivity.class);

                intent.putExtra("code","1"); // let main activity know we edited the object
                intent.putExtra("editedSub", newSub); // grab contents of new object
                setResult(RESULT_OK,intent);
                //startActivityForResult(intent,MainActivity.EDIT_CODE);
                finish();

            }
        });

   }


}

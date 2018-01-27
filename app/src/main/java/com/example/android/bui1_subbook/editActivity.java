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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        final Intent intent = this.getIntent();
        String thing = intent.getStringExtra("item");


        String[] parts = thing.split(",");



        final EditText name = (EditText) findViewById(R.id.NameEdit);
        final EditText charge = (EditText) findViewById(R.id.ChargeEdit);
        final EditText date = (EditText) findViewById(R.id.DateEdit);
        final EditText comment = (EditText) findViewById(R.id.CommentEdit);
        Button changeBtn = (Button)findViewById(R.id.change);
        Button deleteBtn = (Button) findViewById(R.id.delete);

        name.setText(parts[0]);
        date.setText(parts[1]);
        charge.setText(parts[2]);

        do {
            try {
                comment.setText(parts[3]);
                break;

            } catch (Exception e) {

            }
        }
        while (false);


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("deletion",intent.getStringExtra("item"));
                intent.putExtra("code","0");
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        changeBtn.setOnClickListener(new View.OnClickListener(){
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
                //Intent intent = new Intent(editActivity.this , MainActivity.class);

                intent.putExtra("code","1");
                intent.putExtra("editedthing", newSub);
                setResult(RESULT_OK,intent);
                //startActivityForResult(intent,MainActivity.EDIT_CODE);
                finish();

            }
        });

   }


}

package com.example.android.bui1_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class detailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final Intent intent = this.getIntent();
        TextView text = (TextView)findViewById(R.id.subDetail);
        text.setText(intent.getStringExtra("item"));

        Button deletebtn = (Button)findViewById(R.id.delete);

        deletebtn.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v) {
                intent.putExtra("deletion",intent.getStringExtra("item"));
                setResult(RESULT_OK,intent);
                finish();
            }
        });


        Button editbtn = (Button)findViewById(R.id.edit);
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(detailsActivity.this, editActivity.class);
                x.putExtra("edit",intent.getStringExtra("item"));
                startActivityForResult(x,MainActivity.EDIT_CODE);
                finish();
            }
        });

    }
}

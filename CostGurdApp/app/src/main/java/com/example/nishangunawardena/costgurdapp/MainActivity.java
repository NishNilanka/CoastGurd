package com.example.nishangunawardena.costgurdapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button b1,b2;
    EditText userName,password;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            b1=(Button)findViewById(R.id.login);
            userName =(EditText)findViewById(R.id.username);
            password =(EditText)findViewById(R.id.Password);

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(userName.getText().toString().equals("admin") &&
                            password.getText().toString().equals("admin")) {
                        Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                        Intent intent;
                        intent = new Intent(v.getContext(), DashBoard.class);
                        startActivity(intent);
                        finish();

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();

                    }
                }
            });

    }


}

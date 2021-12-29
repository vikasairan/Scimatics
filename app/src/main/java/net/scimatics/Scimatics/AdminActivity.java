package net.scimatics.Scimatics;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class AdminActivity extends AppCompatActivity {
    public static Button Add,Update,Delete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Add = (Button)findViewById(R.id.Add);
        Update = (Button)findViewById(R.id.Update);
        Delete = (Button)findViewById(R.id.Delete);


        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, AddUser.class));
            }
        });
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, UpdateUser.class));
            }
        });
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, DeleteUser.class));
            }
        });
    }
    }
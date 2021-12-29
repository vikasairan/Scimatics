package net.scimatics.Scimatics;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.google.android.gms.internal.zzbfq.NULL;

public class LoginActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout d;
    private ActionBarDrawerToggle t;
    public static EditText password,Login_id;
    public static Button login_button;
    ProgressBar progressBar;
    public static String id = null;
    public static String pass = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        d = (DrawerLayout) findViewById(R.id.draw);
        t = new ActionBarDrawerToggle(this, d, R.string.open, R.string.close);
        d.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setNavigationViewListener();
        password = (EditText)findViewById(R.id.password);
        Login_id = (EditText)findViewById(R.id.LoginID);
        login_button = (Button)findViewById(R.id.sign_in_button);
        progressBar = (ProgressBar) findViewById(R.id.indeterminateBar);
        progressBar.setVisibility(View.INVISIBLE);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=Login_id.getText().toString().trim();
                pass=password.getText().toString().trim();
                if(TextUtils.isEmpty(id)||TextUtils.isEmpty(pass))
                {
                    Toast.makeText(LoginActivity.this,"Enter Valid User ID and Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference().child("Admin");
                final DatabaseReference dbRef = database.getReference().child("User");
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if ((dataSnapshot.child(id).exists()) && dataSnapshot.child(id).getValue().equals(pass)) {
                                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                startActivity(intent);
                                Login_id.setText(null);
                                password.setText(null);
                                finish();
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                else
                {
                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(id).exists()&&dataSnapshot.child(id).child("password").getValue().equals(pass))
                        {
                            Intent intent=new Intent(LoginActivity.this, UserActivity.class);
                            startActivity(intent);
                            Login_id.setText(null);
                            password.setText(null);
                            finish();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                        else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this,"User ID or Password Incorrect",Toast.LENGTH_SHORT).show();
                            password.setText(null);
                        }}

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });}}
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });
            }});}


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.home:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();
                return true;
            case R.id.aboutus:
                Intent p = new Intent(this, Aboutus.class);
                startActivity(p);
                finish();
                return true;
            case R.id.events:
                Intent h = new Intent(this, event.class);
                startActivity(h);
                finish();
                return true;
            case R.id.login:
                Intent k = new Intent(this, LoginActivity.class);
                startActivity(k);
                return true;
            case R.id.con:
                Intent l = new Intent(this, contact.class);
                startActivity(l);
                finish();
                return true;

            case R.id.details:
                Intent de = new Intent(this, MemberDetails.class);
                startActivity(de);
                finish();
                return true;
            case R.id.updates:
                Intent u = new Intent(this, Latest_Updates.class);
                startActivity(u);
                finish();
                return true;
            case R.id.signout:

                FirebaseAuth auth=FirebaseAuth.getInstance();
                auth.signOut();
                Intent s = new Intent(this, MainLoginActivity.class);
                startActivity(s);
                finish();
                return true;
        }
        //close navigation drawer
        d = (DrawerLayout) findViewById(R.id.draw);
        d.closeDrawer(GravityCompat.START);
        return true;
    }
    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener(this);
    }
}
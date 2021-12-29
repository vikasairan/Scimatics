package net.scimatics.Scimatics;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Edit extends AppCompatActivity {
    EditText name,contact,email,password,rollno,division,learners;
    Button update,back;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private TextView Learner;
    String pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        name=(EditText)findViewById(R.id.name);
        contact=(EditText)findViewById(R.id.contact);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        rollno=(EditText)findViewById(R.id.rollno);
        division=(EditText)findViewById(R.id.division);
        learners=(EditText)findViewById(R.id.learners);
        update=(Button)findViewById(R.id.update);
        Learner=(TextView)findViewById(R.id.Learner);
        back=(Button)findViewById(R.id.back);
        if(!UserActivity.pos.equalsIgnoreCase("Mentor")) {
            learners.setVisibility(View.INVISIBLE);
            Learner.setVisibility(View.INVISIBLE);
        }
        else
        {
            learners.setVisibility(View.VISIBLE);
            Learner.setVisibility(View.VISIBLE);
        }
        name.setText("");
        email.setText("");
        contact.setText("");
        password.setText("");
        rollno.setText("");
        division.setText("");
        learners.setText("");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference().child("User");
                    dbRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(LoginActivity.id).child("name").exists()) {
                                name.setText(dataSnapshot.child(LoginActivity.id).child("name").getValue().toString());
                            }
                            if(dataSnapshot.child(LoginActivity.id).child("email").exists()) {
                                email.setText(dataSnapshot.child(LoginActivity.id).child("email").getValue().toString());
                            }
                            if(dataSnapshot.child(LoginActivity.id).child("contact").exists()) {
                                contact.setText(dataSnapshot.child(LoginActivity.id).child("contact").getValue().toString());

                            }
                            if(dataSnapshot.child(LoginActivity.id).child("password").exists()) {
                                password.setText(dataSnapshot.child(LoginActivity.id).child("password").getValue().toString());

                            }
                            if(dataSnapshot.child(LoginActivity.id).child("roll no").exists()) {
                                rollno.setText(dataSnapshot.child(LoginActivity.id).child("roll no").getValue().toString());

                            }
                            if(dataSnapshot.child(LoginActivity.id).child("division").exists()) {  division.setText(dataSnapshot.child(LoginActivity.id).child("division").getValue().toString());

                            }
                            if(dataSnapshot.child(LoginActivity.id).child("learners").exists()) {
                                learners.setText(dataSnapshot.child(LoginActivity.id).child("learners").getValue().toString());
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {}}
                    );

                    update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (validateForm()) {
                                String na = name.getText().toString();
                                String con= contact.getText().toString();
                                String ema = email.getText().toString();
                                String pass = password.getText().toString();
                                String roll = rollno.getText().toString();
                                String div = division.getText().toString();
                                String lear = learners.getText().toString();
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("User");
                                DatabaseReference User_ID=myRef.child(LoginActivity.id);
                                User_ID.child("name").setValue(na);
                                User_ID.child("contact").setValue(con);
                                User_ID.child("email").setValue(ema);
                                User_ID.child("password").setValue(pass);
                                User_ID.child("roll no").setValue(roll);
                                User_ID.child("division").setValue(div);
                                User_ID.child("learners").setValue(lear);
                                Toast.makeText(Edit.this, "Data updated successfully",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Edit.this, UserActivity.class));
                                finish();
                            }
                        }
                    });
                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(Edit.this, UserActivity.class));
                            finish();
                        }
                    });
    }
            public boolean validateForm() {
                boolean alldone = true;
                String na = name.getText().toString().trim();

                if (TextUtils.isEmpty(na)) {
                    name.setError("Enter name");
                    return false;
                } else {
                    alldone = true;
                    name.setError(null);
                }
                return alldone;
            }
        }

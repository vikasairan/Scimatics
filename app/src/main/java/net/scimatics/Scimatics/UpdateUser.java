package net.scimatics.Scimatics;
import android.content.Intent;
import android.os.Bundle;
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

public class UpdateUser extends AppCompatActivity {
    EditText name,contact,email,uid,password,rollno,position,division,learners;
    Button update;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private TextView Learner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        name=(EditText)findViewById(R.id.name);
        contact=(EditText)findViewById(R.id.contact);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        rollno=(EditText)findViewById(R.id.rollno);
        uid=(EditText)findViewById(R.id.uid);
        position=(EditText)findViewById(R.id.position);
        division=(EditText)findViewById(R.id.division);
        learners=(EditText)findViewById(R.id.learners);
        update=(Button)findViewById(R.id.update);
        Learner=(TextView)findViewById(R.id.Learner);
        Button go = (Button) findViewById(R.id.go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newvalidateForm()) {
                    final String[] na = new String[1];
                    final String[] con = new String[1];
                    final String[] ema = new String[1];
                    final String[] pass = new String[1];
                    final String[] roll = new String[1];
                    final String[] pos = new String[1];
                    final String[] div = new String[1];
                    final String[] lear = new String[1];

                    final String ui = uid.getText().toString().trim();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference dbRef = database.getReference().child("User");
                    dbRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(ui).exists()) {
                                na[0] = (String) dataSnapshot.child(ui).child("name").getValue();
                                con[0] = (String) dataSnapshot.child(ui).child("contact").getValue();
                                ema[0] = (String) dataSnapshot.child(ui).child("email").getValue();
                                pass[0] = (String) dataSnapshot.child(ui).child("password").getValue();
                                roll[0] = (String) dataSnapshot.child(ui).child("roll no").getValue();
                                pos[0] = (String) dataSnapshot.child(ui).child("position").getValue();
                                div[0] = (String) dataSnapshot.child(ui).child("division").getValue();
                                lear[0] = (String) dataSnapshot.child(ui).child("learners").getValue();
                                name.setText(na[0]);
                                email.setText(ema[0]);
                                contact.setText(con[0]);
                                password.setText(pass[0]);
                                rollno.setText(roll[0]);
                                position.setText(pos[0]);
                                division.setText(div[0]);
                                learners.setText(lear[0]);
                            }
                            else
                            {
                                Toast.makeText(UpdateUser.this, "User ID doesn't exist",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(UpdateUser.this, "User ID doesn't exist",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                    update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (validateForm()) {
                                String ui = uid.getText().toString();
                                String na = name.getText().toString();
                                String con= contact.getText().toString();
                                String ema = email.getText().toString();
                                String pass = password.getText().toString();
                                String roll = rollno.getText().toString();
                                String pos = position.getText().toString();
                                String div = division.getText().toString();
                                String lear = learners.getText().toString();
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("User");
                                DatabaseReference User_ID=myRef.child(ui);
                                User_ID.child("name").setValue(na);
                                User_ID.child("contact").setValue(con);
                                User_ID.child("email").setValue(ema);
                                User_ID.child("password").setValue(pass);
                                User_ID.child("roll no").setValue(roll);
                                User_ID.child("position").setValue(pos);
                                User_ID.child("division").setValue(div);
                                User_ID.child("learners").setValue(lear);

                                Toast.makeText(UpdateUser.this, "Data updated successfully",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });
                }
            }

            // to check if user filled all the required fieds
            public boolean validateForm() {
                boolean alldone = true;
                String na = name.getText().toString().trim();
                String con = contact.getText().toString().trim();
                String ema = email.getText().toString().trim();
                String ui = uid.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String roll = rollno.getText().toString().trim();
                String pos = position.getText().toString().trim();
                String div = division.getText().toString().trim();
                if (TextUtils.isEmpty(na)) {
                    name.setError("Enter name");
                    return false;
                } else {
                    alldone = true;
                    name.setError(null);
                }
                if (TextUtils.isEmpty(ui)) {
                    uid.setError("Enter UID");
                    return false;
                } else {
                    alldone = true;
                    uid.setError(null);
                }
                if (TextUtils.isEmpty(pass)) {
                    password.setError("Enter Password");
                    return false;
                } else {
                    alldone = true;
                    password.setError(null);
                }

                return alldone;
            }
        });}
    public boolean newvalidateForm() {
        boolean alldone = true;
        String ui = uid.getText().toString().trim();
        if (TextUtils.isEmpty(ui)) {
            uid.setError("Enter UID");
            return false;
        } else {
            alldone = true;
            uid.setError(null);
        }
        return alldone;
    }
}
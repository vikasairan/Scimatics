package net.scimatics.Scimatics;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class AddUser extends AppCompatActivity {
    EditText name,contact,email,uid;
    Button save;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        name=(EditText)findViewById(R.id.name);
        contact=(EditText)findViewById(R.id.contact);
        email=(EditText)findViewById(R.id.email);
        uid=(EditText)findViewById(R.id.uid);
        save=(Button)findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateForm())
                {
                    String na=name.getText().toString().trim();
                    String con=contact.getText().toString().trim();
                    String ema=email.getText().toString().trim();
                    String ui=uid.getText().toString().trim();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("User");
                    DatabaseReference User_ID=myRef.child(ui);
                    User_ID.child("name").setValue(na);
                    User_ID.child("contact").setValue(con);
                    User_ID.child("email").setValue(ema);
                    User_ID.child("password").setValue("scimatics@123");
                    Toast.makeText(AddUser.this, "Data is saved successfully",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
    }
    // to check if user filled all the required fieds
    public boolean validateForm()
    {
        boolean alldone=true;
        String na=name.getText().toString().trim();
        String con=contact.getText().toString().trim();
        String ema=email.getText().toString().trim();
        String ui=uid.getText().toString().trim();
        if(TextUtils.isEmpty(na))
        {
            name.setError("Enter first name");
            return false;
        }else
        {
            alldone=true;
            name.setError(null);
        }
        if(TextUtils.isEmpty(ui))
        {
            uid.setError("Enter UID");
            return false;
        }else
        {
            alldone=true;
            uid.setError(null);
        }
        return alldone;
    }
}

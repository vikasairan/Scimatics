package net.scimatics.Scimatics;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeleteUser extends AppCompatActivity {
    EditText uid;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        uid=(EditText)findViewById(R.id.uid);
        Button delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newvalidateForm()) {
                    final String ui = uid.getText().toString().trim();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference dbRef = database.getReference().child("User");
                    dbRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(ui).exists()) {
                                dbRef.child(ui).setValue(null);
                                Toast.makeText(DeleteUser.this, "Data Deleted",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(DeleteUser.this, "User ID doesn't exist",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(DeleteUser.this, "User ID doesn't exist",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    finish();
                }
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
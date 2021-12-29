package net.scimatics.Scimatics;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
public class Member_Information extends AppCompatActivity {
    TextView Name, rollno, contact, email, position, division,learners;
    ImageView image;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private TextView ShowTextView1,ShowTextView2,ShowTextView3,ShowTextView4,ShowTextView5,ShowTextView6;
    public static String pos=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member__information);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        ShowTextView1 = (TextView)findViewById(R.id.view1);
        ShowTextView2 = (TextView)findViewById(R.id.view2);
        ShowTextView3 = (TextView)findViewById(R.id.view3);
        ShowTextView4 = (TextView)findViewById(R.id.view4);
        ShowTextView5 = (TextView)findViewById(R.id.view5);
        ShowTextView6 = (TextView)findViewById(R.id.view6);
        ShowTextView6.setVisibility(View.INVISIBLE);
        Name = (TextView) findViewById(R.id.name);
        rollno = (TextView) findViewById(R.id.rollno);
        contact = (TextView) findViewById(R.id.contact);
        email = (TextView) findViewById(R.id.email);
        position = (TextView) findViewById(R.id.position);
        division = (TextView) findViewById(R.id.division);
        image = (ImageView) findViewById(R.id.image);
        learners=(TextView)findViewById(R.id.learners);
        Bundle extras = getIntent().getExtras();
        String user_id = null;
        if (extras != null) {
            user_id = extras.getString("id");
        }
        final SwipeRefreshLayout swipeView = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeView.setColorScheme(android.R.color.holo_blue_dark,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_green_light);
        final String finalUser_id = user_id;
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeView.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeView.setRefreshing(false);
                        mStorageRef = FirebaseStorage.getInstance().getReference();
                        final long ONE_MEGABYTE = 1024 * 1024;

                        mStorageRef.child("User/" + finalUser_id + ".jpg").getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                image.setImageBitmap(bitmap);
                            }
                        });
                    }
                }, 3000);
            }
        });
        mStorageRef = FirebaseStorage.getInstance().getReference();
        final long ONE_MEGABYTE = 1024 * 1024;
        mStorageRef.child("User/" + user_id + ".jpg").getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                image.setImageBitmap(bitmap);
            }
        });
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference dbRef = database.getReference().child("User");
        final String finalUser_id1 = user_id;
        dbRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            Name.setText((CharSequence) dataSnapshot.child(finalUser_id1).child("name").getValue());
                                            rollno.setText((CharSequence) dataSnapshot.child(finalUser_id1).child("roll no").getValue());
                                            email.setText((CharSequence) dataSnapshot.child(finalUser_id1).child("email").getValue());
                                            contact.setText((CharSequence) dataSnapshot.child(finalUser_id1).child("contact").getValue());
                                            position.setText((CharSequence) dataSnapshot.child(finalUser_id1).child("position").getValue());
                                            division.setText((CharSequence) dataSnapshot.child(finalUser_id1).child("division").getValue());
                                            if (position.getText().toString().equalsIgnoreCase("Mentor")) {
                                                learners.setText((CharSequence) dataSnapshot.child(finalUser_id1).child("learners").getValue());
                                                if (!learners.getText().toString().isEmpty()) {
                                                    ShowTextView6.setVisibility(View.VISIBLE);
                                                }
                                            }
                                            pos = position.getText().toString().trim();
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }

                                    }
        );
    }

}

package net.scimatics.Scimatics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
public class list extends AppCompatActivity {
    DatabaseReference dref;
    ListView listview;
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listview = (ListView) findViewById(R.id.listview);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list, list);
        listview.setAdapter(adapter);
        dref = FirebaseDatabase.getInstance().getReference().child("User");

        dref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Bundle extras = getIntent().getExtras();
                String department = null;
                if (extras != null) {
                    department = extras.getString("department");
                }
                if (dataSnapshot.child("position").exists() && dataSnapshot.child("position").getValue().toString().toLowerCase().contains("Mentor".toLowerCase())&&dataSnapshot.child("division").exists() && dataSnapshot.child("division").getValue().toString().toLowerCase().contains(department.toLowerCase()))
                    list.add(dataSnapshot.child("name").getValue().toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                list.remove(dataSnapshot.getValue(String.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String name = (String) listview.getItemAtPosition(i);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference dbRef = database.getReference().child("User");
                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {

                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            if (dsp.child("name").getValue().equals(name)) {
                                String user_id = dsp.getKey();
                                Bundle bundle = new Bundle();
                                bundle.putString("id", user_id);
                                Intent intent = new Intent(list.this, Member_Information.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
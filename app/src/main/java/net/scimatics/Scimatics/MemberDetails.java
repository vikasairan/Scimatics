package net.scimatics.Scimatics;

import java.util.ArrayList;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MemberDetails extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout d;
    private ActionBarDrawerToggle t;
    private ExpandListAdapter ExpAdapter;
    private ArrayList<Group> ExpListItems;
    private ExpandableListView ExpandList;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);
        d = (DrawerLayout) findViewById(R.id.draw);
        t = new ActionBarDrawerToggle(this, d, R.string.open, R.string.close);
        d.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setNavigationViewListener();
        ExpandList = (ExpandableListView) findViewById(R.id.list);
        progressBar = (ProgressBar) findViewById(R.id.indeterminateBar);
        progressBar.setVisibility(View.VISIBLE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference dbRef = database.getReference().child("User");
        dbRef.addValueEventListener(new ValueEventListener() {
            ArrayList<Group> group_list = new ArrayList<Group>();
            ArrayList<Child> child_list1,child_list2,child_list3,child_list4;
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                group_list.clear();
                child_list1 = new ArrayList<Child>();
                child_list2 = new ArrayList<Child>();
                child_list3 = new ArrayList<Child>();
                child_list4 = new ArrayList<Child>();
                Group gru1 = new Group();
                gru1.setName("Core Members");
                final Group gru2 = new Group();
                gru2.setName("Mentors");
                Group gru3 = new Group();
                gru3.setName("Learners");
                Group gru4 = new Group();
                gru4.setName("Others");
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Child ch = new Child();
                    if(dsp.child("position").exists()) {
                        if (dsp.child("position").getValue().toString().equalsIgnoreCase("Founder")||dsp.child("position").getValue().toString().toLowerCase().contains("Head".toLowerCase())||dsp.child("position").getValue().toString().toLowerCase().contains("Core".toLowerCase())) {
                            ch.setName(dsp.child("name").getValue().toString());
                            child_list1.add(ch);
                        }
                        else if (dsp.child("position").getValue().toString().toLowerCase().contains("Learner".toLowerCase())) {
                            ch.setName(dsp.child("name").getValue().toString());
                            child_list3.add(ch);
                        }
                    }
                    else
                    {
                        ch.setName(dsp.child("name").getValue().toString());
                        child_list4.add(ch);
                    }}
                Child ch = new Child();
                ch.setName("Android App Development");
                child_list2.add(ch);
                Child ch1 = new Child();
                ch1.setName("Coding");
                child_list2.add(ch1);
                Child ch2 = new Child();
                ch2.setName("Electronics");
                child_list2.add(ch2);
                Child ch3 = new Child();
                ch3.setName("Innovation");
                child_list2.add(ch3);
                Child ch4 = new Child();
                ch4.setName("Management");
                child_list2.add(ch4);
                Child ch5 = new Child();
                ch5.setName("Marketing");
                child_list2.add(ch5);
                Child ch6 = new Child();
                ch6.setName("Mechanical");
                child_list2.add(ch6);
                Child ch7 = new Child();
                ch7.setName("Video Editing");
                child_list2.add(ch7);
                Child ch8 = new Child();
                ch8.setName("Photoshop");
                child_list2.add(ch8);
                Child ch9 = new Child();
                ch9.setName("Robotics");
                child_list2.add(ch9);
                Child ch10 = new Child();
                ch10.setName("Web Development");
                child_list2.add(ch10);

                gru1.setItems(child_list1);
                gru2.setItems(child_list2);
                gru3.setItems(child_list3);
                gru4.setItems(child_list4);
                group_list.add(gru1);
                group_list.add(gru2);
                group_list.add(gru3);
                group_list.add(gru4);
                ExpListItems = group_list;
                ExpAdapter = new ExpandListAdapter(MemberDetails.this, ExpListItems);

                ExpandList.setAdapter(ExpAdapter);
                progressBar.setVisibility(View.INVISIBLE);
                 ExpandList.setOnChildClickListener(new OnChildClickListener() {

                                                @Override
                                                public boolean onChildClick(ExpandableListView parent, View v,
                                                                            int groupPosition, int childPosition, long id) {
                                                    String group_name = ExpListItems.get(groupPosition).getName();

                                                    ArrayList<Child> ch_list = ExpListItems.get(
                                                            groupPosition).getItems();
                                                    String child_name = ch_list.get(childPosition).getName();
                                                    if(group_name.equalsIgnoreCase("Mentors"))
                                                    {
                                                        Bundle bundle = new Bundle();
                                                        bundle.putString("department", child_name);
                                                        Intent intent = new Intent(MemberDetails.this, list.class);
                                                        intent.putExtras(bundle);
                                                        startActivity(intent);
                                                    }
                                                    else {
                                                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                                            if (dsp.child("name").getValue().equals(child_name)) {
                                                                String user_id = dsp.getKey();
                                                                Bundle bundle = new Bundle();
                                                                bundle.putString("id", user_id);
                                                                Intent intent = new Intent(MemberDetails.this, Member_Information.class);
                                                                intent.putExtras(bundle);
                                                                startActivity(intent);
                                                            }
                                                        }
                                                    }
                                                    return false;
                                                }
                                            });

                                            ExpandList.setOnGroupExpandListener(new OnGroupExpandListener() {

                                                @Override
                                                public void onGroupExpand(int groupPosition) {
                                                    String group_name = ExpListItems.get(groupPosition).getName();

                                                }
                                            });

                                            ExpandList.setOnGroupCollapseListener(new OnGroupCollapseListener() {

                                                @Override
                                                public void onGroupCollapse(int groupPosition) {
                                                    String group_name = ExpListItems.get(groupPosition).getName();

                                                }
                                            });
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    }
        );
    }
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
                finish();
                return true;

            case R.id.con:
                Intent l = new Intent(this, contact.class);
                startActivity(l);
                finish();
                return true;

            case R.id.details:
                Intent de = new Intent(this, MemberDetails.class);
                startActivity(de);
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
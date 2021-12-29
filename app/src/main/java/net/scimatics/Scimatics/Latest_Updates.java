package net.scimatics.Scimatics;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class Latest_Updates extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout d;
    private ActionBarDrawerToggle t;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static final Integer[] Photos = {};
    private ImageView image;
    private ProgressBar progress;
    private StorageReference mStorageRef;
    private ArrayList<Bitmap> PhotosArray = new ArrayList<Bitmap>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_updates);
        d = (DrawerLayout) findViewById(R.id.draw);
        t = new ActionBarDrawerToggle(this, d, R.string.open, R.string.close);
        d.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setNavigationViewListener();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        final long ONE_MEGABYTE = 1024 * 1024;
        image = (ImageView) findViewById(R.id.image);
        progress=(ProgressBar)findViewById(R.id.progressBar2);
        String path = "Updates/poster.jpg";
        mStorageRef.child(path).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                image.setImageBitmap(bitmap);
                d.getRootView().setBackgroundColor(Color.parseColor("#000000"));
            }
        }).addOnCompleteListener(new OnCompleteListener<byte[]>() {
            @Override
            public void onComplete(@NonNull Task<byte[]> task) {
                progress.setVisibility(View.INVISIBLE);
            }
        });
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



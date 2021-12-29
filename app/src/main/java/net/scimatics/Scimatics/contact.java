package net.scimatics.Scimatics;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.firebase.auth.FirebaseAuth;

public class contact extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private DrawerLayout d;
    private ActionBarDrawerToggle t;
    private WebView myWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        d = (DrawerLayout) findViewById(R.id.draw);
        t = new ActionBarDrawerToggle(this, d, R.string.open, R.string.close);
        d.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setNavigationViewListener();
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
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);

    }
}

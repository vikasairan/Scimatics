package net.scimatics.Scimatics;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout d;
    private ActionBarDrawerToggle t;
    private WebView webview;
    private ProgressDialog progressBar;
    private static final String TAG = "Main";
    //NavigationView v;
    //Toolbar t = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        d = (DrawerLayout) findViewById(R.id.draw);
        t = new ActionBarDrawerToggle(this, d, R.string.open, R.string.close);
        d.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setNavigationViewListener();
        this.webview = (WebView) findViewById(R.id.aboutWebView);
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        progressBar = ProgressDialog.show(this, "", "Loading...");

        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "Processing...");
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(MainActivity.this, description, Toast.LENGTH_SHORT).show();
                alertDialog.setTitle("Error");
                alertDialog.setMessage(description);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alertDialog.show();
            }
        });
        webview.loadUrl("http://www.leadsociety.in/");

    }
    public void onBackPressed(){
        if(webview.canGoBack()){
            webview.goBack();

        }
        else {
            super.onBackPressed();
        }
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



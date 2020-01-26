package kslv.team.kaappaan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import kslv.team.kaappaan.functions.Login;
import kslv.team.kaappaan.functions.Policeview;
import kslv.team.kaappaan.functions.complaint;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class home extends AppCompatActivity {



  //textview
    TextView inslogin;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;


    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        checkAndRequestPermissions();

        inslogin=findViewById(R.id.inslogin);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        Log.e("loginstatus",pref.getBoolean("login",false)+"yes");
        if(pref.getBoolean("login",false))
        {

            //Log.e("hai","hello");
            inslogin.setVisibility(View.GONE);
        }
        else {
            inslogin.setPaintFlags(inslogin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            blink();

        }
          //  inslogin.setVisibility(View.VISIBLE);





        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
//        NavController navController = Navigation.findNavController(this, R.id.mobile_navigation);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.mobile_navigation);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }


    private void blink(){
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 700;    //in milissegunds
                try{Thread.sleep(timeToBlink);}catch (Exception e) {}
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                       // TextView txt = (TextView) findViewById(R.id.usage);
                        if(inslogin.getVisibility() == View.VISIBLE){
                            inslogin.setVisibility(View.INVISIBLE);
                        }else{
                            inslogin.setVisibility(View.VISIBLE);
                        }
                        blink();
                    }
                });
            }
        }).start();
    }

    private  boolean checkAndRequestPermissions() {
        int permissioncoarloc = ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION);
        int permissionfineloc = ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION);
        int callphone = ContextCompat.checkSelfPermission(this,CALL_PHONE);
        int read = ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE);
        int write = ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE);
        int readphone=ContextCompat.checkSelfPermission(this,READ_PHONE_STATE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissioncoarloc!= PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(ACCESS_COARSE_LOCATION);
        }
        if ( permissionfineloc!= PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(ACCESS_FINE_LOCATION);
        }
        if ( callphone!= PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(CALL_PHONE);
        }
        if (read!= PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(READ_EXTERNAL_STORAGE);
        }
        if ( write!= PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(WRITE_EXTERNAL_STORAGE);
        }
        if ( readphone!= PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(READ_PHONE_STATE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            //checkAndRequestPermissions();

        }

        return true;

    }

    public void login(View V)
    {
        Intent intent=new Intent(home.this, Login.class);
        startActivity(intent);
    }

    public void fir(View V)
    {
        Intent intent=new Intent(home.this, complaint.class);
        startActivity(intent);
    }
    public void knowstation(View V)
    {
        Intent intent=new Intent(home.this, Policeview.class);
        startActivity(intent);
    }
}

package kslv.team.kaappaan.welcome;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import kslv.team.kaappaan.functions.Login;
import kslv.team.kaappaan.R;
import kslv.team.kaappaan.functions.Police_login;


public class SplashScreen extends Activity {

    //to 03_12


    private static int SPLASH_TIME_OUT = 2000;

    ImageView imv;
    Animation fromtop;
    SharedPreferences pref;
   SharedPreferences.Editor editor;



    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        imv = findViewById ( R.id.spl1 );

       // if(checkAndRequestPermissions()) {
        fromtop = AnimationUtils.loadAnimation ( this , R.anim.fromtop );
        fromtop.setDuration ( 1500 );
        imv.setAnimation ( fromtop );

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                finish();
                Intent intent = new Intent(SplashScreen.this, Police_login.class);

                startActivity(intent);

            }

        }, SPLASH_TIME_OUT);




     }




}
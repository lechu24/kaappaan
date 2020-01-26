package kslv.team.kaappaan.welcome;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import kslv.team.kaappaan.MainActivity;
import kslv.team.kaappaan.R;
import kslv.team.kaappaan.functions.complaint;
import kslv.team.kaappaan.home;


public class SplashScreen extends Activity {

    //to 03_12


    private static int SPLASH_TIME_OUT = 2000;

    ImageView imv;
    //Animation fromtop;
    SharedPreferences pref;
   SharedPreferences.Editor editor;



    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        imv = findViewById ( R.id.spl1 );

       // if(checkAndRequestPermissions()) {



        final Animation zoomAnimation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fromtop);
        imv.startAnimation(zoomAnimation);
        zoomAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Handler mHandler = new Handler(getMainLooper());
                Runnable mRunnable = new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        Intent i = new Intent(SplashScreen.this,home.class);
                        startActivity(i);

                    }
                };
                mHandler.postDelayed(mRunnable, 1500);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });



     }




}
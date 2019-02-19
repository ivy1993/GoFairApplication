package xyz.gofair.gofairapplication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {

    private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                session = new Session(SplashScreen.this);
                if (!session.isFirstTimeLaunch()) {
                    Intent i= new Intent(SplashScreen.this,MainMapsActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Intent i= new Intent(SplashScreen.this,MainMapsActivity.class);
                    startActivity(i);

                }

                finish();
            }
        },4000);


    }
}

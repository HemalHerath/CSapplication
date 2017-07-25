package com.example.beiber.csapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final ImageView iv = (ImageView) findViewById(R.id.ring);
        final ImageView iv2 = (ImageView) findViewById(R.id.splash);

        final Animation an = AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotate);
        final Animation an1 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotate2);
        final Animation an2 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.abc_fade_out);

        iv.startAnimation(an);
        iv2.startAnimation(an1);

        an.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {

            }
            @Override
            public void onAnimationEnd(Animation animation)
            {
                iv.startAnimation(an2);
                iv2.startAnimation(an2);
                finish();
                Intent intent = new Intent(getBaseContext() , MainTabActivity.class);
                startActivity(intent);
            }
            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
    }
}

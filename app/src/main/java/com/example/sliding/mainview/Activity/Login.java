package com.example.sliding.mainview.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sliding.mainview.R;

public class Login extends Activity {

    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button = (Button) this.findViewById(R.id.login_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                animatedStartActivity();
                Login.this.finish();
            }
        });
    }

//    private void animatedStartActivity() {
//        // we only animateOut this activity here.
//        // The new activity will animateIn from its onResume() - be sure to implement it.
//        final Intent intent = new Intent(getApplicationContext(), StartActivity.class);
//        // disable default animation for new intent
//        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//        ActivitySwitcher.animationOut(findViewById(R.id.secound), getWindowManager(), new ActivitySwitcher.AnimationFinishedListener() {
//            @Override
//            public void onAnimationFinished() {
//                startActivity(intent);
////                StartActivity.this.finish();
//            }
//        });
//    }

    @Override
    public void finish() {
        ActivitySwitcher.animationOut(findViewById(R.id.first), getWindowManager(), new ActivitySwitcher.AnimationFinishedListener() {
            @Override
            public void onAnimationFinished() {
                Intent intent = new Intent(Login.this, StartActivity.class);
                Login.this.startActivity(intent);
                Login.super.finish();
                // disable default animation
                overridePendingTransition(0, 0);
            }
        });
    }
}

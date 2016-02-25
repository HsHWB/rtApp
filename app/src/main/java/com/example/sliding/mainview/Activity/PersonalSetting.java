package com.example.sliding.mainview.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.ActivityClass;

public class PersonalSetting extends Activity {

    private TextView registerText;
    private TextView changePassword;
    private TextView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_setting);
        ActivityClass.activityList.add(this);
        registerText = (TextView) this.findViewById(R.id.setting_register);
        changePassword = (TextView) this.findViewById(R.id.setting_changpassword);
        logout = (TextView) this.findViewById(R.id.setting_logout);

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(PersonalSetting.this, UserActivity.class);
                intent.putExtra("tag", "register");
                PersonalSetting.this.startActivity(intent);
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(PersonalSetting.this, UserActivity.class);
                intent.putExtra("tag", "changePassword");
                PersonalSetting.this.startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(PersonalSetting.this, StartActivity.class);
                startActivity(intent);
                ActivityClass.delete();
            }
        });
    }
}

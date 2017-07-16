package com.example.user.squash;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBarRocket,seekBarBall,seekBarUpd;

    Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ctx = this;

        seekBarBall = findViewById(R.id.speedballbar);
        seekBarRocket =  findViewById(R.id.speedrocketbar);
        seekBarUpd =  findViewById(R.id.speedUpd);
        findViewById(R.id.startbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ctx,GameActivity.class);
                i.putExtra("speed_ball",seekBarBall.getProgress());
                i.putExtra("speed_rocket",seekBarRocket.getProgress());
                i.putExtra("speed_upd",seekBarUpd.getProgress());
                startActivity(i);
            }
        });

    }
}

package com.ivart.makedecision;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.ivart.makedecision.Activities.AboutUsActivity;
import com.ivart.makedecision.Activities.MakeDecisionActivity;
import com.ivart.makedecision.Activities.MyDecisionsActivity;
import com.ivart.makedecision.Drawables.Drawables;


public class MainActivity extends Activity implements View.OnClickListener{

    Button makeDecision;
    Button myDecisions;
    Button aboutUs;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // enable transitions
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_main);

        makeDecision =(Button)findViewById(R.id.btn_make_decision);
        makeDecision.setBackground(Drawables.getSelectableDrawableFor(Color.parseColor("#a6ff9b")));
        myDecisions =(Button)findViewById(R.id.btn_get_my_decisions);
        myDecisions.setBackground(Drawables.getSelectableDrawableFor(Color.parseColor("#8470ff")));
        aboutUs =(Button)findViewById(R.id.btn_about_us);
        aboutUs.setBackground(Drawables.getSelectableDrawableFor(Color.parseColor("#9bfff4")));
        makeDecision.setOnClickListener(this);
        myDecisions.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_make_decision:
                getWindow().setExitTransition(new Explode());
                Intent intent = new Intent(this,MakeDecisionActivity.class);
                startActivity(intent,
                        ActivityOptions
                                .makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.btn_get_my_decisions:
                getWindow().setExitTransition(new Explode());
                intent = new Intent(this,MyDecisionsActivity.class);
                startActivity(intent,
                        ActivityOptions
                                .makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.btn_about_us:
                getWindow().setExitTransition(new Explode());
                intent = new Intent(this,AboutUsActivity.class);
                startActivity(intent);
                startActivity(intent,
                        ActivityOptions
                                .makeSceneTransitionAnimation(this).toBundle());
                break;
        }
    }
}

package com.ivart.makedecision.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ivart.makedecision.Model.Decision;
import com.ivart.makedecision.R;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Ivan on 8/16/2016.
 */
public class MyDecisionsActivity extends Activity{

    TextView myDecisions;
    Button clearDecisions;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_decisions);
        myDecisions = (TextView)findViewById(R.id.txtv_my_decisions);
        clearDecisions = (Button)findViewById(R.id.btn_clear_decisions);
        realm = Realm.getDefaultInstance();
        displayDesiosions();

        clearDecisions.setOnClickListener(new View.OnClickListener() {
            RealmResults<Decision> results = realm.where(Decision.class).findAll();
            @Override
            public void onClick(View v) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        results.deleteAllFromRealm();
                        myDecisions.setText("");
                    }
                });
            }
        });
    }

    private void displayDesiosions() {
        String decisions = "";
        RealmResults<Decision> results = realm.where(Decision.class).findAll();
        for(Decision decision : results){
            decisions += decision.toString();
        }
        myDecisions.setText(decisions);
    }
}

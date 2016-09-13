package com.ivart.makedecision.Activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ivart.makedecision.BaseApplication;
import com.ivart.makedecision.Drawables.Drawables;
import com.ivart.makedecision.MainActivity;
import com.ivart.makedecision.Model.Decision;
import com.ivart.makedecision.R;

import io.realm.Realm;

public class MakeDecisionActivity extends Activity {

    EditText decisionQuestion;
    Button addDecision;
    Decision decision;
    Realm realm;
    Long id;
    Long editDecisionId;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_decision);
        Intent intent = getIntent();
        editDecisionId = intent.getLongExtra("edititngId",0);

        realm = Realm.getDefaultInstance();
        decisionQuestion = (EditText) findViewById(R.id.edt_decision_question);
        addDecision = (Button) findViewById(R.id.btn_add_decision);
        addDecision.setBackground(Drawables.getSelectableDrawableFor(Color.parseColor("#a6ff9b")));

        if(editDecisionId !=0) {
            addDecision.setText(R.string.edit_decision_name);
            addDecision.invalidate();
        }

        addDecision.setOnClickListener(new View.OnClickListener() {
            String name;
            @Override
            public void onClick(View v) {
                name = decisionQuestion.getText().toString();
                if (name.equals("")||name == null) {
                    Toast.makeText(MakeDecisionActivity.this, R.string.enter_question, Toast.LENGTH_SHORT).show();
                } else {
                    if(editDecisionId !=0){
                        Toast.makeText(MakeDecisionActivity.this,"" + editDecisionId,Toast.LENGTH_SHORT).show();
                        realm.executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                Decision result = realm.where(Decision.class).equalTo("id",editDecisionId).findFirst();
                                result.setmDecisionName(decisionQuestion.getText().toString());
                                Intent intent = new Intent(MakeDecisionActivity.this,MyDecisionsActivity.class);
                                startActivity(intent);
                            }
                        });

                    }
                    else {
                        saveIntoDatabase(name);
                    }
                }
            }
        });
    }

    public void saveIntoDatabase(final String decisionName) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                decision = realm.createObject(Decision.class);
                id = BaseApplication.productPrimaryKey.getAndIncrement();
                decision.setId(id);
                decision.setmDecisionName(decisionName);
                Intent intent = new Intent(MakeDecisionActivity.this,SquareActivity.class);
                intent.putExtra("decisionId", id);
                startActivity(intent);
                Log.d("LOG",""+id);
            }
        });
    }

}

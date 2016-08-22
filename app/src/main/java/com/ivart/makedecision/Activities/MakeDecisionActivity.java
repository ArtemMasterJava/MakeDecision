package com.ivart.makedecision.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ivart.makedecision.BaseApplication;
import com.ivart.makedecision.Model.Decision;
import com.ivart.makedecision.R;

import io.realm.Realm;

public class MakeDecisionActivity extends AppCompatActivity {

    EditText decisionQuestion;
    Button addDecision;
    Decision decision;
    Realm realm;
    Long decisionId;
    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_decision);
        realm = Realm.getDefaultInstance();
        decisionQuestion = (EditText) findViewById(R.id.edt_decision_question);
        addDecision = (Button) findViewById(R.id.btn_add_decision);

        addDecision.setOnClickListener(new View.OnClickListener() {
            String name;
            @Override
            public void onClick(View v) {
                name = decisionQuestion.getText().toString();
                if (name.equals("")||name == null) {
                    Toast.makeText(MakeDecisionActivity.this, R.string.enter_question, Toast.LENGTH_SHORT).show();
                } else {
                    saveIntoDatabase(name);
                    decisionQuestion.getText().clear();
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

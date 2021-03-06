package com.ivart.makedecision.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.ivart.makedecision.BaseApplication;
import com.ivart.makedecision.Model.DecisionDescription;
import com.ivart.makedecision.R;

import io.realm.Realm;

public class DescriptionActivity extends Activity implements View.OnClickListener {

    EditText description;
    RatingBar raitBar;
    Long decisionId;
    int square;
    float raiting;
    Realm realm;
    DecisionDescription decisionDescription;

    long editId;
    int editSquare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        Intent intent = getIntent();
        decisionId = intent.getLongExtra("idDecision", 0L);
        editId = intent.getLongExtra("editDecisionId", 0L);
        editSquare = intent.getIntExtra("editSquare", 0);
        realm = Realm.getDefaultInstance();
        square = intent.getIntExtra("squareNumber", 0);
        description = (EditText) findViewById(R.id.edt_decision_description);
        raitBar = (RatingBar) findViewById(R.id.raiting_bar);

        description.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(description.getWindowToken(), 0);
                }
                return false;
            }
        });
    }


    @Override
    public void onClick(View v) {
        String text = description.getText().toString();
        raiting = raitBar.getRating();
        switch (v.getId()) {
            case R.id.btn_add_description:
                if (text.isEmpty() || raiting == 0) {
                    Toast.makeText(this, R.string.please_enter_description, Toast.LENGTH_LONG).show();
                } else {
                    if (editSquare != 0) {
                        text = text.trim();
                        saveIntoDatabase(editId, editSquare, text, raiting);
                        DescriptionActivity.this.finish();
                    } else {
                        text = text.trim();
                        saveIntoDatabase(decisionId, square, text, raiting);
                        DescriptionActivity.this.finish();
                    }
                }
                break;
        }
    }

    public void saveIntoDatabase(final Long decisionId, final int square, final String descriptionText, final float rait) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                decisionDescription = realm.createObject(DecisionDescription.class);
                Long id = BaseApplication.productPrimaryKey.getAndIncrement();
                decisionDescription.setId(id);
                decisionDescription.setDecisionId(decisionId);
                decisionDescription.setSquare(square);
                decisionDescription.setDescriptionText(descriptionText);
                decisionDescription.setRaiting(rait);

            }
        });
        description.getText().clear();
        Toast.makeText(DescriptionActivity.this, R.string.description_was_added, Toast.LENGTH_LONG).show();
    }

}

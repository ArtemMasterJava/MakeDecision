package com.ivart.makedecision.Model;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Decision extends RealmObject{
    public void setId(Long id) {
        this.id = id;
    }

    @PrimaryKey
    private Long id;
    public String mDecisionName;

    public Decision() {
    }

    public Decision(String mDecisionName) {
        this.mDecisionName = mDecisionName;
    }

    public Long getId() {
        return id;
    }

    public String getmDecisionName() {
        return mDecisionName;
    }

    public void setmDecisionName(String mDecisionName) {
        this.mDecisionName = mDecisionName;
    }

    @Override
    public String toString() {
        return "Decision{" +
                "id=" + id +
                ", mDecisionName='" + mDecisionName + '\'' +
                '}';
    }
}

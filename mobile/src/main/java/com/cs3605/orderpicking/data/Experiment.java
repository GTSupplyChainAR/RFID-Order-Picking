package com.cs3605.orderpicking.data;

import java.util.ArrayList;

public class Experiment {

    private int id;
    private String experimentName;
    private ArrayList<Trial> trialList;

    public Experiment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExperimentName() {
        return experimentName;
    }

    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

    public ArrayList<Trial> getTrialList() {
        return trialList;
    }

    public void setTrialList(ArrayList<Trial> trialList) {
        this.trialList = trialList;
    }

    public ArrayList<String> getTrialListIds() {
        ArrayList<String> idList = new ArrayList<>();
        for (Trial trial : trialList) {
            idList.add(String.valueOf(trial.getId()));
        }
        return idList;
    }
}

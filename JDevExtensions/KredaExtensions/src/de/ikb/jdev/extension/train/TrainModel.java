package de.ikb.jdev.extension.train;

import de.ikb.jdev.extension.utils.DataModel;

import java.util.ArrayList;

public class TrainModel extends DataModel {

    private String trainName;
    private String configName;
    private ArrayList<Step> steps;
    private String firstStepName;

    public void initSteps(ArrayList<Step> steps) {
        this.steps = steps;
        firstStepName = steps.get(0).getName();
    }

    @Override
    public String toString() {
        String trainModelAsString = "";
        String stepsAsString = "[ ";
        for (Step step : steps) {
            stepsAsString = stepsAsString + step;
        }
        stepsAsString = stepsAsString + " ]";
        trainModelAsString =
            "trainName: " + trainName + "\nconfigName: " + configName + "\nfirstStepName: " + firstStepName +
            "\nsteps: " + stepsAsString + "\n" + super.toString();
        return trainModelAsString;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigName() {
        return configName;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public String getFirstStepName() {
        return firstStepName;
    }

}

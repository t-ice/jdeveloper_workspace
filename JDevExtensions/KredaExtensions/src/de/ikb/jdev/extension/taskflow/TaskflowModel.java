package de.ikb.jdev.extension.taskflow;

import de.ikb.jdev.extension.utils.DataModel;


public class TaskflowModel extends DataModel {
    private String taskflowName;

    public void setTaskflowName(String taskflowName) {
        this.taskflowName = taskflowName;
    }

    public String getTaskflowName() {
        return taskflowName;
    }

    @Override
    public String toString() {
        String dataModelAsString = "taskflowName: " + taskflowName + super.toString();
        return dataModelAsString;
    }

}

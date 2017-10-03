package de.ikb.jdev.extension.utils;


public class DataModel {
    private String javaSourceDirPath;
    private String taskflowAbsoluteDirPath;
    private String taskflowDirPath;
    private String uiBundleName;
    private String uiBundleVarName;
    private String jaznFilePath;
    private String resourceBundleFilePath;
    private String projectName;
    private String packageName;
    private String jsfDirPath;
    private String jsfAbsoluteDirPath;

    public void setJavaSourceDirPath(String javaSourceDirPath) {
        this.javaSourceDirPath = javaSourceDirPath;
    }

    public String getJavaSourceDirPath() {
        return javaSourceDirPath;
    }

    public void setTaskflowAbsoluteDirPath(String taskflowAbsoluteDirPath) {
        this.taskflowAbsoluteDirPath = taskflowAbsoluteDirPath;
    }

    public String getTaskflowAbsoluteDirPath() {
        return taskflowAbsoluteDirPath;
    }

    public void setTaskflowDirPath(String taskflowDirPath) {
        this.taskflowDirPath = taskflowDirPath;
    }

    public String getTaskflowDirPath() {
        return taskflowDirPath;
    }

    public void setUiBundleName(String uiBundleName) {
        this.uiBundleName = uiBundleName;
    }

    public String getUiBundleName() {
        return uiBundleName;
    }

    public void setJaznFilePath(String jaznFilePath) {
        this.jaznFilePath = jaznFilePath;
    }

    public String getJaznFilePath() {
        return jaznFilePath;
    }

    public void setResourceBundleFilePath(String resourceBundleFilePath) {
        this.resourceBundleFilePath = resourceBundleFilePath;
    }

    public String getResourceBundleFilePath() {
        return resourceBundleFilePath;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setJsfDirPath(String jsfDirPath) {
        this.jsfDirPath = jsfDirPath;
    }

    public String getJsfDirPath() {
        return jsfDirPath;
    }

    public void setJsfAbsoluteDirPath(String jsfAbsoluteDirPath) {
        this.jsfAbsoluteDirPath = jsfAbsoluteDirPath;
    }

    public String getJsfAbsoluteDirPath() {
        return jsfAbsoluteDirPath;
    }
    
    @Override
    public String toString() {
        String dataModelAsString ="packageName: " + packageName  +
            "\nuiBundleName: " + uiBundleName +"\nuiBundleVarName: " + uiBundleVarName + "\njaznFilePath: " + jaznFilePath + "\nresourceBundleFilePath: " +
            resourceBundleFilePath + "\nprojectName: " + projectName + "\ntaskflowDirPath: " + taskflowDirPath +
            "\ntaskflowAbsoluteDirPath: " + taskflowAbsoluteDirPath + "\njsfDirPath: " + jsfDirPath +
            "\njsfAbsoluteDirPath: " + jsfAbsoluteDirPath ;
        return dataModelAsString;
    }

    public void setUiBundleVarName(String uiBundleVarName) {
        this.uiBundleVarName = uiBundleVarName;
    }

    public String getUiBundleVarName() {
        return uiBundleVarName;
    }
}

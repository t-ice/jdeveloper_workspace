package de.ikb.jdev.extension.train;


import de.ikb.jdev.extension.utils.FileAndPathCollector;
import de.ikb.jdev.extension.utils.StringHelper;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import oracle.ide.Context;
import oracle.ide.Ide;
import oracle.ide.model.Project;
import oracle.ide.wizard.Invokable;

/**
 * Implementation of the "TrainGenerator" gallery item.
 */
public final class TrainGeneratorWizard implements Invokable {


    private static final int FIELD_WIDTH = 30;

    public boolean invoke(Context context) {
        Project activeProject = Ide.getActiveProject();

        String projectDirPath = activeProject.getBaseDirectory();

        String packageInfix = "controller.train";
        String jsfPathInfix = "pages/";
        String taskflowPathSuffix = "taskflows/train/";

        FileAndPathCollector projectFileProcessor = new FileAndPathCollector();
        projectFileProcessor.collectAll(Paths.get(projectDirPath));

        FileAndPathCollector workspaceFileProcessor = new FileAndPathCollector();
        workspaceFileProcessor.collectAll(Paths.get(Ide.getActiveWorkspace().getBaseDirectory()));
        final Path resourceBundleFilePath = projectFileProcessor.getCollectedFile("uiBundle.properties");

        // ist erforderlich
        if (projectDirPath != null && resourceBundleFilePath != null) {
            final String resourceBundleFilePathAsString = resourceBundleFilePath.toString();
            final String resourceBundleName = getResourceBundleName(resourceBundleFilePathAsString);
            final Path jaznFilePath = workspaceFileProcessor.getCollectedFile("jazn-data.xml");
            String jaznFilePathAsString = null;

            // ist nicht zwangsweise erforderlich
            if (jaznFilePath != null) {
                jaznFilePathAsString = jaznFilePath.toString();
            }

            // Erstellung Array vom Datentyp Object, Hinzufügen der Komponenten
            JTextField trainNameField = new JTextField(FIELD_WIDTH);
            trainNameField.setToolTipText("UpperCamelCase verwenden, bspw. ZinsenGrundinfo");
            JTextField configNameField = new JTextField(FIELD_WIDTH);
            configNameField.setToolTipText("UpperCamelCase verwenden, bspw. Nominalzins oder Leitzins");
            JTextField stepsField = new JTextField(FIELD_WIDTH);
            stepsField.setToolTipText("UpperCamelCase verwenden, bspw. Allgemein, Bemerkung, Freigabe");
            JTextField packageInfixField = new JTextField(FIELD_WIDTH);
            JTextField jsfPathInfixField = new JTextField(FIELD_WIDTH);
            JTextField taskflowPathSuffixField = new JTextField(FIELD_WIDTH);
            packageInfixField.setText(packageInfix);
            jsfPathInfixField.setText(jsfPathInfix);
            taskflowPathSuffixField.setText(taskflowPathSuffix);
                        
            JPanel panel =
                baueLayout(trainNameField, configNameField, stepsField, packageInfixField, jsfPathInfixField,
                           taskflowPathSuffixField);


            JOptionPane pane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
            pane.createDialog(null, "KreDaTrainWizard").setVisible(true);

            Object selectedValue = pane.getValue();
            int n = -1;


            if (selectedValue == null)
                n = JOptionPane.CLOSED_OPTION;
            else
                n = Integer.parseInt(selectedValue.toString());


            if (n == JOptionPane.YES_OPTION) {

                TrainModel trainModel = new TrainModel();

                // optional user input
                packageInfix = packageInfixField.getText();
                jsfPathInfix = jsfPathInfixField.getText();
                taskflowPathSuffix = taskflowPathSuffixField.getText();

                // trainName
                final String uncapTrain = StringHelper.uncapFirst(trainNameField.getText()) + "Train";
                final String uncapTrainDir = uncapTrain + "/";

                // java package und path
                final String projectName = activeProject.getShortLabel().replaceAll(".jpr", "");
                trainModel.setProjectName(projectName);
                final String packageName =
                    "de." + projectName + "." + (packageInfix.isEmpty() ? "" : packageInfix + ".") + uncapTrain;
                trainModel.setPackageName(packageName);
                trainModel.setJavaSourceDirPath(activeProject.getBaseDirectory() + "/src/" +
                                                packageName.replace('.', '/') + "/");
                
                // jazn-data, resourcBundle und absoluter bundleName ermitteln
                trainModel.setJaznFilePath(jaznFilePathAsString);
                trainModel.setResourceBundleFilePath(resourceBundleFilePathAsString);
                trainModel.setUiBundleName(resourceBundleName);
                trainModel.setUiBundleVarName(projectName.replaceAll("\\.", "_"));

                // taskflow und jsf paths
                final String publicHtmlAbsoluteDirPath = activeProject.getBaseDirectory() + "/public_html";
                final String webinfAndProjectDirPath = "/WEB-INF/" + projectName.replaceAll(".ui", "") + "/";
                trainModel.setJsfAbsoluteDirPath(publicHtmlAbsoluteDirPath + webinfAndProjectDirPath + jsfPathInfix +
                                                 uncapTrainDir);
                trainModel.setJsfDirPath(webinfAndProjectDirPath + jsfPathInfix + uncapTrainDir);
                trainModel.setTaskflowAbsoluteDirPath(publicHtmlAbsoluteDirPath + webinfAndProjectDirPath +
                                                      taskflowPathSuffix);
                trainModel.setTaskflowDirPath(webinfAndProjectDirPath + taskflowPathSuffix);

                // user input
                String[] splittedSteps = stepsField.getText().split(",");
                ArrayList<Step> stepList = new ArrayList<Step>();
                String trimmedCurrentStep;
                for (int i = 0; i < splittedSteps.length; i++) {
                    trimmedCurrentStep = StringHelper.capFirst(splittedSteps[i].trim());
                    Step currentStep = new Step();
                    currentStep.setName(StringHelper.ersetzeUmlaute(trimmedCurrentStep));
                    currentStep.setEscapedName(StringHelper.escapeJava(trimmedCurrentStep));
                    stepList.add(currentStep);
                }

                trainModel.initSteps(stepList);

                // user input
                trainModel.setTrainName(StringHelper.capFirst(trainNameField.getText()));

                // user input
                trainModel.setConfigName(StringHelper.capFirst(configNameField.getText()));

                //generate Train
                System.out.println(trainModel);
                try {
                    TrainGenerator trainGenerator = new TrainGenerator();
                    trainGenerator.generate(trainModel);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } else {
            final JOptionPane errorPane =
                new JOptionPane("Bitte vorher ein ui-Projekt öffnen/expandieren und sicherstellen, dass ein uiBundle angelegt ist!");
            errorPane.createDialog(null, "KreDaTrainWizard").setVisible(true);
        }

        return true;

    }

    public String getResourceBundleName(String absoluteBundlePath) {
        final String dottedAbsoluteBundlePath = absoluteBundlePath.replaceAll("\\\\", ".");
        final String[] splittedPath = dottedAbsoluteBundlePath.split(".src.");
        String bundleName = splittedPath[1];
        return bundleName.replaceAll(".properties", "");
    }


    private JPanel baueLayout(JTextField trainNameField, JTextField configNameField, JTextField stepsField,
                              JTextField packageInfixField, JTextField jsfPathInfixField,
                              JTextField taskflowPathSuffixField) {
        // Layout
        JPanel newPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);


        // Komponenten zum Layout hinzufuegen
        constraints.gridx = 0;
        constraints.gridy = 0;
        newPanel.add(new JLabel("Name des Trains:"), constraints);
        constraints.gridx = 1;
        newPanel.add(trainNameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        newPanel.add(new JLabel("Konfigurationsname:"), constraints);
        constraints.gridx = 1;
        newPanel.add(configNameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        newPanel.add(new JLabel("Kommaseparierte Liste von Stepnamen:"), constraints);
        constraints.gridx = 1;
        newPanel.add(stepsField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;       
        newPanel.add(new JLabel("Optional - Package-Infix:"), constraints);
        constraints.gridx = 1;
        newPanel.add(packageInfixField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        newPanel.add(new JLabel("Optional - JSF-Pfad-Infix:"), constraints);
        constraints.gridx = 1;
        newPanel.add(jsfPathInfixField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        newPanel.add(new JLabel("Optional - Taskflow-Pfad-Suffix:"), constraints);
        constraints.gridx = 1;
        newPanel.add(taskflowPathSuffixField, constraints);
        
        
        return newPanel;
    }
}

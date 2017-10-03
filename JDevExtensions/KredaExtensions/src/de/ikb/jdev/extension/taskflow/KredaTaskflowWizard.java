package de.ikb.jdev.extension.taskflow;

import de.ikb.jdev.extension.utils.FileAndPathCollector;
import de.ikb.jdev.extension.utils.StringHelper;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import oracle.ide.Context;
import oracle.ide.Ide;
import oracle.ide.model.Project;
import oracle.ide.wizard.Invokable;

/**
 * Implementation of the "Kreda Taskflow" gallery item.
 */
public final class KredaTaskflowWizard implements Invokable {
    
    private static final int FIELD_WIDTH = 30;

    public boolean invoke(Context context) {
        Project activeProject = Ide.getActiveProject();

        String projectDirPath = activeProject.getBaseDirectory();

        String packageSuffix = "controller";
        String jsfPathSuffix = "fragments/";
        String taskflowPathSuffix = "taskflows/";

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
            JTextField taskflowNameField = new JTextField(FIELD_WIDTH);
            taskflowNameField.setToolTipText("UpperCamelCase verwenden, bspw. ZinsenGrundinfo");
            JTextField packageSuffixField = new JTextField(FIELD_WIDTH);
            JTextField taskflowPathSuffixField = new JTextField(FIELD_WIDTH);
            JTextField jsfPathSuffixField = new JTextField(FIELD_WIDTH);
            packageSuffixField.setText(packageSuffix);
            taskflowPathSuffixField.setText(taskflowPathSuffix);
            jsfPathSuffixField.setText(jsfPathSuffix);

            // Erstelle Layout
            JPanel panel =
                baueLayout(taskflowNameField, packageSuffixField, jsfPathSuffixField, taskflowPathSuffixField);

            JOptionPane pane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
            pane.createDialog(null, "KreDaTaskflowWizard").setVisible(true);

            Object selectedValue = pane.getValue();
            int n = -1;


            if (selectedValue == null)
                n = JOptionPane.CLOSED_OPTION;
            else
                n = Integer.parseInt(selectedValue.toString());


            if (n == JOptionPane.YES_OPTION) {

                TaskflowModel taskflowModel = new TaskflowModel();

                // optional user input
                packageSuffix = packageSuffixField.getText();
                taskflowPathSuffix = taskflowPathSuffixField.getText();
                jsfPathSuffix = jsfPathSuffixField.getText();
                
                // java package und path
                final String projectName = activeProject.getShortLabel().replaceAll(".jpr", "");
                taskflowModel.setProjectName(projectName);
                final String packageName = "de." + projectName + (packageSuffix.isEmpty() ? "" : "." + packageSuffix);
                taskflowModel.setPackageName(packageName);
                taskflowModel.setJavaSourceDirPath(activeProject.getBaseDirectory() + "/src/" +
                                                   packageName.replace('.', '/') + "/");

                // jazn-data, resourcBundle und absoluter bundleName ermitteln
                taskflowModel.setJaznFilePath(jaznFilePathAsString);
                taskflowModel.setResourceBundleFilePath(resourceBundleFilePathAsString);
                taskflowModel.setUiBundleName(resourceBundleName);
                taskflowModel.setUiBundleVarName(projectName.replaceAll("\\.", "_"));
             

                // taskflow and jsf path
                final String publicHtmlAbsoluteDirPath = activeProject.getBaseDirectory() + "/public_html";
                final String webinfAndProjectDirPath = "/WEB-INF/" + projectName.replaceAll(".ui", "") + "/";
                taskflowModel.setJsfAbsoluteDirPath(publicHtmlAbsoluteDirPath + webinfAndProjectDirPath +
                                                    jsfPathSuffix);
                taskflowModel.setJsfDirPath(webinfAndProjectDirPath + jsfPathSuffix);
                taskflowModel.setTaskflowAbsoluteDirPath(publicHtmlAbsoluteDirPath + webinfAndProjectDirPath +
                                                         taskflowPathSuffix);
                taskflowModel.setTaskflowDirPath(webinfAndProjectDirPath + taskflowPathSuffix);

                // user input
                taskflowModel.setTaskflowName(StringHelper.uncapFirst(taskflowNameField.getText()));

                //generate Train
                System.out.println(taskflowModel);
                try {
                    TaskflowGenerator taskflowGenerator = new TaskflowGenerator();
                    taskflowGenerator.generate(taskflowModel);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } else {
            final JOptionPane errorPane =
                new JOptionPane("Bitte vorher ein ui-Projekt öffnen/expandieren und sicherstellen, dass ein uiBundle angelegt ist!");
            errorPane.createDialog(null, "KreDaTaskflowWizard").setVisible(true);
        }


        return true;

    }

    public String getResourceBundleName(String absoluteBundlePath) {
        final String dottedAbsoluteBundlePath = absoluteBundlePath.replaceAll("\\\\", ".");
        final String[] splittedPath = dottedAbsoluteBundlePath.split(".src.");
        String bundleName = splittedPath[1];
        return bundleName.replaceAll(".properties", "");
    }

    private JPanel baueLayout(JTextField taskflowNameField, JTextField packageSuffixField,
                              JTextField jsfPathSuffixField, JTextField taskflowPathSuffixField) {
        // Layout
        JPanel newPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Komponenten zum Layout hinzufuegen
        constraints.gridx = 0;
        constraints.gridy = 0;
        newPanel.add(new JLabel("Name des Taskflows:"), constraints);
        constraints.gridx = 1;
        newPanel.add(taskflowNameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        newPanel.add(new JLabel("Optional - Package-Suffix:"), constraints);
        constraints.gridx = 1;
        newPanel.add(packageSuffixField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        newPanel.add(new JLabel("Optional - JSF-Pfad-Suffix:"), constraints);
        constraints.gridx = 1;
        newPanel.add(jsfPathSuffixField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        newPanel.add(new JLabel("Optional - Taskflow-Pfad-Suffix:"), constraints);
        constraints.gridx = 1;
        newPanel.add(taskflowPathSuffixField, constraints);


        return newPanel;
    }

}

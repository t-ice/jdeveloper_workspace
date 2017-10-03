/* $Header: WizardDetailsPage.java 16-aug-2007.21:03:59 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat with JCS
    bduff       02/27/07 - Added copyright banner
 */
package oracle.jdeveloper.extsamples.classgenerator;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JTextField;

import oracle.ide.panels.DefaultTraversablePanel;
import oracle.ide.panels.TraversableContext;


public class WizardDetailsPage extends DefaultTraversablePanel {
    private JLabel _lbPackage = new JLabel("Package:");
    private JTextField _tfPackage = new JTextField();
    private JLabel _lbName = new JLabel("Addin Name:");
    private JTextField _tfName = new JTextField();
    private JLabel _lbAuthor = new JLabel("Author:");
    private JTextField _tfAuthor = new JTextField();

    public WizardDetailsPage() {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.insets = new Insets(0, 0, 5, 5);
        add(_lbPackage, gbc);
        gbc.gridy++;
        add(_lbName, gbc);
        gbc.gridy++;
        add(_lbAuthor, gbc);

        gbc.gridy = 0;
        gbc.gridx++;
        gbc.weightx = 1.0f;
        gbc.insets = new Insets(0, 0, 5, 0);

        add(_tfPackage, gbc);
        gbc.gridy++;
        add(_tfName, gbc);
        gbc.gridy++;
        add(_tfAuthor, gbc);
    }

    public void onEntry(TraversableContext tc) {
        ClassGeneratorWizardModel model =
            (ClassGeneratorWizardModel)tc.get(ClassGeneratorWizard.MODEL_KEY);

        _tfPackage.setText(model.getPackageName());
        _tfName.setText(model.getClassName());
        _tfAuthor.setText(model.getAuthorName());
    }

    public void onExit(TraversableContext tc) {
        ClassGeneratorWizardModel model =
            (ClassGeneratorWizardModel)tc.get(ClassGeneratorWizard.MODEL_KEY);

        model.setPackageName(_tfPackage.getText().trim());
        model.setClassName(_tfName.getText().trim());
        model.setAuthorName(_tfAuthor.getText().trim());
    }
}

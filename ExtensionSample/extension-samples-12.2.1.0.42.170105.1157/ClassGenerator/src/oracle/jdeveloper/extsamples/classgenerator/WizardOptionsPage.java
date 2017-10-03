/* $Header: WizardOptionsPage.java 16-aug-2007.21:04:03 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat with JCS
    bduff       02/27/07 - Added copyright banner
 */
package oracle.jdeveloper.extsamples.classgenerator;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JCheckBox;

import oracle.ide.panels.DefaultTraversablePanel;
import oracle.ide.panels.TraversableContext;


public class WizardOptionsPage extends DefaultTraversablePanel {
    private JCheckBox _cbInstallShutdownHook =
        new JCheckBox("Install shutdown hook");

    public WizardOptionsPage() {
        setLayout(new GridBagLayout());
        add(_cbInstallShutdownHook, new GridBagConstraints());
    }


    public void onEntry(TraversableContext tc) {
        ClassGeneratorWizardModel model =
            (ClassGeneratorWizardModel)tc.get(ClassGeneratorWizard.MODEL_KEY);

        _cbInstallShutdownHook.setSelected(model.isInstallShutdownHook());

    }

    public void onExit(TraversableContext tc) {
        ClassGeneratorWizardModel model =
            (ClassGeneratorWizardModel)tc.get(ClassGeneratorWizard.MODEL_KEY);

        model.setInstallShutdownHook(_cbInstallShutdownHook.isSelected());
    }
}

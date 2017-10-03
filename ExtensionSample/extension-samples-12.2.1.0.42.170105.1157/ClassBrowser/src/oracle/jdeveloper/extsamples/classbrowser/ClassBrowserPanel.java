/* $Header: ClassBrowserPanel.java 16-aug-2007.20:27:02 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Code cleanup, reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.jdeveloper.extsamples.classbrowser;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import oracle.ide.Context;
import oracle.ide.Ide;
import oracle.ide.controls.JWrappedLabel;
import oracle.ide.model.Project;
import oracle.ide.util.ResourceUtils;

import oracle.jdeveloper.dialogs.ClassPackageBrowserV2;
import oracle.jdeveloper.java.JavaManager;

public final class ClassBrowserPanel extends JPanel {
    private final GridBagLayout layout = new GridBagLayout();
    private final JLabel promptLabel = new JLabel();
    private final ClassPackageBrowserV2 classList =
        new ClassPackageBrowserV2();
    private final JWrappedLabel bottomLabel = new JWrappedLabel();

    public ClassBrowserPanel() {
        jbInit();
    }

    void jbInit() {
        ResourceUtils.resLabel(promptLabel, classList,
                               "Available Classes in your Project");
        bottomLabel.setText("Please choose a Class...");
        this.setLayout(layout);
        this.add(promptLabel,
                 new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
                                        GridBagConstraints.NONE,
                                        new Insets(4, 4, 2, 2), 0, 0));
        this.add(classList,
                 new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0, GridBagConstraints.NORTHWEST,
                                        GridBagConstraints.BOTH,
                                        new Insets(0, 0, 0, 0), 0, 0));
        this.add(bottomLabel,
                 new GridBagConstraints(0, 3, 2, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST,
                                        GridBagConstraints.BOTH,
                                        new Insets(0, 4, 3, 4), 0, 0));

        setProject(Ide.getActiveProject());
        Project activeProject = Ide.getActiveProject();
        activeProject.delete();
        classList.setMode(ClassPackageBrowserV2.CLASS_ONLY);
        classList.setMultiSelect(false);
    }

    void setContext(Context context) {
        setProject(context.getProject());
    }

    private void setProject(Project p) {
        classList.setJavaManager(JavaManager.getJavaManager(p));
    }

    public String[] getSelectedClasses() {
        return classList.getSelectedItems();
    }
}

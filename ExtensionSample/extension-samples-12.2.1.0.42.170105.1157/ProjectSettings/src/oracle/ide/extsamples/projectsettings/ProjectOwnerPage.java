/* $Header: ProjectOwnerPage.java 16-aug-2007.22:18:50 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */

package oracle.ide.extsamples.projectsettings;

import javax.swing.JTextField;

import oracle.ide.model.panels.ProjectSettingsTraversablePanel;
import oracle.ide.panels.TraversableContext;

import oracle.javatools.data.PropertyStorage;
import oracle.javatools.ui.layout.FieldLayoutBuilder;


public final class ProjectOwnerPage extends ProjectSettingsTraversablePanel {
    /**
     * The keys of all properties in ProjectOwnerOptions this panel
     * modifies.
     */
    private final static String[] MY_KEYS =
    { ProjectOwnerOptions.NAME, ProjectOwnerOptions.EMAIL_ADDRESS };

    private final JTextField name = new JTextField();
    private final JTextField email = new JTextField();

    public ProjectOwnerPage() {
        FieldLayoutBuilder b = new FieldLayoutBuilder(this);

        b.add(b.field().label().withText("&Name:").component(name));
        b.add(b.field().label().withText("&Email:").component(email));
        b.addVerticalSpring();
    }

    public String getDataKey() {
        return ProjectOwnerOptions.KEY_OPTIONS;
    }

    public String[] getPropertyKeys() {
        return MY_KEYS;
    }

    public void onProjectPanelEntry(TraversableContext context) {
        load(getProjectOwnerOptions(context));
    }

    public void onExit(TraversableContext context) {
        commit(getProjectOwnerOptions(context));
    }

    private static ProjectOwnerOptions getProjectOwnerOptions(TraversableContext context) {
        final PropertyStorage propertyData = getPropertyData(context);
        return ProjectOwnerOptions.getInstance(propertyData);
    }

    private void load(ProjectOwnerOptions options) {
        // Load options from the options object into the UI.
        email.setText(options.getEmailAddress());
        name.setText(options.getName());
    }

    private void commit(ProjectOwnerOptions options) {
        options.setEmailAddress(email.getText().trim());
        options.setName(name.getText().trim());
    }
}

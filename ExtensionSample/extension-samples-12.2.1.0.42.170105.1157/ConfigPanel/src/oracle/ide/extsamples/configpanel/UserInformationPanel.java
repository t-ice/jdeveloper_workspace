/* $Header: UserInformationPanel.java 16-aug-2007.22:25:48 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS. Minor code cleanup.
    bduff       02/27/07 - Big rewrite for 11.1.1 ESDK
 */
package oracle.ide.extsamples.configpanel;

import javax.swing.JTextField;

import oracle.ide.panels.DefaultTraversablePanel;
import oracle.ide.panels.TraversableContext;
import oracle.ide.panels.TraversalException;

import oracle.javatools.ui.layout.FieldLayoutBuilder;


/**
 * The UI panel that provides support in the Preferences dialog for editing the
 * preferences stored in the {@link UserInformation} model.
 */
// You should keep the panel class package-private and final unless there
// is a good reason to open it up. In general, preferences panels are not
// supposed to be published APIs, so we enforce that. Even though the panel
// is constructed by the IDE framework using reflection, the IDE framework
// does not require that it is public (only that it has a no-argument
// constructor).
final class UserInformationPanel extends DefaultTraversablePanel {
    private final JTextField name = new JTextField();
    private final JTextField organization = new JTextField();

    public UserInformationPanel() {
        layoutControls();
    }

    private void layoutControls() {
        FieldLayoutBuilder b = new FieldLayoutBuilder(this);
        b.add(b.field().label().withText("&Name:").component(name));
        b.add(b.field().label().withText("&Organization:").component(organization));
        b.addVerticalSpring();
    }

    @Override
    public void onEntry(TraversableContext traversableContext) {
        // On entering the panel, we need to populate all fields with properties
        // from the model object.
        UserInformation info = getUserInformation(traversableContext);

        name.setText(info.getName());
        organization.setText(info.getOrganization());

        super.onEntry(traversableContext);
    }

    @Override
    public void onExit(TraversableContext traversableContext) throws TraversalException {
        // On leaving the panel, we need to write data back into the model object.
        // You can throw a TraversalException if you want to display a validation
        // message and prevent the user from navigating away from the page.
        UserInformation info = getUserInformation(traversableContext);

        info.setName(name.getText().trim());
        info.setOrganization(organization.getText().trim());

        super.onExit(traversableContext);
    }

    private static UserInformation getUserInformation(TraversableContext tc) {
        // This gets a defensive copy of the preferences being used in the Tools->
        // Preferences dialog. The IDE framework takes care of making this copy,
        // and applying it back to the real preferences store, or abandoning it
        // if the user clicks Cancel.
        return UserInformation.getInstance(tc.getPropertyStorage());
    }
}

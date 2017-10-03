/* $Header: SimpleWizard.java 16-aug-2007.22:06:27 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.extsamples.firstsample;

import javax.swing.Icon;

import oracle.ide.Context;
import oracle.ide.Ide;
import oracle.ide.wizard.Wizard;

import oracle.javatools.dialogs.MessageDialog;
import oracle.javatools.icons.OracleIcons;


/**
 * An implementation of Wizard is an item that appears in the File->New
 * dialog. When the user selects the item and clicks OK, the invoke()
 * method is called.
 */
public final class SimpleWizard extends Wizard {

    public Icon getIcon() {
        return OracleIcons.getIcon(OracleIcons.LABEL);
    }

    public String getShortLabel() {
        return "Sample One";
    }

    public boolean invoke(Context context) {
        // Invocation: Display message dialog from the Gallery
        showMessageBox(context);
        return true;
    }

    public boolean isAvailable(Context context) {
        return true;
    }

    private void showMessageBox(Context context) {
        String caption = "Sample One was Invoked!";
        Ide.getStatusBar().setText(caption);
        String msg = "Now Edit this code to do something more interesting!";
        MessageDialog.information(Ide.getMainWindow(), msg, caption, null);
    }
}

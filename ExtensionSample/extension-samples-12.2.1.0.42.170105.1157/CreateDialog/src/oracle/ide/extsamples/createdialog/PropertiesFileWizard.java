/* $Header: PropertiesFileWizard.java 16-aug-2007.21:58:35 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS. Minor cleanup.
    bduff       04/09/07 - Initial revision
 */

package oracle.ide.extsamples.createdialog;

import javax.swing.Icon;

import oracle.ide.Context;
import oracle.ide.wizard.Wizard;

import oracle.javatools.icons.OracleIcons;

/**
 * Implementation of the "(ESDK Sample) Properties File" gallery item.
 */
public final class PropertiesFileWizard extends Wizard {
    public boolean isAvailable(Context context) {
        return context.getProject() != null;
    }

    public boolean invoke(Context context) {
        return new CreatePropertiesFilePanel().runDialog(context);
    }

    public String getShortLabel() {
        return "(ESDK Sample) Properties File";
    }

    @Override
    public String getLongLabel() {
        return "Opens the Create Properties File dialog which allows you to create a new properties file.";
    }

    @Override
    public Icon getIcon() {
        return OracleIcons.getIcon(OracleIcons.FILE);
    }
}

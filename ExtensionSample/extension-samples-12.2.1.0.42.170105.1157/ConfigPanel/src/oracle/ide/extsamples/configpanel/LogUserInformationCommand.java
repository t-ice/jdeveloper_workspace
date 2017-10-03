/* $Header: LogUserInformationCommand.java 16-aug-2007.22:25:38 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS. Minor code cleanup.
    bduff       02/27/07 - Big rewrite for 11.1.1 ESDK
 */

package oracle.ide.extsamples.configpanel;

import oracle.ide.config.Preferences;
import oracle.ide.controller.Command;


/**
 * A command that logs the current UserInformation preferences to the messages
 * log.
 */
public final class LogUserInformationCommand extends Command {
    public LogUserInformationCommand() {
        super(UserInformationAddin.CMD_ID);
    }

    public int doit() {
        // This is how to get the current preferences object.
        // DO NOT USE THIS TECHNIQUE IN PREFERENCES PAGES (e.g. in a subclass
        // of DefaultTraversablePage). It will get the "live" preferences object,
        // so any changes you make will be immediately applied, and the Cancel
        // button in the Preferences dialog will not work.

        Preferences p = Preferences.getPreferences();
        UserInformation userInformation = UserInformation.getInstance(p);

        UserInformationAddin.log("Name is " + userInformation.getName());
        UserInformationAddin.log("Organization is " +
                                 userInformation.getOrganization());

        return OK;
    }
}

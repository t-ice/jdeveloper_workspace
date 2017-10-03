/* $Header: DumpMenuController.java 16-aug-2007.22:18:40 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       03/28/07 - Pulled up out of ProjectSettingsAddin.
 */

package oracle.ide.extsamples.projectsettings;

import oracle.ide.Context;
import oracle.ide.controller.Controller;
import oracle.ide.controller.IdeAction;
import oracle.ide.log.LogManager;
import oracle.ide.model.Project;


final class DumpMenuController implements Controller {
    private final void logMessage(String msg) {
        LogManager.getLogManager().showLog();
        LogManager.getLogManager().getMsgPage().log(msg + "\n");
    }

    public boolean handleEvent(IdeAction action, Context context) {
        Project prj = context.getProject();
        if (prj == null)
            return false;

        ProjectOwnerOptions ownerOptions =
            ProjectOwnerOptions.getInstance(prj);
        String name = ownerOptions.getName();
        String email = ownerOptions.getEmailAddress();

        logMessage("Name is: " + String.valueOf(name) + " Email is " +
                   String.valueOf(email));

        return true;
    }

    public boolean update(IdeAction action, Context context) {
        action.setEnabled(true);
        return true;
    }
}

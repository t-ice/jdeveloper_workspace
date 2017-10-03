/* $Header: ClassBrowserCommand.java 16-aug-2007.20:27:02 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Initial revision
 */
package oracle.jdeveloper.extsamples.classbrowser;

import oracle.bali.ewt.dialog.JEWTDialog;

import oracle.ide.Ide;
import oracle.ide.controller.Command;
import oracle.ide.dialogs.OnePageWizardDialogFactory;
import oracle.ide.dialogs.WizardLauncher;
import oracle.ide.extension.RegisteredByExtension;
import oracle.ide.log.LogManager;

/**
 * Command handler for esdksample.showClassBrowser.
 */
@RegisteredByExtension("oracle.jdeveloper.extsamples.classbrowser")
public final class ClassBrowserCommand extends Command {
    public ClassBrowserCommand() {
        super(actionId());
    }

    public int doit() {
        String msg = null;

        ClassBrowserPanel cp = new ClassBrowserPanel();
        cp.setContext(context);
        JEWTDialog dlg =
            OnePageWizardDialogFactory.createJEWTDialog(cp, null, "Click the Button");
        dlg.setDefaultButton(JEWTDialog.BUTTON_OK);
        dlg.setOKButtonEnabled(true);

        boolean ok = WizardLauncher.runDialog(dlg);
        if (ok) {
            if (cp.getSelectedClasses() != null &&
                cp.getSelectedClasses().length > 0)
                msg = "You've selected:" + cp.getSelectedClasses()[0];
            else
                msg = "Select a class!";
        } else
            msg = "Operation has been canceled";


        if (msg != null) {
            logMessage(msg);
            Ide.getStatusBar().setText(msg);
        }
        return OK;
    }


    private static final void logMessage(String msg) {
        LogManager mgr = LogManager.getLogManager();
        mgr.showLog();
        mgr.getMsgPage().log(msg + "\n");
    }

    /**
     * Returns the id of the action this command is associated with.
     *
     * @return the id of the action this command is associated with.
     * @throws IllegalStateException if the action this command is associated
     *    with is not registered.
     */
    public static int actionId() {
        final Integer cmdId = Ide.findCmdID("esdksample.showClassBrowser");
        if (cmdId == null)
            throw new IllegalStateException("Action esdksample.showClassBrowser not found.");
        return cmdId;
    }
}

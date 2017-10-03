/* $Header: SimpleController.java 16-aug-2007.22:06:23 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.extsamples.firstsample;

import oracle.ide.Context;
import oracle.ide.Ide;
import oracle.ide.controller.Controller;
import oracle.ide.controller.IdeAction;
import oracle.ide.wizard.WizardManager;

/**
 * The controller implementation is responsible for enabling and disabling
 * actions and performing any work required when they are invoked.
 */
public final class SimpleController implements Controller {
    /**
     * Look up the numeric id of the action we defined in our extension
     * manifest. The string constant matches the value of the id attribute in
     * the action element of the extension manifest.<p>
     *
     * The numeric id can be used to retrieve an instance of IdeAction.
     */
    public static final int SAMPLE_CMD_ID =
        Ide.findCmdID("oracle.ide.extsamples.first.invokeAction");

    public boolean handleEvent(IdeAction action, Context context) {
        // This demonstrates how to programmatically invoke a gallery wizard.
        WizardManager.getInstance().getWizard(SimpleWizard.class).invoke(context);
        return true;
    }

    public boolean update(IdeAction action, Context context) {
        action.setEnabled(true);
        return true;
    }
}

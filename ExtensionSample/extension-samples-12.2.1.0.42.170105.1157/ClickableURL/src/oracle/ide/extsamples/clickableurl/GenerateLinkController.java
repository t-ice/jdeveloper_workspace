/* $Header: ClassSpyCommand.java 16-aug-2007.20:27:02 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Initial revision
 */
package oracle.ide.extsamples.clickableurl;

import oracle.ide.Context;
import oracle.ide.controller.Controller;
import oracle.ide.controller.IdeAction;
import oracle.ide.extension.RegisteredByExtension;


/**
 * Controller for action esdksample.GenerateLink.
 */
@RegisteredByExtension("oracle.ide.extsamples.clickableurl")
public final class GenerateLinkController implements Controller {
    public boolean update(IdeAction action, Context context) {
        // TODO Determine when esdksample.GenerateLink is enabled, and call action.setEnabled().
        return true;
    }

    public boolean handleEvent(IdeAction action, Context context) {
        // Command is handled by oracle.ide.extsamples.clickableurl.GenerateLinkCommand
        return false;
    }
}

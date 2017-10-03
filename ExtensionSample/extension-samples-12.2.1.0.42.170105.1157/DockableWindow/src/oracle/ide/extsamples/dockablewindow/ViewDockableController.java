/* $Header: ViewDockableController.java 16-aug-2007.21:53:07 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/20/07 - Initial revision
 */
package oracle.ide.extsamples.dockablewindow;

import oracle.ide.Context;
import oracle.ide.controller.Controller;
import oracle.ide.controller.IdeAction;
import oracle.ide.extension.RegisteredByExtension;


/**
 * Controller for action esdksample.exampleDockable.
 */
@RegisteredByExtension("oracle.ide.extsamples.dockablewindow")
public final class ViewDockableController implements Controller {
    public boolean update(IdeAction action, Context context) {
        return true;
    }

    public boolean handleEvent(IdeAction action, Context context) {
        // Command is handled by oracle.ide.extsamples.dockablewindow.ViewDockableCommand
        return false;
    }
}

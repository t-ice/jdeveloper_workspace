/* $Header: ClassBrowserController.java 16-aug-2007.20:27:02 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Initial revision
 */
package oracle.jdeveloper.extsamples.classbrowser;

import oracle.ide.Context;
import oracle.ide.controller.Controller;
import oracle.ide.controller.IdeAction;
import oracle.ide.extension.RegisteredByExtension;


/**
 * Controller for action esdksample.showClassBrowser.
 */
@RegisteredByExtension("oracle.jdeveloper.extsamples.classbrowser")
public final class ClassBrowserController implements Controller {
    public boolean update(IdeAction action, Context context) {
        return true;
    }

    public boolean handleEvent(IdeAction action, Context context) {
        // Command is handled by oracle.jdeveloper.extsamples.classbrowser.ClassBrowserCommand
        return false;
    }
}

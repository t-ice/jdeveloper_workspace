/* $Header: CountCallsController.java 27-feb-2007.22:37:33 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/20/07 - Initial Revision
 */
package oracle.jdeveloper.extsamples.methodcallcounter;

import oracle.ide.Context;
import oracle.ide.controller.Controller;
import oracle.ide.controller.IdeAction;
import oracle.ide.extension.RegisteredByExtension;

import oracle.jdeveloper.model.JavaSourceNode;


/**
 * Controller for action esdksample.countMethodCalls.
 */
@RegisteredByExtension("oracle.ide.extsamples.methodcallcounter")
public final class CountCallsController implements Controller {
    public boolean update(IdeAction action, Context context) {
        action.setEnabled( context.getElement() instanceof JavaSourceNode );
        return true;
    }

    public boolean handleEvent(IdeAction action, Context context) {
        // Command is handled by oracle.jdeveloper.extsamples.methodcallcounter.CountCallsCommand
        return false;
    }
}

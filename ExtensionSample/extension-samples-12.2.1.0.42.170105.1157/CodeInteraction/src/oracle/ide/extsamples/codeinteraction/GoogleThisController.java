/* $Header: GoogleThisController.java 27-feb-2007.22:37:33 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/20/07 - Initial revision
 */
package oracle.ide.extsamples.codeinteraction;

import oracle.ide.Context;
import oracle.ide.ceditor.CodeEditor;
import oracle.ide.controller.Controller;
import oracle.ide.controller.IdeAction;
import oracle.ide.extension.RegisteredByExtension;
import oracle.ide.view.View;


/**
 * Controller for action esdksample.GoogleThis.
 */
@RegisteredByExtension("oracle.ide.extsamples.codeinteraction")
public final class GoogleThisController implements Controller {
    public boolean update(IdeAction action, Context context) {
        action.setEnabled( hasSelectedText( context.getView() ) );
        return true;
    }

    public boolean handleEvent(IdeAction action, Context context) {
        return false;
    }
    
    private boolean hasSelectedText( View view ) {
        if ( !(view instanceof CodeEditor )) return false;
        String selectedText = ((CodeEditor)view).getSelectedText();
        
        return selectedText != null && selectedText.length() != 0;
    }
}

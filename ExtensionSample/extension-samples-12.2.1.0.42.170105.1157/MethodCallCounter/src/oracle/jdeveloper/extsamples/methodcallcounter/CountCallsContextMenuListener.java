/* $Header: CountCallsContextMenuListener.java 27-feb-2007.22:37:33 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/20/07 - Initial Revision
 */
package oracle.jdeveloper.extsamples.methodcallcounter;

import oracle.ide.Context;
import oracle.ide.controller.ContextMenu;
import oracle.ide.controller.ContextMenuListener;

import oracle.ide.controller.IdeAction;

import oracle.jdeveloper.model.JavaSourceNode;

final class CountCallsContextMenuListener implements ContextMenuListener {

    public void menuWillShow(ContextMenu contextMenu) {
        Context context = contextMenu.getContext();
        if ( context.getElement() instanceof JavaSourceNode ||
             context.getNode() instanceof JavaSourceNode ) {
            contextMenu.add( contextMenu.createMenuItem(
                IdeAction.find( CountCallsCommand.actionId() )                     
            ));
        }
    }

    public void menuWillHide(ContextMenu contextMenu) {
    }

    public boolean handleDefaultAction(Context context) {
        return false;
    }
}

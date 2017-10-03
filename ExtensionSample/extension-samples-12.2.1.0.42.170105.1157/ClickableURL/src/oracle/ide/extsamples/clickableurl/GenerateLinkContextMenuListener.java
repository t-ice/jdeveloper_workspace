/* $Header: GenerateLinkContextMenuListener.java 16-aug-2007.20:27:02 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Initial revision
 */
package oracle.ide.extsamples.clickableurl;

import oracle.ide.Context;
import oracle.ide.controller.ContextMenu;
import oracle.ide.controller.ContextMenuListener;
import oracle.ide.controller.IdeAction;


final class GenerateLinkContextMenuListener implements ContextMenuListener {

    public void menuWillShow(ContextMenu contextMenu) {
        contextMenu.add( contextMenu.createMenuItem(
            IdeAction.find( GenerateLinkCommand.actionId() )
        ));
    }

    public void menuWillHide(ContextMenu contextMenu) {
    }

    public boolean handleDefaultAction(Context context) {
        return false;
    }
}

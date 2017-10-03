/* $Header: jdev/src/esdk-samples/sample/CodeInteraction/src/oracle/ide/extsamples/codeinteraction/GoogleThisContextMenuListener.java /main/2 2010/11/22 01:11:02 alfarouk Exp $ */

/* Copyright (c) 2007, 2010, Oracle and/or its affiliates. 
All rights reserved. */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/20/07 - Initial revision
 */
package oracle.ide.extsamples.codeinteraction;

import oracle.ide.Context;
import oracle.ide.controller.ContextMenu;
import oracle.ide.controller.ContextMenuListener;
import oracle.ide.controller.IdeAction;

/*
 * NOTE THIS CLASS NOT USED ANYMORE. 
 * Context menus are now added declaratively. 
 * 
 * Please see the extension.xml file. 
 */
final class GoogleThisContextMenuListener implements ContextMenuListener {
    public void menuWillShow(ContextMenu contextMenu) {
        contextMenu.add( contextMenu.createMenuItem( 
            IdeAction.find( GoogleThisCommand.actionId() )
        ));
    }

    public void menuWillHide(ContextMenu contextMenu) {
    }

    public boolean handleDefaultAction(Context context) {
        return false;
    }
}

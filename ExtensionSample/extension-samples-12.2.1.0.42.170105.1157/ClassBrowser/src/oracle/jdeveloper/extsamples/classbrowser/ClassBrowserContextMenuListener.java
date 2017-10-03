/* $Header: jdev/src/esdk-samples/sample/ClassBrowser/src/oracle/jdeveloper/extsamples/classbrowser/ClassBrowserContextMenuListener.java /main/3 2010/10/13 14:47:44 alfarouk Exp $ */

/* Copyright (c) 2007, 2010, Oracle and/or its affiliates. 
All rights reserved. */

/*
   MODIFIED    (MM/DD/YY)
    alfarouk    10/13/10 - modified for on-demand loading
    bduff       11/21/07 - Bug 6624651 - fix compilation warning.
    bduff       08/16/07 - Initial revision
 */
package oracle.jdeveloper.extsamples.classbrowser;

import oracle.ide.Context;
import oracle.ide.controller.ContextMenu;
import oracle.ide.controller.ContextMenuListener;
import oracle.ide.controller.IdeAction;

public class ClassBrowserContextMenuListener implements ContextMenuListener {

    public void menuWillShow(ContextMenu contextMenu) {
        /*
         * Context menus are now added declaratively. Please see the extension.xml file
         * for details. 
         * Old code left commented for reference.
         */
        //contextMenu.add(contextMenu.createMenuItem(IdeAction.find(ClassBrowserCommand.actionId()))); 
    }

    public void menuWillHide(ContextMenu contextMenu) {
    }

    public boolean handleDefaultAction(Context context) {
        return false;
    }
}

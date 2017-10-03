/* $Header: jdev/src/esdk-samples/sample/FirstSample/src/oracle/ide/extsamples/firstsample/SimpleContextMenuListener.java /main/5 2010/09/19 17:14:42 alfarouk Exp $ */

/* Copyright (c) 2007, 2010, Oracle and/or its affiliates. 
All rights reserved. */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.extsamples.firstsample;

import oracle.ide.Context;
import oracle.ide.controller.ContextMenu;
import oracle.ide.controller.ContextMenuListener;
import oracle.ide.controller.IdeAction;

/**
 * 
 * NOTE: This listener is no longer used. The context menu is now added
 * declaratively, via the 'extension.xml' file. The 'menuWillShow' method 
 * used to contain logic that the extension would use to decided whether
 * a menu should be added or not depending on the context. These are now
 * replaced by 'rule' which are declared in the extension.xml and evaluated
 * at run-time to decide whether or not this menu should be added. 
 *
 * Please see the extension.xml file to see how a context menu is now added. 
 *
 */
public final class SimpleContextMenuListener implements ContextMenuListener {
    public void menuWillShow(ContextMenu contextMenu) {
        // First, retrieve our action using the ID we specified in the extension
        // manifest.
        IdeAction action = IdeAction.find(SimpleController.SAMPLE_CMD_ID);

        // Then add it to the context menu.
        contextMenu.add(contextMenu.createMenuItem(action));
    }

    public void menuWillHide(ContextMenu contextMenu) {
        // Most context menu listeners will do nothing in this method. In
        // particular, you should *not* remove menu items in this method.
    }

    public boolean handleDefaultAction(Context context) {
        // You can implement this method if you want to handle the default
        // action (usually double click) for some context.
        return false;
    }
}

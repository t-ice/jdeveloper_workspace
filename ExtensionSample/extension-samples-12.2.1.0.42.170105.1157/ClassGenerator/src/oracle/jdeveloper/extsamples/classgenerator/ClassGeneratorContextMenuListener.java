/* $Header: ClassGeneratorContextMenuListener.java 16-aug-2007.21:03:56 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat with JCS
    bduff       02/27/07 - Added copyright banner
 */
package oracle.jdeveloper.extsamples.classgenerator;

import oracle.ide.Context;
import oracle.ide.controller.ContextMenu;
import oracle.ide.controller.ContextMenuListener;
import oracle.ide.controller.IdeAction;

/**
 * Context menu listener for the ClassGenerator sample.
 */
final class ClassGeneratorContextMenuListener implements ContextMenuListener {

    /*
    * Called just before the context menu is shown.
    * Implementations should add their items to the context menu here.
    */

    public void menuWillShow(ContextMenu popup) {
        Context context = (popup == null) ? null : popup.getContext();
        if (context == null)
            return;
        // Insert Simple MenuItem in the View Menu
        if (ClassGeneratorController.isActionEnabled(context)) {
            popup.add(popup.createMenuItem(IdeAction.find(ClassGeneratorCommand.actionId())));
        }
    }

    /*
    * This method is called just before a showing context menu is dismissed.
    * Most implementations should not do anything in this method.
    * In particular, it is not necessary to clean out menu items or
    * submenus that were added during menuWillShow(oracle.ide.controller.ContextMenu),
    * since the IDE takes care of that automatically.
    */

    public void menuWillHide(ContextMenu popup) {
        // Do any necessary clean up!
    }

    /*
    * Called when the user double clicks on an item that has a context menu.
    * If the listener handles the action, then it must return true;
    * otherwise it must return false.
    */

    public boolean handleDefaultAction(Context context) {
        return false;
    }
}

/* $Header: FavoritesContextMenuListener.java 20-aug-2007.14:19:15 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/20/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.extsamples.customnavigator;

import oracle.ide.Context;
import oracle.ide.controller.ContextMenu;
import oracle.ide.controller.ContextMenuListener;
import oracle.ide.controller.IdeAction;

// Context menu listener that adds the "Add Favorite" menu item to the navigator
// context menu.
final class FavoritesContextMenuListener implements ContextMenuListener {
    public void menuWillShow(ContextMenu contextMenu) {
        IdeAction action =
            IdeAction.find(FavoritesController.ADD_FAVORITE_CMD_ID);
        // This is a neat way of avoiding repeating the logic you use in your
        // controller to disable or enable the action if it's exactly the same
        // as the logic to decide whether to add the item (often the case with
        // context menu items).
        action.updateAction(contextMenu.getContext());
        if (action.isEnabled()) {
            contextMenu.add(contextMenu.createMenuItem(action));
        }
    }

    public void menuWillHide(ContextMenu contextMenu) {
    }

    public boolean handleDefaultAction(Context context) {
        return false;
    }
}

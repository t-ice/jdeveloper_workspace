/* $Header: FavoritesNavigatorWindow.java 20-aug-2007.14:19:30 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/20/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.extsamples.customnavigator;

import java.util.Arrays;

import oracle.ide.model.Element;

import oracle.ide.Context;

import oracle.ide.IdeConstants;
import oracle.ide.controller.Controller;

import oracle.ide.controller.IdeAction;

import oracle.ide.model.UpdateMessage;

import oracle.ideri.navigator.DefaultNavigatorWindow;

// We override the DefaultNavigatorWindow just so that we can install a custom
// controller to handle the standard IDE Delete action. Arguably, the IDE should
// make it easier for us to override this behavior without having to create
// a subclass of DefaultNavigatorWindow.
final class FavoritesNavigatorWindow extends DefaultNavigatorWindow {
    private final Controller _controller = new FavoritesController();

    FavoritesNavigatorWindow(Context context, String viewId) {
        super(context, viewId);
    }

    public Controller getController() {
        return _controller;
    }

    private final class FavoritesController implements Controller {
        public boolean handleEvent(IdeAction ideAction, Context context) {
            int cmdId = ideAction.getCommandId();

            // This is how to override standard IDE actions. Because the controller
            // is associated with our view, we are only overriding the behavior
            // when ours is the active view. Our controller has no effect on the
            // action when invoked in other views.
            if (cmdId == IdeConstants.DELETE_CMD_ID) {
                Element[] selection = context.getSelection();
                for (Element e : selection) {
                    FavoritesFolder.getFavoritesFolder().remove(e);
                }
                // Now fire a single update event.
                UpdateMessage.fireChildrenRemoved(FavoritesFolder.getFavoritesFolder(),
                                                  Arrays.asList(selection));
                return true;
            }
            return false;
        }

        public boolean update(IdeAction ideAction, Context context) {
            int cmdId = ideAction.getCommandId();

            if (cmdId == IdeConstants.DELETE_CMD_ID) {
                // Can delete anything except the favorites folder.
                Element[] selection = context.getSelection();
                if (selection != null && selection.length > 0) {
                    for (Element e : selection) {
                        if (e instanceof FavoritesFolder) {
                            ideAction.setEnabled(false);
                            return true;
                        }
                    }
                    // The selection does not contain the favorites folder.
                    ideAction.setEnabled(true);
                    return true;
                }
                ideAction.setEnabled(false);
                return true;
            }
            return false;
        }
    }
}

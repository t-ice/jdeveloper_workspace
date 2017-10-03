/* $Header: FavoritesNavigatorManager.java 20-aug-2007.14:19:27 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/20/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.extsamples.customnavigator;

import oracle.ide.Context;
import oracle.ide.Ide;
import oracle.ide.IdeConstants;
import oracle.ide.controller.IdeAction;
import oracle.ide.docking.Dockable;
import oracle.ide.docking.DockingParam;
import oracle.ide.layout.ViewId;
import oracle.ide.navigator.NavigatorWindow;

import oracle.ideri.navigator.DefaultNavigatorManager;
import oracle.ideri.navigator.DefaultNavigatorWindow;

/**
 * A demonstration navigator manager for the custom "Favories" navigator.
 */
public class FavoritesNavigatorManager extends DefaultNavigatorManager {
    /**
     * The action id of the show favorites navigator action. This matches the
     * id defined for this action in extension.xml.
     */
    private static final String SHOW_NAVIGATOR_ACTION_ID =
        "esdk.showFavoritesNavigator";
    private static final int SHOW_NAVIGATOR_CMDID =
        Ide.findOrCreateCmdID(SHOW_NAVIGATOR_ACTION_ID);

    /**
     * The view ID of dockable views created by this navigator manager. This is
     * returned from the getViewCategory() method.
     */
    private static final String NAVIGATOR_WINDOW_ID = "ESDKFavoritesNavigator";

    private final FavoritesFolder _favoritesFolder;

    FavoritesNavigatorManager(FavoritesFolder favoritesFolder) {
        _favoritesFolder = favoritesFolder;
    }

    // Get the action used to show this navigator. The superclass takes care
    // of attaching a controller to this action which actually makes the
    // navigator visible.

    protected IdeAction createShowNavigatorAction() {
        return IdeAction.find(SHOW_NAVIGATOR_CMDID);
    }

    // We override this so that we can pass in _favoritesFolder as the root
    // node of our navigator window.

    protected NavigatorWindow createNavigatorWindow() {
        return createNavigatorWindow(_favoritesFolder, true,
                                     Dockable.DEFAULT_VISIBILITY_VISIBLE);
    }

    // This is the method that actually creates a new instance of
    // DefaultNavigatorWindow. We override it here to create our custom
    // subclass (required in order to use a different controller to handle
    // the standard Delete action).

    protected DefaultNavigatorWindow createNavigatorWindow(Context context,
                                                           ViewId viewId) {
        return new FavoritesNavigatorWindow(context, viewId.getId());
    }


    // The view category for dockable views created by this navigator manager.
    // Even if there are multiple navigators, they all share the same view
    // category.

    protected String getViewCategory() {
        return NAVIGATOR_WINDOW_ID;
    }

    // The name displayed in the tab for this navigator.

    protected String getDefaultName() {
        return "Favorites";
    }

    // You *must* override this to tell the docking subsystem where to dock this
    // navigator by default. (it's arguably a bug in the ide that it throws an
    // exception if you don't).

    protected DockingParam createNavigatorDockingParam() {
        DockingParam param = super.createNavigatorDockingParam();
        param.setPosition(IdeConstants.WEST);
        return param;
    }

}

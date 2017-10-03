/* $Header: FavoritesNavigatorAddin.java 20-aug-2007.14:19:24 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/20/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.extsamples.customnavigator;

import oracle.ide.Addin;

// Addin that only exists to initialize our custom navigator manager, and
// create the favorites folder.
final class FavoritesNavigatorAddin implements Addin {
    public void initialize() {
        FavoritesFolder folder = FavoritesFolder.getFavoritesFolder();
        FavoritesNavigatorManager navigatorManager =
            new FavoritesNavigatorManager(folder);
        navigatorManager.initialize();
    }
}

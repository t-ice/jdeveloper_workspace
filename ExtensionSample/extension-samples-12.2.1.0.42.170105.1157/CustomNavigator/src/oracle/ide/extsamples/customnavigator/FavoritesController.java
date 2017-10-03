/* $Header: FavoritesController.java 20-aug-2007.14:19:18 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/20/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.extsamples.customnavigator;

import oracle.ide.Context;
import oracle.ide.Ide;
import oracle.ide.controller.Controller;
import oracle.ide.controller.IdeAction;
import oracle.ide.model.Element;
import oracle.ide.model.Locatable;
import oracle.ide.model.UpdateMessage;


// Controls the behavior of the Add Favorite action.
final class FavoritesController implements Controller {
    private static final String ADD_FAVORITE_ID = "esdk.AddFavorite";
    static final int ADD_FAVORITE_CMD_ID =
        Ide.findOrCreateCmdID(ADD_FAVORITE_ID);

    public boolean handleEvent(IdeAction action, Context context) {
        // Add the single selection to the favorites folder, firing an update
        // event to ensure that the navigator is notified of the change.
        final Element[] sel = context.getSelection();
        FavoritesFolder.getFavoritesFolder().add(sel[0], true);
        UpdateMessage.fireChildAdded( FavoritesFolder.getFavoritesFolder(), sel[0]);
        return true;
    }

    public boolean update(IdeAction action, Context context) {
        action.setEnabled( actionIsEnabled( context ) );
        return true;
    }
    
    private boolean actionIsEnabled( Context context ) {
        // Our conditions for enablement are:
        //   - Selection length is exactly 1.
        //   - Selection is locatable (has a URL)
        //   - Selection is not the favorites folder itself.
        //   - Selection is not already in the favorites folder.
        final Element[] sel = context.getSelection();
        if ( sel == null || sel.length != 1 ||
             !(sel[0] instanceof Locatable) ) return false;
        
        Locatable locatable = (Locatable) sel[0];
        if ( locatable instanceof FavoritesFolder ) return false;
        
        return !FavoritesFolder.getFavoritesFolder().containsChild( sel[0] );
    }

}

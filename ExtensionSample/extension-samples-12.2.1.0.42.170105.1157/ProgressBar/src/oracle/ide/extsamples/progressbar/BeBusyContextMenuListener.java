package oracle.ide.extsamples.progressbar;

import oracle.ide.Context;
import oracle.ide.controller.ContextMenu;
import oracle.ide.controller.ContextMenuListener;
import oracle.ide.controller.IdeAction;

public class BeBusyContextMenuListener implements ContextMenuListener {
    public BeBusyContextMenuListener() {
    }

    public void menuWillShow(ContextMenu contextMenu) {
    /*
     * Note: We no longer add the menu programmatically. 
     * It is added declaratively, please see the extension.xml
     * file for details. 
     * Leaving the old code commented out for reference. 
     */
    //    contextMenu.add( contextMenu.createMenuItem( 
    //        IdeAction.find( BeBusyCommand.actionId() )                     
    //    ));
    }

    public void menuWillHide(ContextMenu contextMenu) {
    }

    public boolean handleDefaultAction(Context context) {
        return false;
    }
}

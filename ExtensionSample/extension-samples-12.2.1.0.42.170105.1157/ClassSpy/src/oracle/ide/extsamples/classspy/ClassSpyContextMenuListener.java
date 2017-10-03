package oracle.ide.extsamples.classspy;

import oracle.ide.Context;
import oracle.ide.controller.ContextMenu;
import oracle.ide.controller.ContextMenuListener;
import oracle.ide.controller.IdeAction;

public class ClassSpyContextMenuListener implements ContextMenuListener {

    public void menuWillShow(ContextMenu contextMenu) {
        contextMenu.add( contextMenu.createMenuItem( 
            IdeAction.find( ClassSpyCommand.actionId() )                     
        ));
    }

    public void menuWillHide(ContextMenu contextMenu) {
    }

    public boolean handleDefaultAction(Context context) {
        return false;
    }
}

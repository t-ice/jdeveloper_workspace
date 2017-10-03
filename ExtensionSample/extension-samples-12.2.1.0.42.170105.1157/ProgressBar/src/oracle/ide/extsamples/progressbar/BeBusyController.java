package oracle.ide.extsamples.progressbar;

import oracle.ide.Context;
import oracle.ide.controller.Controller;
import oracle.ide.controller.IdeAction;
import oracle.ide.extension.RegisteredByExtension;


/**
 * Controller for action esdksample.doBusyWork.
 */
@RegisteredByExtension("oracle.ide.extsamples.progressbar")
public final class BeBusyController implements Controller {
    public boolean update(IdeAction action, Context context) {
        return true;
    }

    public boolean handleEvent(IdeAction action, Context context) {
        // Command is handled by oracle.ide.extsamples.progressbar.BeBusyCommand
        return false;
    }
}

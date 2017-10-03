package oracle.ide.extsamples.externaltoolimportexport;

import oracle.ide.Context;
import oracle.ide.controller.Controller;
import oracle.ide.controller.IdeAction;
import oracle.ide.extension.RegisteredByExtension;


/**
 * Controller for action oracle.ide.extsamples.externaltoolimportexport.importexternaltools.
 */
@RegisteredByExtension("oracle.ide.extsamples.externaltoolimportexport")
public final class ImportExternalToolsController implements Controller {
    public boolean update(IdeAction action, Context context) {
        // TODO Determine when oracle.ide.extsamples.externaltoolimportexport.importexternaltools is enabled, and call action.setEnabled().
        return true;
    }

    public boolean handleEvent(IdeAction action, Context context) {
        // Command is handled by oracle.ide.extsamples.externaltoolimportexport.ImportExternalToolsCommand
        return false;
    }
}

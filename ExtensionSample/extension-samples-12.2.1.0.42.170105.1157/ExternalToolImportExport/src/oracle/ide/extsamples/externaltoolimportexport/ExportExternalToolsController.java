package oracle.ide.extsamples.externaltoolimportexport;

import oracle.ide.Context;
import oracle.ide.controller.Controller;
import oracle.ide.controller.IdeAction;
import oracle.ide.extension.RegisteredByExtension;
import oracle.ide.externaltools.ExternalToolManager;


/**
 * Controller for action oracle.ide.extsamples.externaltoolimportexport.exportexternaltools.
 */
@RegisteredByExtension("oracle.ide.extsamples.externaltoolimportexport")
public final class ExportExternalToolsController implements Controller {
    public boolean update(IdeAction action, Context context) {
        // Only enable the action if there is at least one external tool.
        ExternalToolManager mgr = ExternalToolManager.getExternalToolManager();
        action.setEnabled( !mgr.tools().isEmpty() );
        return true;
    }

    public boolean handleEvent(IdeAction action, Context context) {
        return false;
    }
}

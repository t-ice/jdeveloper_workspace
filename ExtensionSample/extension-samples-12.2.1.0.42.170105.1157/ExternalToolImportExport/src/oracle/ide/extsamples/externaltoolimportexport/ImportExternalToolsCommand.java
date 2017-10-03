package oracle.ide.extsamples.externaltoolimportexport;

import java.io.IOException;

import java.net.URL;

import oracle.ide.Ide;
import oracle.ide.controller.Command;
import oracle.ide.dialogs.DialogUtil;
import oracle.ide.extension.RegisteredByExtension;
import oracle.ide.net.URLChooser;

/**
 * Command handler for oracle.ide.extsamples.externaltoolimportexport.importexternaltools.
 */
public final class ImportExternalToolsCommand extends Command {
    public ImportExternalToolsCommand() {
        super(actionId());
    }

    public int doit() throws IOException {
        URLChooser chooser = DialogUtil.newURLChooser( getContext() );
        chooser.setSelectionMode( URLChooser.SINGLE_SELECTION );
        int result = chooser.showOpenDialog( Ide.getMainWindow(), "Import External Tools" );
        if ( result != URLChooser.APPROVE_OPTION ) return OK;
        
        URL url = chooser.getSelectedURL();
        if ( url != null ) new ToolImportExport().importTools( url );
        return OK;
    }
    
    /**
     * Returns the id of the action this command is associated with.
     *
     * @return the id of the action this command is associated with.
     * @throws IllegalStateException if the action this command is associated
     *    with is not registered.
     */
    public static int actionId() {
        final Integer cmdId =
            Ide.findCmdID("oracle.ide.extsamples.externaltoolimportexport.importexternaltools.ImportAction");
        if (cmdId == null)
            throw new IllegalStateException("Action oracle.ide.extsamples.externaltoolimportexport.importexternaltools.ImportAction not found.");
        return cmdId;
    }
}

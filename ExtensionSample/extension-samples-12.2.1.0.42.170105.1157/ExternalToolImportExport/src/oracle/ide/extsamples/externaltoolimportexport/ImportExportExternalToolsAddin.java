package oracle.ide.extsamples.externaltoolimportexport;

import javax.swing.JMenuItem;

import oracle.ide.Addin;
import oracle.ide.Ide;
import oracle.ide.IdeMainWindow;
import oracle.ide.controller.IdeAction;
import oracle.ide.controller.MenuManager;
import oracle.ide.extension.RegisteredByExtension;
import javax.swing.JMenu;

/**
 * TODO Provide javadoc comment for ImportExportExternalToolsAddin.
 */
@RegisteredByExtension("oracle.ide.extsamples.externaltoolimportexport")
final class ImportExportExternalToolsAddin implements Addin {
    public void initialize() {
        Ide.getMenubar().add( 
            createMenuItem( ImportExternalToolsCommand.actionId() ),
            toolsMenu()
        );
        Ide.getMenubar().add( 
            createMenuItem( ExportExternalToolsCommand.actionId() ),
            toolsMenu()
        );
    }

    private JMenu toolsMenu() {
        return MenuManager.getJMenu( IdeMainWindow.MENU_TOOLS );
    }
    
    private JMenuItem createMenuItem( int actionId ) {
        return Ide.getMenubar().createMenuItem( IdeAction.find( actionId ) );
    }
}

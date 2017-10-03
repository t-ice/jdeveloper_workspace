/* $Header: ProjectStructureAddin.java 16-aug-2007.22:22:27 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.extsamples.structurepane;

import oracle.ide.Addin;
import oracle.ide.explorer.ExplorerManager;
import oracle.ide.model.Project;
import oracle.ide.model.Workspace;

/**
 * Sample that installs a structure window for projects (and generally, any
 * node that implements oracle.javatools.data.PropertyStorage).
 */
final class ProjectStructureAddin implements Addin {
    public void initialize() {
        // Register for Projects and Workspaces.
        ExplorerManager.getExplorerManager().register(Project.class,
                                                      PropertyStorageExplorer.class,
                                                      null);
        ExplorerManager.getExplorerManager().register(Workspace.class,
                                                      PropertyStorageExplorer.class,
                                                      null);
    }
}

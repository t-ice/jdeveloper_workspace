/* $Header: CreateStructureCommand.java 20-aug-2007.13:43:01 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/20/07 - Reformat to JCS. Fix some minor code issues.
    bduff       02/27/07 - Fix ctor
    bduff       02/27/07 - Creation
 */

package oracle.ide.extsamples.createstructure;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import oracle.ide.Ide;
import oracle.ide.cmd.NewWorkspaceCommand;
import oracle.ide.controller.Command;
import oracle.ide.log.LogManager;
import oracle.ide.log.LogPage;
import oracle.ide.model.NodeFactory;
import oracle.ide.model.Project;
import oracle.ide.model.ProjectContent;
import oracle.ide.model.ProjectMigrator;
import oracle.ide.model.ProjectVersion;
import oracle.ide.model.Workspace;
import oracle.ide.net.URLFactory;
import oracle.ide.net.URLFileSystem;


/**
 * A command that demonstrates how to create an application and a project
 * from scratch, and add them to the ide.
 *
 * @since 11.1.1
 */
public final class CreateStructureCommand extends Command {
    public CreateStructureCommand() {
        super(CreateStructure.CREATE_STRUCT_CMD_ID);
    }

    public int doit() throws Exception {
        String tmpDir = System.getProperty("java.io.tmpdir");
        URL tmpDirURL = URLFactory.newDirURL(new File(tmpDir));

        if (!URLFileSystem.isDirectory(tmpDirURL))
            throw new IllegalStateException("No temporary directory");

        Workspace application =
            NewWorkspaceCommand.createEmptyWorkspace(getContext(),
                                                     URLFactory.newURL(tmpDirURL,
                                                                       "SampleApplication.jws"));
        if (application == null)
            return log("Can't create application.");

        Project project =
            createProjectInApplication(URLFactory.newURL(tmpDirURL,
                                                         "SampleProject.jpr"),
                                       application);
        if (project == null)
            return log("Can't create project");

        return OK;
    }

    private Project createProjectInApplication(URL projectURL,
                                               Workspace app) throws Exception {
        // N.b. You can also call oracle.jdeveloper.cmd.NewEmptyProjectCommand
        //
        // return oracle.jdeveloper.cmd.NewEmptyProjectCommand.createProjectInWorkspace( projectURL, app );
        //

        // This sample has been written against oracle.ide.* APIs only, so
        // we avoid using jdeveloper APIs here. It's not inconcievable that
        // the createProjectInWorkspace() method on oracle.jdeveloper.cmd.NewEmptyProjectCommand
        // could be hoisted up to the IDE level in a future release...
        //
        // BDUFF (Note to self): The code here is much cleaner than the code in
        // NewEmptyProjectCommand too...

        final Project newProject = createNewProject(projectURL);
        if (newProject == null)
            return null;

        newProject.applyBatchChanges(new Runnable() {
                    public void run() {
                        initializeNewProject(newProject);
                    }
                });

        Project existingProject = (Project)NodeFactory.find(projectURL);
        if (existingProject != null)
            updateExistingProject(existingProject, newProject);

        Project projectToAdd =
            existingProject == null ? newProject : existingProject;
        app.add(projectToAdd, true);

        app.save();
        projectToAdd.save();

        return projectToAdd;
    }

    private void updateExistingProject(Project oldProject,
                                       Project newProject) throws IOException {
        oldProject.close();

        newProject.save();
        newProject.close();

        oldProject.open();

        // Force the old project back into the node cache.
        NodeFactory.recache(newProject.getURL(), newProject.getURL(),
                            oldProject);
    }

    private Project createNewProject(URL newURL) throws IOException,
                                                        IllegalAccessException,
                                                        InstantiationException {
        // Try to clone the default project. If we can't find the default project
        // (it may not exist in some products based on the IDE), fall back to
        // just "raw" creation via the node factory findOrCreate method.

        Project defaultProject = Ide.getDefaultProject();
        if (defaultProject == null)
            return NodeFactory.findOrCreateOrFail(Project.class, newURL);

        // It's possible changes were made to the default project, and it hasn't
        // been saved yet. Make sure we save it first.
        if (defaultProject.isDirty())
            defaultProject.save();

        return (Project)NodeFactory.clone(defaultProject, newURL);
    }

    private void initializeNewProject(Project project) {
        ProjectContent.initializeContentSets(project);
        ProjectVersion.initializeVersions(project,
                                          ProjectMigrator.MIGRATOR_NAME);
    }


    private int log(String message) {
        LogPage msgLog = LogManager.getLogManager().getMsgPage();
        msgLog.log("(ESDK Sample - CreateStructure) " + message + "\n");

        return OK;
    }
}

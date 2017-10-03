package de.ikb.jdev.extension.taskflow;

import de.ikb.jdev.extension.utils.FileReadAndWriteHelper;
import de.ikb.jdev.extension.utils.Generator;
import de.ikb.jdev.extension.utils.StringHelper;

import freemarker.core.ParseException;

import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

public class TaskflowGenerator extends Generator {

    private static final String XPATH_AUTHENTICATED_ROLE_PERMISSIONS_NODE =
        "/jazn-data/policy-store/applications/application/jazn-policy/grant/grantee/principals/principal[name='authenticated-role']/../../../permissions";

    Template controllerTemplate = null;
    Template taskflowfTemplate = null;
    Template permissionTemplate = null;
    Template viewTemplate = null;

    public void generate(TaskflowModel taskflowModel) {

        // set encoding and template path
        Configuration cfg = setup();

        // load templates into cache
        boolean templatesLoaded = loadTemplates(cfg);

        if (templatesLoaded) {
            String controllerFileName = StringHelper.capFirst(taskflowModel.getTaskflowName()) + "Controller.java";
            String taskflowFileName = StringHelper.uncapFirst(taskflowModel.getTaskflowName()) + "TF.xml";

            // create a data-model
            Map dataModel = new HashMap();
            dataModel.put("taskflowModel", taskflowModel);

            // write java sources
            FileReadAndWriteHelper.generateAndWriteFile(dataModel, controllerTemplate,
                                                        taskflowModel.getJavaSourceDirPath() + controllerFileName);

            // write TF
            FileReadAndWriteHelper.generateAndWriteFile(dataModel, taskflowfTemplate,
                                                        taskflowModel.getTaskflowAbsoluteDirPath() + taskflowFileName);

            // manipulate jazn if available
            if (taskflowModel.getJaznFilePath() != null) {
                FileReadAndWriteHelper.generateAndInsertIntoXML(taskflowModel.getJaznFilePath(),
                                                                XPATH_AUTHENTICATED_ROLE_PERMISSIONS_NODE, dataModel,
                                                                permissionTemplate);
            }

            FileReadAndWriteHelper.generateAndWriteFile(dataModel, viewTemplate,
                                                        taskflowModel.getJsfAbsoluteDirPath() +
                                                        StringHelper.uncapFirst(taskflowModel.getTaskflowName()) +
                                                        ".jsff");
        }

    }


    @Override
    public boolean loadTemplates(Configuration cfg) {
        boolean successfullyLoaded = true;
        try {
            controllerTemplate = cfg.getTemplate("ControllerTemplate.ftl");
            taskflowfTemplate = cfg.getTemplate("TaskflowTemplate.ftl");
            permissionTemplate = cfg.getTemplate("PermissionTemplate.ftl");
            viewTemplate = cfg.getTemplate("ViewTemplate.ftl");
        } catch (MalformedTemplateNameException | ParseException | TemplateNotFoundException e) {
            e.printStackTrace();
            successfullyLoaded = false;
        } catch (IOException e) {
            e.printStackTrace();
            successfullyLoaded = false;
        }
        return successfullyLoaded;
    }


}

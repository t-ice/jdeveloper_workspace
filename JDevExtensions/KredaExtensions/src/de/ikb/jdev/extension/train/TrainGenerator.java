package de.ikb.jdev.extension.train;

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

public class TrainGenerator extends Generator {

    private static final String XPATH_AUTHENTICATED_ROLE_PERMISSIONS_NODE =
        "/jazn-data/policy-store/applications/application/jazn-policy/grant/grantee/principals/principal[name='authenticated-role']/../../../permissions";

    Template trainControllerTemplate = null;
    Template trainConfigurationManagerTemplate = null;
    Template taskflowfTemplate = null;
    Template uiBundleTemplate = null;
    Template viewTemplate = null;
    Template permissionTemplate = null;
    Template trainMessageCxtTemplate = null;
    Template readMeTemplate = null;

    public void generate(TrainModel trainModel) {

        // set encoding and template path
        Configuration cfg = setup();

        // load templates into cache
        boolean templatesLoaded = loadTemplates(cfg);

        if (templatesLoaded) {
            // create a data-model
            Map dataModel = new HashMap();
            dataModel.put("trainModel", trainModel);

            // generate data-model with template and write output
            String trainControllerFileName = trainModel.getTrainName() + "TrainController.java";
            String trainConfigManagerFileName = trainModel.getTrainName() + "TrainConfigurationManager.java";
            String trainMessageCxtFileName = trainModel.getTrainName() + "TrainMessageContext.java";
            String taskflowFileName = StringHelper.uncapFirst(trainModel.getTrainName()) + "TrainTF.xml";

            // write java sources
            FileReadAndWriteHelper.generateAndWriteFile(dataModel, trainControllerTemplate,
                                                        trainModel.getJavaSourceDirPath() + trainControllerFileName);
            FileReadAndWriteHelper.generateAndWriteFile(dataModel, trainConfigurationManagerTemplate,
                                                        trainModel.getJavaSourceDirPath() + trainConfigManagerFileName);
            FileReadAndWriteHelper.generateAndWriteFile(dataModel, trainMessageCxtTemplate,
                                                        trainModel.getJavaSourceDirPath() + trainMessageCxtFileName);


            // manipulate uiBundle
            FileReadAndWriteHelper.generateAndAppendToFile(dataModel, uiBundleTemplate,
                                                           trainModel.getResourceBundleFilePath());
            // manipulate jazn if available
            if (trainModel.getJaznFilePath() != null) {
                FileReadAndWriteHelper.generateAndInsertIntoXML(trainModel.getJaznFilePath(),
                                                                XPATH_AUTHENTICATED_ROLE_PERMISSIONS_NODE, dataModel,
                                                                permissionTemplate);
            }

            // write JSFs
            for (Step step : trainModel.getSteps()) {
                FileReadAndWriteHelper.generateAndWriteFile(dataModel, viewTemplate,
                                                            trainModel.getJsfAbsoluteDirPath() +
                                                            StringHelper.uncapFirst(step.getName()) + ".jsf");
            }

            // write ReadMe
            FileReadAndWriteHelper.generateAndWriteFile(dataModel, readMeTemplate,
                                                        trainModel.getJsfAbsoluteDirPath() + "ReadMe.txt");

            // write TF
            FileReadAndWriteHelper.generateAndWriteFile(dataModel, taskflowfTemplate,
                                                        trainModel.getTaskflowAbsoluteDirPath() + taskflowFileName);
        }
    }

    @Override
    public boolean loadTemplates(Configuration cfg) {
        boolean successfullyLoaded = true;
        try {
            trainControllerTemplate = cfg.getTemplate("TrainControllerTemplate.ftl");
            trainConfigurationManagerTemplate = cfg.getTemplate("TrainConfigurationManagerTemplate.ftl");
            taskflowfTemplate = cfg.getTemplate("TaskflowTemplate.ftl");
            uiBundleTemplate = cfg.getTemplate("UiBundleTemplate.ftl");
            viewTemplate = cfg.getTemplate("ViewTemplate.ftl");
            permissionTemplate = cfg.getTemplate("PermissionTemplate.ftl");
            trainMessageCxtTemplate = cfg.getTemplate("TrainMessageContext.ftl");
            readMeTemplate = cfg.getTemplate("ReadMe.ftl");
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

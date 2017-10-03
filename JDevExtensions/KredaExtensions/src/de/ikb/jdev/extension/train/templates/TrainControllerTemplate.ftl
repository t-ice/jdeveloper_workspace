package ${trainModel.packageName};

import de.ikb.adf.basis.utils.ADFHelper;
import de.ikb.adf.kreda.basis.ui.train.TrainConfiguration;
import de.ikb.adf.kreda.basis.ui.train.TrainController;

import oracle.adf.share.logging.ADFLogger;

import ${trainModel.packageName}.${trainModel.trainName}TrainConfigurationManager;


public class ${trainModel.trainName}TrainController extends TrainController {
    @SuppressWarnings("compatibility:-6519550706393338609")
    private static final long serialVersionUID = 1L;
    
    private static final String CLASS_NAME = ${trainModel.trainName}TrainController.class.getName();
    private static ADFLogger LOG = ADFLogger.createADFLogger(CLASS_NAME);


    @Override
    public String initTrainSteps() {
        LOG.entering(CLASS_NAME, "initTrainSteps");
        String action = "";
        ${trainModel.trainName}TrainConfigurationManager trainManager = new ${trainModel.trainName}TrainConfigurationManager();

        // lade die in den Taskflow übergebene Konfiguration
        LOG.finest("getInitialConf(): " + getInitialConf());
        TrainConfiguration initialTrainConf = trainManager.loadConfigs(getInitialConf());
        if (initialTrainConf != null) {
            LOG.finest("initialTrainConf found.");
            // setze die in den Taskflow übergebene Konf. als aktive
            setActiveConf(initialTrainConf);
            // setze den aktiven Step
            action = decideNavigationAction(getInitialActiveStep());
        } else {
            LOG.warning("No initialTrainConf found.");
        }
        LOG.exiting(CLASS_NAME, "initTrainSteps", action);
        return action;
    }


}

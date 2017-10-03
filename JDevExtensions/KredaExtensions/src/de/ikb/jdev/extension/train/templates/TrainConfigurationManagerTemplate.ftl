package ${trainModel.packageName};

import de.ikb.adf.basis.utils.JSFHelper;
import de.ikb.adf.kreda.basis.ui.train.Step;
import de.ikb.adf.kreda.basis.ui.train.TrainConfiguration;
import de.ikb.adf.kreda.basis.ui.train.TrainConfigurationManager;

import java.util.HashMap;
import java.util.Map;

import oracle.adf.share.logging.ADFLogger;

public class ${trainModel.trainName}TrainConfigurationManager extends TrainConfigurationManager {
    @SuppressWarnings("compatibility:-4738223797966689006")
    private static final long serialVersionUID = 1L;
    private static final String CLASS_NAME = ${trainModel.trainName}TrainConfigurationManager.class.getName();
    private static ADFLogger LOG = ADFLogger.createADFLogger(CLASS_NAME);


    private static final String RES_BUNDLE_UI = "${trainModel.uiBundleName}";

    private static final String CONFIG = "${trainModel.configName}";


    private Map<String, TrainConfiguration> configurationMap = new HashMap<String, TrainConfiguration>();


    
    <#list trainModel.steps as step>
    public static final String STEP_${step.name?upper_case}_ID = "step${step.name}";
    </#list>


    // Steps
    <#list trainModel.steps as step>
    Step step${step.name} =
        new Step(STEP_${step.name?upper_case}_ID, "${step.name}",
                 JSFHelper.getValueFromResourceBundle(RES_BUNDLE_UI, "${trainModel.trainName?upper_case}TRAIN_STEP_${step.name?upper_case}"));
    </#list>
    

    @Override
    public TrainConfiguration loadConfigs(String configName) {
        TrainConfiguration config;

        switch (configName) {
        case CONFIG:
        
            config = new TrainConfiguration(STEP_${trainModel.firstStepName?upper_case}_ID);
            // createStep ist seiteneffektfrei
            <#list trainModel.steps as step>
            config.getUsedSteps().put(step${step.name}.getStepId(), Step.createStep(step${step.name}));             
            </#list>            
                
            configurationMap.put(CONFIG, config);
            break;
        default:
            throw new IllegalArgumentException("Konfiguration >>" + configName + "<< nicht gefunden!");
        }
        
        return config;
    }
}

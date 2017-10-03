package ${trainModel.packageName};


import de.ikb.adf.kreda.basis.ui.train.TrainMessageContext;

import java.util.Map;

import oracle.adf.share.logging.ADFLogger;

import oracle.javatools.util.Tuple;


public class ${trainModel.trainName}TrainMessageContext extends TrainMessageContext {

    @SuppressWarnings("compatibility:-4153523064747290582")
    private static final long serialVersionUID = -8415316198570778725L;

    private static final String MY_CLASS_NAME = ${trainModel.trainName}TrainMessageContext.class.getName();
    private static final ADFLogger LOG = ADFLogger.createADFLogger(MY_CLASS_NAME);
   

    @Override
    public Map<String, Tuple<String, String>> createMap() {
        LOG.entering(MY_CLASS_NAME, "createMap");        
        Map<String, Tuple<String, String>> messageMap = super.createMap();
        
        <#list trainModel.steps as step>
        // EXAMPLE
        messageMap.put("TODO NameEO.validate TODO Name",
                       new Tuple<String, String>("TODO jsfComponentId",
                                                 ${trainModel.trainName}TrainConfigurationManager.STEP_${step.name?upper_case}_ID));
                                                 
        </#list>                                         
                                              
        LOG.exiting(MY_CLASS_NAME, "createMap", messageMap);
        return messageMap;
    }
}

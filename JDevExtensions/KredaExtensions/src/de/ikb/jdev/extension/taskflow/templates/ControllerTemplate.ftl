package ${taskflowModel.packageName};

import de.ikb.adf.basis.utils.ADFHelper;
import de.ikb.adf.kreda.basis.ui.controller.KredaBasisController;

import oracle.adf.share.logging.ADFLogger;

import org.apache.myfaces.trinidad.event.ReturnEvent;

public class ${taskflowModel.taskflowName?cap_first}Controller extends KredaBasisController {
    @SuppressWarnings("compatibility:-6519550226393338609")
    private static final long serialVersionUID = 1L;
    
    private static final String CLASS_NAME = ${taskflowModel.taskflowName?cap_first}Controller.class.getName();
    private static ADFLogger LOG = ADFLogger.createADFLogger(CLASS_NAME);

    @Override
    public void afterDialogCommit(ReturnEvent returnEvent) {
        LOG.entering(CLASS_NAME, "afterDialogCommit", returnEvent);
        // TODO lese Aenderungen nach commit implementieren
        /*final String RETURN_ID_KEY = "id";
        Map<Object, Object> returnParams = returnEvent.getReturnParameters();
        if (returnParams.containsKey(RETURN_ID_KEY)) {
            // create sequence bzw. id aus dem Train lesen
            DBSequence id = (DBSequence) returnParams.get(RETURN_ID_KEY);

            OperationBinding executeWithParamsBinding = ADFHelper.findOperation("ExecuteWithParams");
            executeWithParamsBinding.execute();

            OperationBinding setCurrentRowBinding = ADFHelper.findOperation("setCurrentRowWithKeyValue");
            setCurrentRowBinding.getParamsMap().put("rowKey", id);
            executeWithParamsBinding.execute();

        } else {
            LOG.finer("Keine Id zurueckgegeben! returnEvent: " + returnEvent);
        }
        LOG.exiting(CLASS_NAME, "afterDialogCommit");*/
    }
}

package oracle.ide.extsamples.progressbar;

import oracle.ide.Ide;
import oracle.ide.controller.Command;
import oracle.ide.dialogs.ProgressBar;
import oracle.ide.extension.RegisteredByExtension;

/**
 * Command handler for esdksample.doBusyWork.
 */
@RegisteredByExtension("oracle.ide.extsamples.progressbar")
public final class BeBusyCommand extends Command {
    public BeBusyCommand() {
        super(actionId());
    }

    public int doit() {
        RunnableWork work = new RunnableWork(); // What has to be done

        final String LABEL = "Long Running Task (ESDK Sample)";
        ProgressBar progress = new ProgressBar(Ide.getMainWindow(),
                                               LABEL,
                                               work,
                                               true);
        progress.setCancelable(false);
        work.setProgressBar(progress);
        progress.start("Working, please wait...", null);

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
        final Integer cmdId = Ide.findCmdID("esdksample.doBusyWork");
        if (cmdId == null)
            throw new IllegalStateException("Action esdksample.doBusyWork not found.");
        return cmdId;
    }
}

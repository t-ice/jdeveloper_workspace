/* $Header: CreateStructure.java 27-feb-2007.22:37:33 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/20/07 - Reformat to JCS. Code cleanup.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.extsamples.progressbar;

import oracle.ide.dialogs.ProgressBar;
import oracle.ide.log.LogManager;

import oracle.javatools.util.ClosureException;
import oracle.javatools.util.SwingClosure;


final class RunnableWork implements Runnable {
    private ProgressBar progressbar;

    /**
     * Log a message safely on the AWT event thread. We must dispach calls that
     * affect UI on the right thread.
     *
     * @param message
     */
    private void logMessage(final String message) {
        try {
            new SwingClosure() {
                    protected void runImpl() {
                        LogManager.getLogManager().showLog();
                        LogManager.getLogManager().getMsgPage().log(message);
                    }
                }.run();
        } catch (ClosureException ce) {
            ce.printStackTrace();
        }
    }

    public void run() {
        try {
            logMessage("Pretending to do something expensive.");
            for (int i = 0; i < 20; i++) {
                Thread.sleep(500);
                logMessage(".");
            }
            logMessage("I'm done!\n");
            logMessage("Thanks for using the ProgressBar.\n");
        } catch (InterruptedException exc) {
            Thread.interrupted();
        } finally {
            progressbar.setDoneStatus(); 
        }
    }

    void setProgressBar(ProgressBar progressBar) {
        progressbar = progressBar;
    }
}

/* $Header: GenerateLinkCommand.java 16-aug-2007.20:27:02 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Initial revision
 */
package oracle.ide.extsamples.clickableurl;

import java.io.PrintWriter;

import java.net.URL;

import javax.swing.JOptionPane;

import oracle.ide.Ide;
import oracle.ide.controller.Command;
import oracle.ide.extension.RegisteredByExtension;
import oracle.ide.log.Href;
import oracle.ide.log.LogManager;
import oracle.ide.model.Element;
import oracle.ide.model.Node;
import oracle.ide.webbrowser.BrowserRunner;


/**
 * Command handler for esdksample.GenerateLink.
 */
@RegisteredByExtension("oracle.ide.extsamples.clickableurl")
public final class GenerateLinkCommand extends Command {

    private static MessageWindow messageWindow = null;

    public GenerateLinkCommand() {
        super(actionId());
    }

    /**
     * Returns the id of the action this command is associated with.
     *
     * @return the id of the action this command is associated with.
     * @throws IllegalStateException if the action this command is associated
     *    with is not registered.
     */
    public static int actionId() {
        final Integer cmdId = Ide.findCmdID("esdksample.GenerateLink");
        if (cmdId == null)
            throw new IllegalStateException("Action esdksample.GenerateLink not found.");
        return cmdId;
    }

    public int doit() {
        Element element = ((context == null) ? null : context.getElement());
        if ( element == null ) return CANCEL;

        if (element instanceof Node) {
            Node node = (Node)element;
            logLink(node.getURL(), element.getShortLabel());
        } else {
            JOptionPane.showMessageDialog(null,
                                          element.getShortLabel() + " cannot be represented as a URL",
                                          "Click",
                                          JOptionPane.WARNING_MESSAGE);
        }
        return OK;
    }

    private void logLink(final URL url, String mess) {
        final String str = mess;
        Href href = new Href(str, url) {
                public void go() {
                    launchBrowser(url);
                }
            };
        LogManager.getLogManager().showLog();
        if (messageWindow == null) messageWindow = new MessageWindow("ClickableURL ESDK Sample");
        messageWindow.show();
        messageWindow.log(href);
        messageWindow.log("\n");

    }

    private static void launchBrowser(URL browserURL) {
        BrowserRunner.getBrowserRunner().runBrowserOnURL(browserURL, null,
                                      new PrintWriter(System.out));
    }

}

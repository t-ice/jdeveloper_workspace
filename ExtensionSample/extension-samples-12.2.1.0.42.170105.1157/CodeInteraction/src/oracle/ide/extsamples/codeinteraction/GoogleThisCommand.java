/* $Header: GoogleThisCommand.java 27-feb-2007.22:37:33 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/20/07 - Initial revision
 */
package oracle.ide.extsamples.codeinteraction;

import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;

import oracle.ide.Ide;
import oracle.ide.ceditor.CodeEditor;
import oracle.ide.controller.Command;
import oracle.ide.extension.RegisteredByExtension;
import oracle.ide.net.URLFactory;
import oracle.ide.webbrowser.BrowserRunner;

/**
 * Command handler for esdksample.GoogleThis.
 */
@RegisteredByExtension("oracle.ide.extsamples.codeinteraction")
public final class GoogleThisCommand extends Command {
    public GoogleThisCommand() {
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
        final Integer cmdId = Ide.findCmdID("esdksample.GoogleThis");
        if (cmdId == null)
            throw new IllegalStateException("Action esdksample.GoogleThis not found.");
        return cmdId;
    }

    public int doit() {
        String text = ((CodeEditor)getContext().getView()).getSelectedText();
        
        googleFor( text );

        return OK;
    }
    
    private void googleFor( String text ) {
        String url = "http://www.google.com/search?q=" + escape( text );
        BrowserRunner.getBrowserRunner().runBrowserOnURL(
            URLFactory.newURL( url ),
            null, null
        );
    }
    
    private String escape( String text ) {
        try {
            return URLEncoder.encode( text, "UTF-8" );
        }
        catch ( UnsupportedEncodingException e ) {
            throw new IllegalStateException( e );    
        }
    }


}

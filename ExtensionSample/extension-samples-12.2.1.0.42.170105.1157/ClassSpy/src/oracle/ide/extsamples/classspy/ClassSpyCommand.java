/* $Header: ClassSpyCommand.java 16-aug-2007.20:27:02 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Initial revision
 */
package oracle.ide.extsamples.classspy;

import java.util.Date;
import java.util.Iterator;

import oracle.ide.Ide;
import oracle.ide.controller.Command;
import oracle.ide.extension.RegisteredByExtension;
import oracle.ide.log.LogManager;
import oracle.ide.model.Element;
import oracle.ide.model.Node;
import oracle.ide.model.Project;
import oracle.ide.model.Workspace;
import oracle.ide.net.URLFileSystem;
import oracle.ide.view.View;

/**
 * Command handler for esdksample.showElementClass.
 */
@RegisteredByExtension("oracle.jdeveloper.extsamples.classspy")
public final class ClassSpyCommand extends Command {
    public ClassSpyCommand() {
        super(actionId());
    }

    public int doit() {
        logln( "=== Spying IDE context at " + new Date() + " ===");
        logln( "Context View: " + displayText( context.getView() ) );
        logln( "Context Application: " + displayText( context.getWorkspace() ) );
        logln( "Context Project: " + displayText( context.getProject() ) );
        logln( "Context Selection: " );
        logln( displayText( context.getSelection() ) );
        
        if ( context.getWorkspace() != null ) {
            logln( "Projects in application:" );
            dumpProjects( context.getWorkspace() );            
            logln( "" );
        }
        
        if ( context.getProject() != null ) {
            logln( "Children of project:" );
            dumpChildren( context.getProject() );            
        }
                
        return OK;
    }
    
    private void dumpChildren( Project project  ) {
        for ( Iterator i = project.getChildren(); i.hasNext(); ) {
            logln( "  " + displayText( (Element)i.next() ) );
        }
    }
    
    private void dumpProjects( Workspace workspace ) {
        for ( Project p : workspace.projects() ) {
            logln( "  " + displayText( p ) );
        }
    }
    
    private String displayText( Element[] elements ){
        if ( elements == null  ) return "  <null>";
        if ( elements.length == 0 ) return "  <empty>";
        StringBuilder b = new StringBuilder();
        for ( Element e : elements ) {
            b.append( "  " );
            b.append( displayText( e ) );
            b.append( "\n" );
        }
        return b.toString();
    }
    
    private String displayText( Element element ) {
        if ( element == null ) return "<null>";
        
        StringBuilder b = new StringBuilder();
        b.append( "[" );
        b.append( element.getClass().getName() );
        b.append( "] " );
        if ( element instanceof Node ) {
            b.append( URLFileSystem.getPlatformPathName( ((Node)element).getURL()  ));
        }
        else {
            b.append( element.getShortLabel() );
        }
        
        return b.toString();
    }

    private String displayText( View view ) {
        if ( view == null ) return "<null>";
        
        StringBuilder b = new StringBuilder();
        b.append( "[" );
        b.append( view.getClass().getName() );
        b.append( "] " );
        b.append( view.getId() ) ;
        
        return b.toString();
    }

    private void logln( String message ) {
        LogManager lm = LogManager.getLogManager();
        lm.showLog();
        lm.getMsgPage().log( message + "\n" );
    }

    /**
     * Returns the id of the action this command is associated with.
     *
     * @return the id of the action this command is associated with.
     * @throws IllegalStateException if the action this command is associated
     *    with is not registered.
     */
    public static int actionId() {
        final Integer cmdId = Ide.findCmdID("esdksample.showElementClass");
        if (cmdId == null)
            throw new IllegalStateException("Action esdksample.showElementClass not found.");
        return cmdId;
    }
}

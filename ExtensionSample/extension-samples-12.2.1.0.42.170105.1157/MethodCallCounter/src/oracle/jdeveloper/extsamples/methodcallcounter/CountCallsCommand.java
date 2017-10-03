/* $Header: CountCallsCommand.java 27-feb-2007.22:37:33 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/20/07 - Initial Revision
 */
package oracle.jdeveloper.extsamples.methodcallcounter;

import javax.swing.JOptionPane;

import oracle.ide.Context;
import oracle.ide.Ide;
import oracle.ide.controller.Command;
import oracle.ide.extension.RegisteredByExtension;

import oracle.javatools.dialogs.MessageDialog;

import oracle.javatools.parser.java.v2.model.SourceElement;
import oracle.javatools.parser.java.v2.model.SourceFile;

import oracle.javatools.parser.java.v2.model.expression.SourceMethodCallExpression;

import oracle.jdeveloper.java.JavaManager;
import oracle.jdeveloper.model.JavaSourceNode;

/**
 * Command handler for esdksample.countMethodCalls.
 */
@RegisteredByExtension("oracle.ide.extsamples.methodcallcounter")
public final class CountCallsCommand extends Command {
    public CountCallsCommand() {
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
        final Integer cmdId = Ide.findCmdID("esdksample.countMethodCalls");
        if (cmdId == null)
            throw new IllegalStateException("Action esdksample.countMethodCalls not found.");
        return cmdId;
    }

    public int doit() {
        String methodName = prompt();
        int count = countMethodCalls( context, methodName );
        
        MessageDialog.information( Ide.getMainWindow(), 
            methodName + " is called " + count + " times.", "Count Method Calls", null );
        
        return OK;
    }

    private String prompt() {
        return JOptionPane.showInputDialog( Ide.getMainWindow(), "Enter a method name:" );
    }
    
    private int countMethodCalls( Context context, String methodName ) {
        JavaSourceNode sourceNode = (JavaSourceNode) context.getElement();
        if ( sourceNode == null )
            sourceNode = (JavaSourceNode) context.getNode();
        JavaManager manager = JavaManager.getJavaManager( context.getProject() );
        SourceFile sourceFile = manager.getSourceFile( sourceNode.getURL() );
        sourceFile.resolve();
        
        return countMethodCalls( sourceFile, methodName, 0 );
    }
    
    private int countMethodCalls( SourceElement element, String methodName, int count ) {
        if ( element instanceof SourceMethodCallExpression ) {
            if ( methodName.equals( ((SourceMethodCallExpression)element).getName() ) )
                count++;
        }
        for ( Object o : element.getChildren() ) {
            count = countMethodCalls( (SourceElement)o, methodName, count );
        }
        
        return count;
    }
}

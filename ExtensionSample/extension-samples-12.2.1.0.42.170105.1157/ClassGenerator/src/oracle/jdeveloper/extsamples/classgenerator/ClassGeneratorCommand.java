/* $Header: jdev/src/esdk-samples/sample/ClassGenerator/src/oracle/jdeveloper/extsamples/classgenerator/ClassGeneratorCommand.java /main/3 2008/11/26 00:33:21 ltribble Exp $ */

/* Copyright (c) 2007, 2008, Oracle and/or its affiliates.
All rights reserved. */

/*
   MODIFIED    (MM/DD/YY)
    sblight     09/11/08 - 
    bduff       08/16/07 - Initial revision
 */
package oracle.jdeveloper.extsamples.classgenerator;

import java.io.IOException;

import java.lang.reflect.Modifier;

import java.net.URL;

import oracle.ide.Ide;
import oracle.ide.controller.Command;
import oracle.ide.editor.EditorManager;
import oracle.ide.model.Element;
import oracle.ide.model.NodeFactory;
import oracle.ide.model.Project;
import oracle.ide.net.URLFactory;
import oracle.ide.net.URLFileSystem;
import oracle.ide.net.URLPath;

import oracle.javatools.parser.java.v2.JavaConstants;
import oracle.javatools.parser.java.v2.SourceFactory;
import oracle.javatools.parser.java.v2.model.SourceClass;
import oracle.javatools.parser.java.v2.model.SourceElement;
import oracle.javatools.parser.java.v2.model.SourceFile;
import oracle.javatools.parser.java.v2.model.SourceMethod;
import oracle.javatools.parser.java.v2.model.SourceTypeReference;
import oracle.javatools.parser.java.v2.model.doc.SourceDocComment;
import oracle.javatools.parser.java.v2.write.SourceTransaction;

import oracle.jdeveloper.java.JavaManager;
import oracle.jdeveloper.java.TransactionDescriptor;
import oracle.jdeveloper.model.JavaSourceNode;
import oracle.jdeveloper.model.PathsConfiguration;


public class ClassGeneratorCommand extends Command {
    public ClassGeneratorCommand() {
        super(actionId(), NO_CHANGE);
    }

    /**
     * Returns the id of the action this command is associated with.
     *
     * @return the id of the action this command is associated with.
     * @throws IllegalStateException if the action this command is associated
     *    with is not registered.
     */
    public static int actionId() {
        final Integer cmdId = Ide.findCmdID("esdksample.classGenerator");
        if (cmdId == null)
            throw new IllegalStateException("Action esdksample.classGenerator not found.");
        return cmdId;
    }


    public int doit() {

        Element element = context.getElement();
        if (element instanceof Project) {
            new ClassGeneratorWizard().runWizard(Ide.getMainWindow(),
                                                 (Project)element, this);

        }

        return OK;
    }

    private URL getSourceURL(Project project, String packageName,
                             String className) {
        final URLPath srcPath =
            PathsConfiguration.getInstance(project).getSourcePath();

        // Get the first sourcepath entry.
        if (srcPath.size() > 0) {
            URL srcDir = srcPath.asList().get(0);
            URL dirURL =
                URLFactory.newDirURL(srcDir, packageName.replace('.', '/'));
            return URLFactory.newURL(dirURL, className + ".java");
        }
        return null;
    }

    void generate(Project project,
                  ClassGeneratorWizardModel model) throws IOException {
        //  Since we are create a new class and it will need a name, let's
        //  make up a name, made from the project's name.

        final String packageName = model.getPackageName();

        final String simpleClassName = model.getClassName();
        URL sourceURL = getSourceURL(project, packageName, simpleClassName);

        final String fullyQualifiedClassName =
            packageName + "." + simpleClassName;

        if (sourceURL == null) {
            throw new IOException("Cannot create URL for " +
                                  fullyQualifiedClassName);
        }

        final JavaSourceNode jsn = loadJavaSourceNode(sourceURL);

        JavaManager javaMgr = JavaManager.getJavaManager(project);
        SourceFile javaFile = javaMgr.getSourceFile(jsn.getURL());

        // If javaFile is not null, the source file already
        // exists. We don't want to overwrite it, so bail
        // out now.
        if (javaFile != null) {
            throw new IOException(URLFileSystem.getPlatformPathName(sourceURL) +
                                  " already exists!");
        }

        jsn.open();
        jsn.save();

        createContent(model, jsn, project);
        jsn.save();
        EditorManager.getEditorManager().openDefaultEditorInFrame(jsn.getURL());
    }

    private JavaSourceNode loadJavaSourceNode(URL sourceURL) {
        return NodeFactory.findOrCreateOrFail(JavaSourceNode.class, sourceURL);
    }

    /**
     * Creates the actual source code for the sample class
     */
    private static void createContent(ClassGeneratorWizardModel model,
                                            JavaSourceNode node,
                                            Project prj) throws IOException {

        JavaManager javaMgr = JavaManager.getJavaManager(prj);
        SourceFile javaFile = javaMgr.getSourceFile(node.getURL());

        // but if it fails, I cannot proceed.
        if (javaFile == null)
            throw new IOException("Null javaFile!");

        // A transaction will be created.
        final SourceTransaction st = javaMgr.beginTransaction(javaFile);
        try {

            SourceFactory factory = javaFile.getFactory();
            javaFile.setPackageName(model.getPackageName());


            SourceClass addinClass =
                factory.createClass(JavaConstants.TY_CLASS,
                                    model.getClassName());
            addinClass.addSelf(javaFile);
            addinClass.setModifiers(Modifier.PUBLIC);

            SourceTypeReference addinTypeReference =
                factory.createType("oracle.ide.Addin");
            addinTypeReference.addSelf(addinClass.getInterfacesClause());

            SourceMethod init =
                factory.createMethod(factory.createType(JavaConstants.PRIMITIVE_VOID),
                                     "initialize", null, null,
                                     factory.createBlock("// TODO: Implement this method!"));
            init.setModifiers(Modifier.PUBLIC);
            init.addSelf(addinClass);


            SourceDocComment comment =
                factory.createDocCommentFromText("/** An addin class, created by " +
                                                 model.getAuthorName() + "*/");
            comment.addSelf(addinClass);

            javaFile.reformatSelf( SourceElement.REFORMAT_ALL );

            // My work here is done.
            javaMgr.commitTransaction(st,
                                      new TransactionDescriptor("Generate file"));
        } catch (Throwable e) {
            st.abort();
            e.printStackTrace();
            IOException ioe =
                new IOException("Unexpected exception: " + e.getMessage());
            ioe.initCause(e);
            throw ioe;
        }
    }

}

/* $Header: ExecuteQueryCommand.java 16-aug-2007.21:53:07 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner (original author rcleveng)
 */

package oracle.ide.extsamples.customeditor.extension;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import oracle.bali.xml.addin.XMLSourceNode;
import oracle.bali.xml.model.XmlModel;

import oracle.ide.Context;
import oracle.ide.Ide;
import oracle.ide.controller.Command;
import oracle.ide.extsamples.customeditor.model.XMLQueryModel;
import oracle.ide.extsamples.customeditor.util.PrettyPrinter;
import oracle.ide.model.Node;
import oracle.ide.view.View;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


public class ExecuteQueryCommand extends Command {
    public static final int ID =
        Ide.findOrCreateCmdID(ExecuteQueryCommand.class.getName());

    public ExecuteQueryCommand() {
        super(ExecuteQueryCommand.ID, Command.NO_CHANGE);
    }

    public int doit() {
        final Context context = getContext();
        final View view = context.getView();
        final Node node = context.getNode();
        if (view instanceof XMLQueryEditor && node instanceof XMLSourceNode) {
            final XMLSourceNode xml = (XMLSourceNode)node;
            final XMLQueryModel documentModel =
                (XMLQueryModel)context.getProperty(XMLQueryModel.class.getName());
            final XmlModel model = XMLSourceNode.getXmlContext(context).getModel();
            try {
                model.acquireReadLock();
                executeQuery(documentModel, model.getDocument());
                return Command.OK;
            } finally {
                model.releaseReadLock();
            }
        }
        return Command.CANCEL;
    }

    private boolean executeQuery(XMLQueryModel model, Document document) {
        try {
            final XPath xpath = XPathFactory.newInstance().newXPath();
            xpath.setNamespaceContext(model.createNamespaceContext());
            final NodeList list =
                (NodeList)xpath.evaluate(model.getQuery(), document,
                                         XPathConstants.NODESET);
            if (list != null) {
                final StringBuilder nodeTextBuffer = new StringBuilder(1024);
                for (int i = 0; i < list.getLength(); i++) {
                    nodeTextBuffer.append(PrettyPrinter.print(list.item(i)));
                }
                model.setQueryResults(nodeTextBuffer.toString());
                model.setNumberOfRowsAffected(list.getLength());
                return true;
            }
        } catch (Exception e) {
            model.setQueryResults(getExceptionMessage(e));
            model.setNumberOfRowsAffected(0);
        }
        return false;
    }

    private String getExceptionMessage(Exception e) {
        if (e instanceof XPathExpressionException) {
            final Throwable t = e.getCause();
            if (t instanceof TransformerException) {
                return ((TransformerException)t).getMessageAndLocation();
            }
            return t.getMessage();
        }
        return e.getMessage();
    }

}

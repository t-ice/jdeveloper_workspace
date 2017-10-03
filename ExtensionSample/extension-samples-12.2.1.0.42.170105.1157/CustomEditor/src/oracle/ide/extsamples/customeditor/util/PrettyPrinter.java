/* $Header: PrettyPrinter.java 16-aug-2007.21:53:33 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner (original author rcleveng)
 */
package oracle.ide.extsamples.customeditor.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.Text;


public class PrettyPrinter {
    private PrettyPrinter() {
    }


    public static String print(Node doc) {
        try {
            final OutputStream out = new ByteArrayOutputStream();
            switch (doc.getNodeType()) {
            case Node.ATTRIBUTE_NODE:
                printAttribute((Attr)doc, out);
                break;
            case Node.TEXT_NODE:
                printText((Text)doc, out);
                break;
            default:
                printNode(doc, out);
            }
            return out.toString();
        } catch (TransformerException ex) {
            return ex.getMessage();
        } catch (IOException ex) {
            return ""; //$NON-NLS-1$
        }
    }


    public static void printNode(Node doc,
                                 OutputStream out) throws TransformerException,
                                                          IOException {
        TransformerFactory tfactory = TransformerFactory.newInstance();
        Transformer serializer = tfactory.newTransformer();
        //Setup indenting to pretty print
        serializer.setOutputProperty(OutputKeys.INDENT, "yes"); //$NON-NLS-1$
        serializer.setOutputProperty(OutputKeys.STANDALONE,
                                     "no"); //$NON-NLS-1$
        serializer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
                                     "yes"); //$NON-NLS-1$

        serializer.transform(new DOMSource(doc), new StreamResult(out));
        addCRLF(out);

    }

    private static void addCRLF(OutputStream out) throws IOException {
        out.write("\n".getBytes()); //$NON-NLS-1$
    }

    private static void printAttribute(Attr attr,
                                       OutputStream out) throws IOException {
        out.write(attr.getValue().getBytes());
        addCRLF(out);
    }

    private static void printText(Text text,
                                  OutputStream out) throws IOException {
        out.write(text.getTextContent().getBytes());
        addCRLF(out);
    }
}

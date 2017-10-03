/* $Header: ExecuteQueryUtils.java 16-aug-2007.21:53:11 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner (original author rcleveng)
 */
package oracle.ide.extsamples.customeditor.extension;

import java.util.logging.Logger;

import javax.xml.XMLConstants;

import oracle.ide.extsamples.customeditor.model.XMLQueryModel;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;


final class ExecuteQueryUtils {
    private static Logger logger =
        Logger.getLogger(ExecuteQueryUtils.class.getName());

    private ExecuteQueryUtils() {
    }

    static void registerDocumentNamespacesAndPrefixes(XMLQueryModel model,
                                                      Document document) {
        try {
            final Element root = document.getDocumentElement();
            final NamedNodeMap attrs = root.getAttributes();
            final int length = attrs.getLength();
            for (int i = 0; i < length; i++) {
                final Attr attr = (Attr)attrs.item(i);
                final String name = attr.getName();
                final String uri = attr.getValue();
                if (name.startsWith(XMLConstants.XMLNS_ATTRIBUTE)) {
                    if (name.length() > 5) {
                        final String prefix =
                            name.substring(XMLConstants.XMLNS_ATTRIBUTE.length() +
                                           1);
                        model.registerPrefixAndNamespace(prefix, uri);
                        logger.info(prefix + ":" + uri); //$NON-NLS-1$
                    } else {
                        model.setDefaultNamespaceURI(uri);
                        logger.info("default URI=" + uri); //$NON-NLS-1$
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

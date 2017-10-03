/* $Header: MyXMLCategoryDataProvider.java 16-aug-2007.19:38:29 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.extsamples.appoverview;

import java.net.URL;

import oracle.ide.category.URLDataProvider;
import oracle.ide.index.QueryCriteria;
import oracle.ide.index.file.FileCriteria;
import oracle.ide.model.Project;
import oracle.ide.net.URLFileSystem;


/**
 * This class gathers content and status information files with an extension of
 * .myxml.  These files will be considered part of the XML Category
 */

public final class MyXMLCategoryDataProvider extends URLDataProvider {
    private static final String XML_EXT = ".xml"; //NOTRANS


    /**
     * This defines the query criteria that will be used to get the urls from
     * the project that have a .xml extension.
     * @return a query critera object for use in getting the contents to display
     * in the application overview
     */
    public QueryCriteria getQueryCriteria() {
        final QueryCriteria criteria = new QueryCriteria();
        criteria.put(FileCriteria.FILE_EXTENSION, XML_EXT);
        return criteria;
    }

    /**
     * Used to determine if the specified project and url belong to the category
     * @param project the project the url is in
     * @param url
     * @return true if the category should contain the url; false otherwise
     */
    public boolean accept(Project project, URL url) {
        return URLFileSystem.getSuffix(url).equals(XML_EXT);
    }
}

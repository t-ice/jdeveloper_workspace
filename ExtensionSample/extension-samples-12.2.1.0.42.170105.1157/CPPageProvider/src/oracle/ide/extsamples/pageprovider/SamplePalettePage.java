/* $Header: jdev/src/esdk-samples/sample/CPPageProvider/src/oracle/ide/extsamples/pageprovider/SamplePalettePage.java /main/4 2012/03/28 11:37:31 svassile Exp $ */

/* Copyright (c) 2007, 2012, Oracle and/or its affiliates. 
All rights reserved. */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/21/07 - Add copyright header. Reformat to JCS.
 */
package oracle.ide.extsamples.pageprovider;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import oracle.ide.palette2.DefaultPalettePage;

/**
 * SamplePalettePage
 * <p>
 * This example extends DefaultPalettePage.  DefaultPalettePage comprises the attributes
 * and methods that this example requires. A constructor is added to accomodate
 * the data values for this sample.
 * </p>
 */

public class SamplePalettePage
  extends DefaultPalettePage
{

  public SamplePalettePage(String pageId, String pageName, String pageDescription, String pageIcon, String pageType,
                           String pageShowForTypes, String pageTechnologyScope)
  {
    setName(pageName);
    setDescription(pageDescription);
    setIcon(pageIcon);
    setData(PAGE_PAGEID, pageId);
    setData(PAGE_TYPE, pageType);

    // make delimited string into a List of strings
    List<String> showForTypes = new ArrayList<String>();
    final StringTokenizer tknTypes = new StringTokenizer(pageShowForTypes, ";"); //NOTRANS
    while (tknTypes.hasMoreTokens())
    {
      String token = tknTypes.nextToken();
      showForTypes.add(token);
    }
    setData(PAGE_SHOWFORTYPES, showForTypes);

    // make delimited string into a List of strings
    List<String> technologyScope = new ArrayList<String>();
    final StringTokenizer tknScopes = new StringTokenizer(pageTechnologyScope, ";"); //NOTRANS
    while (tknScopes.hasMoreTokens())
    {
      String token = tknScopes.nextToken();
      technologyScope.add(token);
    }
    setData(PAGE_TECHNOLOGYSCOPES, technologyScope);

  }
}

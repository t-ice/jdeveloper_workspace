/* $Header: jdev/src/esdk-samples/sample/CPPageProvider/src/oracle/ide/extsamples/pageprovider/SamplePages.java /main/10 2013/01/25 11:43:23 sjfarrel Exp $ */

/* Copyright (c) 2007, 2012, Oracle and/or its affiliates. 
All rights reserved. */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/21/07 - Add copyright header. Reformat to JCS.
 */
package oracle.ide.extsamples.pageprovider;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import oracle.ide.Context;
import oracle.ide.net.URLFileSystem;

import oracle.ide.palette2.DefaultPaletteSection;
import oracle.ide.palette2.PaletteItem;
import oracle.ide.palette2.PalettePage;
import oracle.ide.palette2.PalettePages;
import oracle.ide.palette2.PalettePagesListener;

/**
 * SamplePages
 * <p>
 * This class creates a palette page with a single group that contains a
 * single section that contains a single item when the pageType is "java".
 * As required this class extends PalettePages.
 * </p>
 * <p>
 * TODO: palettePagesListener
 * </p>
 */

public class SamplePages extends PalettePages {

    private final static String SAMPLEPROVIDER_ID =
        SamplePageProvider.class.getName();

    /**
     * Singleton
     */
    private static SamplePages _singleton = new SamplePages();

    /**
     * Composite for PalettePagesListener
     */
    protected List<PalettePagesListener> palettePagesListeners;

    /**
     * Composite for PalettePage
     */
    protected List<PalettePage> palettePages;

    // Default constructor

    public SamplePages() {
    }

    /**
     * Returns the singleton instance of SamplePages.
     * @return the singleton instance of SamplePages
     */
    public static SamplePages getInstance() {
        return _singleton;
    }

    /**
     * Initialize palettePages.
     * @param context
     */
    public void initialize(Context context) {
        if (context == null || context.getNode() == null)
            return;
        URL url = context.getNode().getURL();
        final String pageType = getSuffix(url);

        // Only interested in java.
        if (pageType.equals("java")) {
            if (palettePages != null) {
                palettePages.clear();
            }

            // create a PalettePage
            SamplePalettePage page =
                new SamplePalettePage("oracle.ide.samples.pageprovider.SampPage01",
                                      // pageId
                    "My Sample Page", // name
                    "My Sample Page Component", // description
                    null, // icon
                    "java", // type
                    "java", // showForTypes
                    "Java;JavaBeans"); // technologyScope

            // create a PaletteGroup
            SamplePaletteGroup group =
                new SamplePaletteGroup("oracle.ide.samples.pageprovider.SampGroup01",
                                       // groupId
                    "My Sample Group", // name
                    "My Sample Group Component", // description
                    "java"); // type

            // add group to page
            page.addGroup(group);

            // create a section. Make the name null since a separator is not neeeded.
            DefaultPaletteSection section = new DefaultPaletteSection.Builder("SampName01", 
                                                                              "oracle.ide.samples.pageprovider.SampSection01").build(); // name

            // add section to group
            group.addSection(section);

            // create an item. TODO: use SampleBean.java
            SamplePaletteItem item =
                new SamplePaletteItem("oracle.ide.samples.pageprovider.SampItem01",
                                      // itemId
                    SAMPLEPROVIDER_ID, // provider id
                    "My Sample Bean", // name
                    "My Sample Bean Description", // description
                    "/oracle/ide/samples/pageprovider/snapshot.png", // icon
                    "java"); // type

            // add item to section
            section.addItem(item); // add item to section

            // add page
            addPage(page);
        } else {
            if (palettePages != null) {
                palettePages.clear(); 
            }
        }
    }

    /**
     * getPages
     */
    public Collection<PalettePage> getPages() {
        if (palettePages == null) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableList(palettePages);
    }

    /**
     * Returns the PaletteItem identified by itemId.  providerId is used to
     * determine whether this item is owned by this page provider.
     * <p>
     * The provider returns the matching PaletteItem only if the PaletteItem
     * is within the current context.  Null is returned if the PaletteItem is not
     * within the current context or not recognized by this provider.
     * </p>
     * @retrun PaletteItem
     */
    public PaletteItem getItem(String providerId, String itemId) {

        if (providerId == null || providerId.length() == 0 || itemId == null ||
            itemId.length() == 0) {
            return null;
        }

        PaletteItem paletteItem = null;
        if (providerId.equals(SAMPLEPROVIDER_ID)) {
            for (PalettePage palettePage : getPages()) {
                SamplePalettePage sampPage = (SamplePalettePage)palettePage;
                paletteItem = sampPage.getItem(itemId);
                if (paletteItem != null) {
                    break;
                }
            } // end of for
        }
        return paletteItem;
    }

    /**
     * addPalettePagesListener
     */
    public void addPalettePagesListener(PalettePagesListener listener) {
        if (palettePagesListeners == null) {
            palettePagesListeners = new ArrayList<PalettePagesListener>();
        }
        palettePagesListeners.add(listener);
    }

    public void removePalettePagesListener(PalettePagesListener listener) {
        if (palettePagesListeners != null) {
            palettePagesListeners.remove(listener);
        }
    }
    /*
     * add pages to palettePages.
     */

    private void addPage(SamplePalettePage sampPage) {
        if (palettePages == null) {
            palettePages = new ArrayList<PalettePage>();
        }
        palettePages.add(sampPage);
    }

    /*
    * Return suffix
    * @param title Title of EditorFrame
    */

    private String getSuffix(URL url) {
        final String suffix = URLFileSystem.getSuffix(url);
        int period = suffix.lastIndexOf(".");
        if (period != -1) {
            // Is a selected file
            return suffix.substring(period + 1);
        }
        return "";
    }

}

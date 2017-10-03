/* $Header: SampleBean.java 16-aug-2007.22:25:48 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/21/07 - Add copyright header. Reformat to JCS.
 */
package oracle.ide.extsamples.pageprovider;

import oracle.ide.palette2.DefaultPaletteItem;

/**
 * SamplePaletteItem
 * <p>
 * This example extends DefaultPaletteItem. DefaultPaletteItem comprises the attributes
 * and methods that this example requires. A constructor is added to accomodate
 * the data values for this sample.
 * </p>
 *
 */

public class SamplePaletteItem extends DefaultPaletteItem {
    public SamplePaletteItem(String itemId, String itemProviderId,
                             String itemName, String itemDescription,
                             String itemIcon, String itemType) {
        setName(itemName);
        setDescription(itemDescription);
        setIcon(itemIcon);
        setItemId(itemId);
        setProviderId(itemProviderId);
        setData(ITEM_TYPE, itemType);

    }
}

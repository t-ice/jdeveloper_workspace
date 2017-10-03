/* $Header: SampleBean.java 16-aug-2007.22:25:48 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/21/07 - Add copyright header. Reformat to JCS.
 */
package oracle.ide.extsamples.pageprovider;

import oracle.ide.palette2.DefaultPaletteGroup;

/**
 * SamplePaletteGroup
 * <p>
 * This example extends DefaultPaletteGroup.  DefaultPaletteGroup comprises the attributes
 * and methods that this example requires. A constructor is added to accomodate
 * the data values for this sample.
 * </p>
 *
 */

public class SamplePaletteGroup extends DefaultPaletteGroup {
    public SamplePaletteGroup(String groupId, String groupName,
                              String groupDescription, String groupType) {
        setName(groupName);
        setDescription(groupDescription);
        setData(GROUP_GROUPID, groupId);
        setData(GROUP_TYPE, groupType);
    }
}

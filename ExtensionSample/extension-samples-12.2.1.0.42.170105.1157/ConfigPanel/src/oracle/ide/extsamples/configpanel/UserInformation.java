/* $Header: UserInformation.java 16-aug-2007.22:25:42 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS. Minor code cleanup.
    bduff       02/27/07 - Big rewrite for 11.1.1 ESDK
 */
package oracle.ide.extsamples.configpanel;

import oracle.javatools.data.HashStructure;
import oracle.javatools.data.HashStructureAdapter;
import oracle.javatools.data.PropertyStorage;
import oracle.javatools.data.StructureChangeListener;

/**
 * This is a preferences object, used as the data model for user preferences. It
 * consists of two properties, name and organization, which you can obtain
 * from the preferences object using the {@link #getName()} and {@link #getOrganization()}
 * methods.
 */
// Start with class being final.  You can always remove final if subclassing ever
// proves useful.  In many cases, subclassing is actually unnecessary and may get
// you into an instanceof/typecast mess.  Consider defining a separate (not
// subclass) adapter class instead.
public final class UserInformation extends HashStructureAdapter {
    /**
     * The DATA_KEY should be a hard-coded String to guarantee that its value stays
     * constant across releases.  Specifically, do NOT use CoolFeaturePrefs.class.getName().
     * The reason is that if CoolFeaturePrefs is ever renamed or moved,
     * CoolFeaturePrefs.class.getName() will cause the DATA_KEY String to change, which
     * introduces a preferences migration issue (since this key is used in the persisted
     * XML) that will require more code and testing to accomodate and open up your code to
     * annoying little bugs. Always eliminate this cause of bugs by using a hard-coded String for DATA_KEY.
     *
     * By convention, DATA_KEY should be the fully qualified class name of the
     * HashStructureAdapter.  This helps ensure against name collisions.  This also makes it
     * easier to identify what piece of code is responsible for a preference when you're
     * looking at the XML in the product-preferences.xml file.  Of course, that only works
     * as long as the adapter class itself is never renamed or moved, so avoid renaming or
     * moving this class once it's been released in production.
     */
    private static final String DATA_KEY =
        "oracle.ide.extsamples.UserInformation";

    // Private constructor enforces use of the public factory method below.

    private UserInformation(HashStructure hash) {
        super(hash);
    }

    /**
     * Factory method should take a PropertyStorage (instead of HashStructure directly).
     * This decouples the origin of the HashStructure and allows the future possibility
     * of resolving preferences through multiple layers of HashStructure.
     *
     * Classes/methods that currently implement/return PropertyStorage:
     * - oracle.ide.config.Preferences
     * - oracle.ide.model.Project
     * - oracle.ide.model.Workspace
     * - oracle.ide.panels.TraversableContext.getPropertyStorage()
     */
    public static UserInformation getInstance(PropertyStorage prefs) {
        // findOrCreate makes sure the HashStructure is not null.  If it is null, a
        // new empty HashStructure is created and the default property values will
        // be determined by the getters below.
        return new UserInformation(findOrCreate(prefs, DATA_KEY));
    }

    // A key for an individual property in this preferences object. These names
    // are used directly in the persistence file that stores preferences, so you
    // either need to keep them the same between releases, or deal with migration
    // from previous names in this adapter class.
    private static final String KEY_NAME = "name";

    public String getName() {
        // The second parameter to getString() is a default value. Defaults are
        // stored in the persistent preferences file using a "placeholder".
        return getHashStructure().getString(KEY_NAME, System.getProperty("user.name"));
    }

    public void setName(String name) {
        getHashStructure().putString(KEY_NAME, name);
    }

    private static final String KEY_ORGANIZATION = "organization";

    public String getOrganization() {
        return getHashStructure().getString(KEY_ORGANIZATION);
    }

    public void setOrganization(String organization) {
        getHashStructure().putString(KEY_ORGANIZATION, organization);
    }

    // Support for listening for changes.

    public void addStructureChangeListener(StructureChangeListener l) {
        getHashStructure().addStructureChangeListener(l);
    }

    public void removeStructureChangeListener(StructureChangeListener l) {
        getHashStructure().removeStructureChangeListener(l);
    }
}

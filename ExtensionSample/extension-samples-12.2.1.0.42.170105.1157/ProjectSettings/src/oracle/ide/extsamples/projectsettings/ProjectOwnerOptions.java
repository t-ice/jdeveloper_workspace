/* $Header: ProjectOwnerOptions.java 16-aug-2007.22:18:46 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.extsamples.projectsettings;

import oracle.javatools.data.HashStructureAdapter;
import oracle.javatools.data.PropertyStorage;
import oracle.javatools.data.HashStructure;

public final class ProjectOwnerOptions extends HashStructureAdapter {
    static final String KEY_OPTIONS = "projectOwnerDetails";

    static final String EMAIL_ADDRESS = "EmailAddress";
    static final String NAME = "Name";

    private ProjectOwnerOptions(HashStructure hash) {
        super(hash);
    }


    /**
     * Get an instance of project owner options for the given property storage.
     *
     * @param project property storage.
     * @return the project owner options for the specified property storage.
     */
    public static ProjectOwnerOptions getInstance(PropertyStorage project) {
        return new ProjectOwnerOptions(findOrCreate(project, KEY_OPTIONS));
    }

    /**
     * Get the email address.
     *
     * @return the email address.
     */
    public String getEmailAddress() {
        return getHashStructure().getString(EMAIL_ADDRESS);
    }

    /**
     * Set the email address.
     *
     * @param emailAddress the email address.
     */
    public void setEmailAddress(String emailAddress) {
        getHashStructure().putString(EMAIL_ADDRESS, emailAddress);
    }

    /**
     * Get the name of the owner.
     *
     * @return the name of the owner.
     */
    public String getName() {
        return getHashStructure().getString(NAME);
    }

    /**
     * Set the name of the owner.
     *
     * @param name the name of the owner.
     */
    public void setName(String name) {
        getHashStructure().putString(NAME, name);
    }
}

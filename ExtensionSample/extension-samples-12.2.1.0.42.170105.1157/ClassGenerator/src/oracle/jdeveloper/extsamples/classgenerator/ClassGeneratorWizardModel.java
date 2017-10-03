/* $Header: ClassGeneratorWizardModel.java 16-aug-2007.21:03:53 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat with JCS
    bduff       02/27/07 - Added copyright banner
 */
package oracle.jdeveloper.extsamples.classgenerator;

/**
 * A model class that stores information set in the wizard.
 */
final class ClassGeneratorWizardModel {
    private String _packageName;
    private String _className;
    private String _authorName;
    private boolean _installShutdownHook;

    void setPackageName(String packageName) {
        _packageName = packageName;
    }

    String getPackageName() {
        return _packageName;
    }

    void setClassName(String className) {
        _className = className;
    }

    String getClassName() {
        return _className;
    }

    void setAuthorName(String authorName) {
        _authorName = authorName;
    }

    String getAuthorName() {
        return _authorName;
    }

    void setInstallShutdownHook(boolean installShutdownHook) {
        _installShutdownHook = installShutdownHook;
    }

    boolean isInstallShutdownHook() {
        return _installShutdownHook;
    }
}

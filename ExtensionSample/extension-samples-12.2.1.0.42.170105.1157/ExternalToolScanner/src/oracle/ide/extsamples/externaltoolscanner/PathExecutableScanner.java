/* $Header: jdev/src/esdk-samples/sample/ExternalToolScanner/src/oracle/ide/extsamples/externaltoolscanner/PathExecutableScanner.java /main/3 2011/02/28 11:50:04 dlane Exp $ */

/* Copyright (c) 2007, 2011, Oracle and/or its affiliates. 
All rights reserved. */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       08/07/07 - Initial Revision
 */
package oracle.ide.extsamples.externaltoolscanner;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import oracle.ide.externaltools.Availability;
import oracle.ide.externaltools.ExternalProgramToolProperties;
import oracle.ide.externaltools.ExternalTool;
import oracle.ide.externaltools.ExternalToolBaseProperties;
import oracle.ide.externaltools.ExternalToolFactory;
import oracle.ide.externaltools.ExternalToolManager;
import oracle.ide.externaltools.ExternalToolScanner;
import oracle.ide.externaltools.IntegrationPoint;

/**
 * An external tools scanner that finds tools on the system path.
 */
final class PathExecutableScanner extends ExternalToolScanner {

    private final Collection<String> PROGRAMS_TO_LOOK_FOR =
        set("emacs", "nautilus", "notepad.exe");

   

    public ExternalTool[] findTools(Collection<ExternalTool> collection) {
        // Subtract tags that have already been created from the set of
        // programs to look for.
        Set<String> scannerTags = ExternalToolManager.scannerTags(collection);
        Set<String> missingTags = new HashSet<String>(PROGRAMS_TO_LOOK_FOR);
        missingTags.removeAll(scannerTags);

        if (missingTags.isEmpty())
            return new ExternalTool[0];

        return create(missingTags);
    }

    private static <T> Set<T> set(T... items) {
        return Collections.unmodifiableSet(new HashSet<T>(Arrays.asList(items)));
    }

    private ExternalTool[] create(Set<String> missingTags) {
        List<ExternalTool> newTools = new ArrayList<ExternalTool>();
        for (String pathEntry : pathEntries()) {
            Set<String> filenames = filenames(pathEntry);
            for (Iterator<String> i = missingTags.iterator(); i.hasNext(); ) {
                String tag = i.next();
                if (!filenames.contains(tag))
                    continue;

                newTools.add(createTool(pathEntry, tag));
                i.remove();
            }
        }
        return newTools.toArray(new ExternalTool[0]);
    }

    private String[] pathEntries() {
        String path = System.getenv("PATH");
        if (path == null) return new String[0];
        return path.split(File.pathSeparator);
    }

    private Set<String> filenames(String dir) {
        File[] filesInDir = new File(dir).listFiles();
        if (filesInDir == null)
            return Collections.emptySet();

        Set<String> filenames = new HashSet<String>(filesInDir.length);
        for (File file : filesInDir) {
            filenames.add(file.getName());
        }

        return filenames;
    }

    private ExternalTool createTool(String pathEntry, String tag) {
        ExternalTool tool =
            ExternalToolFactory.getInstance().createProgramTool();

        configureBaseProperties(tag, tool);
        configureProgramProperties(pathEntry, tag, tool);

        return tool;
    }

    private void configureProgramProperties(String pathEntry, String tag,
                                            ExternalTool tool) {
        ExternalProgramToolProperties programProperties =
            ExternalProgramToolProperties.getInstance(tool);
        programProperties.setExecutable(new File(pathEntry,
                                                 tag).getAbsolutePath());
        programProperties.setRunDirectory(new File(pathEntry).getAbsolutePath());
        programProperties.setArguments("${file.path}");
    }

    private void configureBaseProperties(String tag, ExternalTool tool) {
        ExternalToolBaseProperties baseProperties =
            ExternalToolBaseProperties.getInstance(tool);
        baseProperties.setScannerTag(tag);
        baseProperties.setCaption(tag +
                                  " (found by ExternalToolScanner ESDK Sample)");
        baseProperties.setAvailability(Availability.ALWAYS);
        baseProperties.setIntegrated(IntegrationPoint.TOOLS_MENU, true);
    }


}

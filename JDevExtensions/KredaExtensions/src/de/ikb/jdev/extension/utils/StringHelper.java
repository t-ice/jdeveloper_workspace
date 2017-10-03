package de.ikb.jdev.extension.utils;

import org.apache.commons.lang3.StringEscapeUtils;


public class StringHelper extends StringEscapeUtils {
    
    public static String uncapFirst(String inputString) {
        char c[];
        c = inputString.toCharArray();
        c[0] = Character.toLowerCase(c[0]);
        return new String(c);
    }

    public static String ersetzeUmlaute(String ohneUmlauteStep) {
        ohneUmlauteStep = ohneUmlauteStep.replaceAll("\u00E4", "ae");
        ohneUmlauteStep = ohneUmlauteStep.replaceAll("\u00F6", "oe");
        ohneUmlauteStep = ohneUmlauteStep.replaceAll("\u00FC", "ue");
        ohneUmlauteStep = ohneUmlauteStep.replaceAll("\u00DF", "ss");
        ohneUmlauteStep = ohneUmlauteStep.replaceAll("\u00C4", "Ae");
        ohneUmlauteStep = ohneUmlauteStep.replaceAll("\u00D6", "Oe");
        ohneUmlauteStep = ohneUmlauteStep.replaceAll("\u00DC", "Ue");
        return ohneUmlauteStep;
    }
    
    public static String capFirst(String inputString) {
        char c[];
        c = inputString.toCharArray();
        c[0] = Character.toUpperCase(c[0]);
        return new String(c);
    }
}

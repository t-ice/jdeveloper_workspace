package de.ikb.jdev.extension.utils;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

public abstract class Generator {

    public Configuration setup() {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_25);
        // Pfad relativ zum Generator
        cfg.setClassForTemplateLoading(this.getClass(), "templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        return cfg;
    }

    public abstract boolean loadTemplates(Configuration cfg);
}

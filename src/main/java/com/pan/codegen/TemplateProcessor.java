package com.pan.codegen;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author panjb
 */
public class TemplateProcessor {

    private final static Configuration TPL_CFG = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

    static {
        TPL_CFG.setDefaultEncoding("UTF-8");
    }

    private TemplateProcessor() {
    }

    public static void process(String tplDir, String tplName, Object dataModel, String outPath) {
        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(Files.newOutputStream(Paths.get(outPath)));
            TPL_CFG.setDirectoryForTemplateLoading(new File(tplDir));
            Template template = TPL_CFG.getTemplate(tplName);
            template.process(dataModel, writer);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void process(String specificTpl, Object dataModel, String outPath) {
        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(Files.newOutputStream(Paths.get(outPath)));
            StringTemplateLoader templateLoader = new StringTemplateLoader();
            templateLoader.putTemplate("template", specificTpl);
            TPL_CFG.setTemplateLoader(templateLoader);
            Template template = TPL_CFG.getTemplate("template");
            template.process(dataModel, writer);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

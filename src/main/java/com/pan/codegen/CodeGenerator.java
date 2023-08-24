package com.pan.codegen;

import com.pan.codegen.meta.ClassMeta;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Arrays;

/**
 * 代码生成器
 *
 * @author panjb
 */
public class CodeGenerator {

    public static final String PROTOCOL_JAR = "jar";

    private CodeGenerator() {
    }

    /**
     * 根据半成品类和代码模板生成代码
     * <p>比如下面这个类，处理后，可以生成建造者模式的完整类
     * <pre>{@code
     * class Person {
     *  private LocalDateTime birthday;
     *  private String gender;
     *  private String name;
     *  private int id;
     *  private int age;
     * }
     * generate("builder-pattern.ftl", Person.class)
     * }</pre>
     * @param tplName 模板名称，参见{@code /resources/templates}
     * @param aClass 类
     */
    public static void generate(String tplName, Class<?> aClass) {
        Field[] fields = aClass.getDeclaredFields();
        ClassMeta.Builder builder = ClassMeta.builder()
                .name(aClass.getSimpleName())
                .pkg(aClass.getPackage().getName());
        for (Field field : fields) {
            builder.addField(field.getType(), field.getName());
        }
        generate(tplName, builder.build());
    }

    /**
     * 根据类元信息和代码模板生成代码，
     * @param tplName 模板名称，参见{@code /resources/templates}
     * @param classMeta 类元信息
     */
    public static void generate(String tplName, ClassMeta classMeta) {
        URL resource = CodeGenerator.class.getResource("/templates");
        if (resource == null) {
            throw new RuntimeException("无法找到模板文件夹[/templates]");
        }
        String javaFilePath = buildJavaFilePath(classMeta);
        // 如果运行在jar中，无法定位到/templates具体位置，只能以流的方式读取模板文件
        if (PROTOCOL_JAR.equals(resource.getProtocol())) {
            String specificTpl = loadTemplate(tplName);
            if (StringUtils.isBlank(specificTpl)) {
                return;
            }
            TemplateProcessor.process(specificTpl, classMeta, javaFilePath);
        } else {
            generate(resource.getPath(), tplName, classMeta, javaFilePath);
        }
    }

    public static void generate(String tplDir, String tplName, ClassMeta classMeta, String filePath) {
        String out = StringUtils.isBlank(filePath) ? buildJavaFilePath(classMeta) : filePath;
        TemplateProcessor.process(tplDir, tplName, classMeta, out);
    }

    private static String buildJavaFilePath(ClassMeta classMeta) {
        File file = new File("");
        String pkgPath = StringUtils.join(classMeta.getPkg().split("\\."), File.separator);
        String srcPath = StringUtils.join(Arrays.asList("src", "main", "java"), File.separator);
        return file.getAbsolutePath() + File.separator +
                srcPath + File.separator +
                pkgPath + File.separator +
                classMeta.getName() + ".java";
    }

    private static String loadTemplate(String tplName) {
        BufferedReader br = null;
        try {
            InputStream is = CodeGenerator.class.getResourceAsStream("/templates/".concat(tplName));
            if (is == null) {
                return null;
            }
            br = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder template = new StringBuilder();
            while ((line = br.readLine()) != null) {
                template.append(line).append("\n");
            }
            return template.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

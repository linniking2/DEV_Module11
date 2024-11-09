package org.dev.module6;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;

public class ThymeleafConfig {
    private static TemplateEngine templateEngine;

    public static TemplateEngine getTemplateEngine(ServletContext context) {
        if (templateEngine == null) {
            ServletContextTemplateResolver resolver = new ServletContextTemplateResolver(context);
            resolver.setPrefix("/WEB-INF/templates/");
            resolver.setSuffix(".html");
            resolver.setTemplateMode("HTML");
            resolver.setCharacterEncoding("UTF-8");

            templateEngine = new TemplateEngine();
            templateEngine.setTemplateResolver(resolver);
        }
        return templateEngine;
    }
}


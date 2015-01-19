package org.vaadin.appbase.service.templating.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.service.templating.TemplateData;
import org.vaadin.appbase.session.SessionContext;
import org.vaadin.spring.UIScope;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
@UIScope
@Slf4j
public class TemplatingService implements ITemplatingService {
    @Autowired
    private VelocityEngine velocityEngine;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private SessionContext sessionContext;
    private Map<Locale, Map<String, Object>> contexts;

    @Override
    public TemplateData getLayoutTemplate(String templatePath) {
        TemplateData cachedTemplate = loadFromCache(sessionContext.getLocale(), templatePath);
        if (cachedTemplate != null) {
            return cachedTemplate;
        }

        return TemplateData.of(templatePath, new ByteArrayInputStream(merge(templatePath + ".html", sessionContext
                .getLocale()).getBytes(Charset.forName("UTF-8"))));
    }

    private String merge(String templateLocation, Locale locale) {
        String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, "UTF-8",
                getContextFromCache(locale));

        return text == null ? "<null>" : text;
    }

    private Map<String, Object> getContextFromCache(Locale forLocale) {
        if (contexts == null) {
            contexts = new HashMap<>();
        }

        Map<String, Object> ctx = contexts.get(forLocale);
        if (ctx == null) {
            ctx = createContextForLocale(forLocale);
            contexts.put(forLocale, ctx);
        }
        return ctx;
    }

    @SuppressWarnings("unused")
    private TemplateData loadFromCache(Locale forLocale, String templatePath) {
        TemplateData cached = null;
        // TODO: access cache
        if (cached == null) {
            return null;
        }

        if (log.isTraceEnabled()) {
            log.trace(String.format("Cache hit: template %s, locale %s ", templatePath, forLocale.toString()));
        }
        return cached;
    }

    private Map<String, Object> createContextForLocale(Locale locale) {
        Map<String, Object> context = new HashMap<>(2);
        context.put("messages", this.messageSource);
        context.put("locale", locale);
        return context;
    }
}

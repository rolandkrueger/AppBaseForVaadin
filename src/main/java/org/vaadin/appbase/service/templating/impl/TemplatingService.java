package org.vaadin.appbase.service.templating.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.context.WebApplicationContext;
import org.vaadin.appbase.service.templating.ITemplatingService;

@Service
@org.springframework.context.annotation.Scope (value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Slf4j
public class TemplatingService implements ITemplatingService
{
  @Autowired private VelocityEngine        velocityEngine;
  @Autowired private MessageSource         messageSource;
  private Map<Locale, Map<String, Object>> contexts;

  @Override
  public InputStream getLayoutTemplate (Locale forLocale, String templatePath)
  {
    InputStream cachedTemplate = loadFromCache (forLocale, templatePath);
    if (cachedTemplate != null) { return cachedTemplate; }

    return new ByteArrayInputStream (merge (templatePath + ".html", forLocale).getBytes (Charset.forName ("UTF-8")));
  }

  private String merge (String templateLocation, Locale locale)
  {
    String text = VelocityEngineUtils.mergeTemplateIntoString (velocityEngine, templateLocation, "UTF-8",
        getContextFromCache (locale));

    return text == null ? "<null>" : text;
  }

  private Map<String, Object> getContextFromCache (Locale forLocale)
  {
    if (contexts == null)
    {
      contexts = new HashMap<> ();
    }

    Map<String, Object> ctx = contexts.get (forLocale);
    if (ctx == null)
    {
      ctx = createContextForLocale (forLocale);
      contexts.put (forLocale, ctx);
    }
    return ctx;
  }

  @SuppressWarnings ("unused")
  private InputStream loadFromCache (Locale forLocale, String templatePath)
  {
    InputStream cached = null;
    // TODO: access cache
    if (cached == null) { return null; }

    if (log.isTraceEnabled ())
    {
      log.trace (String.format ("Cache hit: template %s, locale %s ", templatePath, forLocale.toString ()));
    }
    return cached;
  }

  private Map<String, Object> createContextForLocale (Locale locale)
  {
    Map<String, Object> context = new HashMap<> (2);
    context.put ("messages", this.messageSource);
    context.put ("locale", locale);
    return context;
  }
}

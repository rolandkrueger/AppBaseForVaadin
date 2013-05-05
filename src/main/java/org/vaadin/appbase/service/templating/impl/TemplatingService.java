package org.vaadin.appbase.service.templating.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolManager;
import org.apache.velocity.tools.config.EasyFactoryConfiguration;
import org.apache.velocity.tools.generic.ResourceTool;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.vaadin.appbase.service.templating.ITemplatingService;

@Service
@org.springframework.context.annotation.Scope (value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Slf4j
public class TemplatingService implements ITemplatingService
{
  private VelocityEngine       velocityEngine;
  private Map<Locale, Context> contexts;
  @Setter private String       messagePrefix = "msg";
  @Setter private String       bundleNames   = "messages";
  @Setter private String       resourceLoaderRoot;

  @Override
  public InputStream getLayoutTemplate (Locale forLocale, String templatePath)
  {
    initIfNotYetInitialized ();
    InputStream cachedTemplate = loadFromCache (forLocale, templatePath);
    if (cachedTemplate != null) { return cachedTemplate; }

    Context ctx = getContextFromCache (forLocale);
    Template template = velocityEngine.getTemplate (templatePath + ".html");

    StringWriter writer = new StringWriter ();
    template.merge (ctx, writer);
    return new ByteArrayInputStream (writer.toString ().getBytes (Charset.forName ("UTF-8")));
  }

  private void initIfNotYetInitialized ()
  {
    if (velocityEngine == null)
    {
      init ();
    }
  }

  private Context getContextFromCache (Locale forLocale)
  {
    Context ctx = contexts.get (forLocale);
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

  private Context createContextForLocale (Locale locale)
  {
    EasyFactoryConfiguration config = new EasyFactoryConfiguration ();
    config.toolbox (Scope.APPLICATION).tool (messagePrefix, ResourceTool.class).property ("bundles", bundleNames)
        .property ("locale", locale);

    ToolManager manager = new ToolManager (false, false);
    manager.configure (config);

    return manager.createContext ();
  }

  public void init ()
  {
    contexts = new Hashtable<Locale, Context> ();
    velocityEngine = new VelocityEngine ();
    Properties velocityProperties = new Properties ();

    velocityProperties.put ("resource.loader", "url");
    velocityProperties.put ("url.resource.loader.root", resourceLoaderRoot);
    velocityProperties.put ("url.resource.loader.class",
        "org.apache.velocity.runtime.resource.loader.URLResourceLoader");
    velocityEngine.init (velocityProperties);
  }
}

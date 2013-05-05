package org.vaadin.appbase.service.templating;

import java.io.InputStream;
import java.util.Locale;

public interface ITemplatingService
{
  public void setResourceLoaderRoot (String resourceLoaderRoot);

  public InputStream getLayoutTemplate (Locale forLocale, String templatePath);

}
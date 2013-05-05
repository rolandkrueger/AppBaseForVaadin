package org.vaadin.appbase.service.templating;

import java.io.InputStream;
import java.util.Locale;

public interface ITemplatingService
{

  public abstract InputStream getLayoutTemplate (Locale forLocale, String templatePath);

}
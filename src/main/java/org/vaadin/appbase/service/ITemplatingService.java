package org.vaadin.appbase.service;

import java.io.InputStream;
import java.util.Locale;

public interface ITemplatingService
{

  public abstract InputStream getLayoutTemplate (Locale forLocale, String templatePath);

}
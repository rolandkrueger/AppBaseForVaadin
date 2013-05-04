package org.vaadin.appbase.service;

import com.vaadin.ui.CustomLayout;

public interface ICustomLayoutProvider
{
  public CustomLayout getLayout (String templatePath);
}

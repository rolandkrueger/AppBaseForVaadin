package org.vaadin.appbase.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.appbase.service.AbstractUsesServiceProvider;
import org.vaadin.appbase.service.ICustomLayoutProvider;
import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.session.SessionContext;

import com.vaadin.ui.CustomLayout;
import org.vaadin.spring.UIScope;

@Component
@UIScope
public class CustomLayoutProvider extends AbstractUsesServiceProvider implements ICustomLayoutProvider
{
  @Autowired private SessionContext     context;
  @Autowired private ITemplatingService templatingService;

  @Override
  public CustomLayout getLayout (String templatePath)
  {
    return null;
  }

}

package org.vaadin.appbase.service.impl;

import org.springframework.stereotype.Component;
import org.vaadin.appbase.SessionServices;
import org.vaadin.appbase.event.IEventBus;
import org.vaadin.appbase.service.IServiceProvider;

import com.vaadin.ui.UI;

@Component
public class ServiceProvider implements IServiceProvider
{
  @Override
  public IEventBus getEventbus ()
  {
    return getServices ().getEventbus ();
  }

  public static SessionServices getServices ()
  {
    Object services = UI.getCurrent ().getData ();

    if (services == null) { throw new IllegalStateException (
        "No valid SessionServices object found. Did you forget to initialize the session service with SessionServices.startUp()?"); }

    if (!(services instanceof SessionServices)) { throw new IllegalStateException (
        "Current UI's data element is not of type SessionServices. Did you forget to initialize the session service with SessionServices.startUp()?"); }

    return (SessionServices) services;
  }

}

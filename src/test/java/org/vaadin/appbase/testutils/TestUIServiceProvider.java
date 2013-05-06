package org.vaadin.appbase.testutils;

import org.vaadin.appbase.event.AppEventBus;
import org.vaadin.appbase.event.IEventBus;
import org.vaadin.appbase.service.IServiceProvider;

public class TestUIServiceProvider implements IServiceProvider
{
  private IEventBus eventbus;

  @Override
  public IEventBus getEventbus ()
  {
    if (eventbus == null)
    {
      eventbus = new AppEventBus ();
    }
    return eventbus;
  }

}

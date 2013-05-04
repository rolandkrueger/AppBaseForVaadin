package org.vaadin.appbase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.appbase.event.IEventBus;

@Configurable (preConstruction = true)
public abstract class AbstractUsesServiceProvider
{
  private IServiceProvider serviceProvider;

  @Autowired
  public void setUIServiceProvider (IServiceProvider serviceProvider)
  {
    this.serviceProvider = serviceProvider;
    onServiceProviderSet ();
  }

  protected void onServiceProviderSet ()
  {
    // to be overwritten by subclasses as required
  }

  protected IEventBus eventbus ()
  {
    if (serviceProvider == null) { throw new IllegalStateException ("Service Provider has not been set for  "
        + getClass ().getName ()); }
    return serviceProvider.getEventbus ();
  }
}

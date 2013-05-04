package org.vaadin.appbase.service;

import org.vaadin.appbase.event.IEventBus;
import org.vaadin.appbase.service.impl.ServiceProvider;

/**
 * @see ServiceProvider
 * @author Roland Krüger
 */
public interface IServiceProvider
{
  public IEventBus getEventbus ();

}

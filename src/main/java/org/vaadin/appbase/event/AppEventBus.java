package org.vaadin.appbase.event;

import lombok.extern.slf4j.Slf4j;

import com.google.common.eventbus.EventBus;

@Slf4j
public class AppEventBus implements IEventBus
{
  private EventBus eventbus;

  public AppEventBus ()
  {
    eventbus = new EventBus ("DBQBMainEventBus");
  }

  @Override
  public void post (IEvent event)
  {
    if (log.isTraceEnabled ())
    {
      log.trace (event.getSource () + " posted event: " + event);
    }
    eventbus.post (event);
  }

  @Override
  public void register (Object listener)
  {
    if (log.isTraceEnabled ())
    {
      log.trace ("Registering event bus listener: " + listener);
    }
    eventbus.register (listener);
  }

  @Override
  public void unregister (Object listener)
  {
    if (log.isTraceEnabled ())
    {
      log.trace ("Removing listener from event bus: " + listener);
    }
    eventbus.unregister (listener);
  }
}

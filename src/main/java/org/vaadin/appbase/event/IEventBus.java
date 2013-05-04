package org.vaadin.appbase.event;

public interface IEventBus
{

  public abstract void post (IEvent event);

  public abstract void register (Object listener);

  public abstract void unregister (Object listener);

}
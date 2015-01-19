package org.vaadin.appbase.uriactions.handlers;

import org.roklib.webapps.uridispatching.SimpleURIActionHandler;
import org.vaadin.appbase.event.IEventBus;
import org.vaadin.appbase.places.AbstractPlace;
import org.vaadin.appbase.uriactions.commands.PlaceRequestActionCommand;

public class SimplePlaceRequestActionHandler extends SimpleURIActionHandler
{
  public SimplePlaceRequestActionHandler (String actionName, AbstractPlace targetPlace, IEventBus eventBus)
  {
    super (actionName, new PlaceRequestActionCommand (targetPlace, eventBus));
  }
}

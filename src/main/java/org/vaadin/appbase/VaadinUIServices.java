package org.vaadin.appbase;

import lombok.Getter;
import lombok.Setter;

import org.vaadin.appbase.event.AppEventBus;
import org.vaadin.appbase.event.IEventBus;
import org.vaadin.appbase.places.PlaceManager;

import com.vaadin.ui.UI;

public class VaadinUIServices
{
  @Getter @Setter private Object data;
  @Getter private IEventBus      eventbus;
  @Getter private PlaceManager   placeManager;

  public void startUp ()
  {
    if (UI.getCurrent ().getData () != null) { throw new IllegalStateException (
        "Given UI object already provides a data object through UI.getData(). Please use this object as container for this data instead."); }
    UI.getCurrent ().setData (this);

    eventbus = new AppEventBus ();
    placeManager = new PlaceManager ();

  }

}

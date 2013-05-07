package org.vaadin.appbase;

import lombok.Getter;
import lombok.Setter;

import org.vaadin.appbase.event.AppEventBus;
import org.vaadin.appbase.event.IEventBus;
import org.vaadin.appbase.places.PlaceManager;
import org.vaadin.appbase.uriactions.URIActionManager;

import com.vaadin.ui.UI;

public class VaadinUIServices
{
  @Getter @Setter private Object   data;
  @Getter private IEventBus        eventbus;
  @Getter private PlaceManager     placeManager;
  @Getter private URIActionManager uriActionManager;

  public void startUp ()
  {
    if (UI.getCurrent ().getData () != null) { throw new IllegalStateException (
        "Given UI object already provides a data object through UI.getData(). Please use this object as container for this data instead."); }
    UI.getCurrent ().setData (this);

    eventbus = new AppEventBus ();
    placeManager = new PlaceManager ();
    uriActionManager = new URIActionManager ();
    uriActionManager.initialize ();
  }

  public static VaadinUIServices get ()
  {
    Object services = UI.getCurrent ().getData ();

    if (services == null) { throw new IllegalStateException ("No valid SessionServices object found for UI "
        + UI.getCurrent ().toString ()
        + ". Did you forget to initialize the session service with SessionServices.startUp()?"); }

    if (!(services instanceof VaadinUIServices)) { throw new IllegalStateException (
        "Current UI's data element is not of type SessionServices. Did you forget to initialize the session service with SessionServices.startUp()?"); }

    return (VaadinUIServices) services;
  }

}

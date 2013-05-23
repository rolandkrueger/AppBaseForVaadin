package org.vaadin.appbase;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.appbase.event.AppEventBus;
import org.vaadin.appbase.event.IEventBus;
import org.vaadin.appbase.places.PlaceManager;
import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.session.SessionContext;
import org.vaadin.appbase.uriactions.URIActionManager;

import com.vaadin.ui.UI;

@Configurable
public class VaadinUIServices
{
  @Getter @Setter private Object                                                data;
  @Getter private IEventBus                                                     eventbus;
  @Getter private PlaceManager                                                  placeManager;
  @Getter private URIActionManager                                              uriActionManager;
  @Autowired @Getter @Setter (AccessLevel.PROTECTED) private SessionContext     context;
  @Autowired @Getter @Setter (AccessLevel.PROTECTED) private ITemplatingService templatingService;

  /**
   * {@link VaadinUIServices} should be created with {@link #startUp()}.
   */
  private VaadinUIServices ()
  {
  }

  /**
   * Creates a new instance of {@link VaadinUIServices} and sets this instance as data property for
   * the current {@link UI}.
   * 
   * @throws IllegalStateException
   *           if the current UI's data property is alread set, e.g. if this function is called a
   *           second time for the same UI
   */
  public static void startUp ()
  {
    if (UI.getCurrent ().getData () != null) { throw new IllegalStateException (
        "Given UI object already provides a data object through UI.getData(). Please use this object as container for this data instead."); }
    VaadinUIServices instance = new VaadinUIServices ();
    UI.getCurrent ().setData (instance);

    instance.eventbus = new AppEventBus ();
    instance.placeManager = new PlaceManager ();
    instance.uriActionManager = new URIActionManager ();
    instance.uriActionManager.initialize ();
  }

  /**
   * Gets the current {@link VaadinUIServices} instance for the current UI.
   * 
   * @throws IllegalStateException
   *           if the current UI's data property is not a {@link VaadinUIServices} object or if no
   *           such object exists since {@link #startUp()} has not been called
   */
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

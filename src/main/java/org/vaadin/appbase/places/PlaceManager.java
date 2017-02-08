package org.vaadin.appbase.places;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.vaadin.appbase.event.impl.places.PlaceRequestEvent;
import org.vaadin.appbase.service.AbstractUsesServiceProvider;
import org.vaadin.spring.UIScope;

import java.util.*;

/**
 * <p>
 * Der Place Manager verarbeitet alle Place Requests ({@link PlaceRequestEvent} ), die auf den
 * Eventbus gegeben werden. Der Manager entscheidet, ob der Place tatsächlich gerendert werden soll,
 * oder ob eine andere Aktion notwendig ist (z.B. die Anmeldung eines Benutzers).
 * </p>
 * <p>
 * Der Place Manager verwaltet auch die Menge aller Places, die in der Anwendung definiert sind.
 * Alle Places müssen hier mit {@link #registerPlace(AbstractPlace)} angemeldet werden, bevor sie
 * gerendert werden können. Ein Place wird eindeutig über seinen Namen identifiziert.
 * </p>
 */
@Slf4j
@UIScope
@Component
public class PlaceManager extends AbstractUsesServiceProvider
{
  private Stack<AbstractPlace>       activePlaces;
  private Map<String, AbstractPlace> registeredPlaces;

  public PlaceManager ()
  {
    activePlaces = new Stack<> ();
    registeredPlaces = new HashMap<>();
  }

  /**
   * Listener für {@link PlaceRequestEvent}s.
   */
  @Subscribe
  public void receivePlaceRequestEvent (PlaceRequestEvent event)
  {
    AbstractPlace place = event.getPlace ();
    if (log.isDebugEnabled ())
    {
      log.debug ("Handling place request event " + place.getClass ().getName () + " (" + place.getName () + ")  ");
    }

    // Place aktivieren
    activatePlaceRecursively (place);
  }

  private void activatePlaceRecursively (AbstractPlace place)
  {
    if (!place.hasDependentParentPlace ())
    {
      if (!isActive (place))
      {
        activePlaces.clear ();
        activatePlace (place);
      }
      updateActivePlacesForPlace (place);
      return;
    }

    AbstractPlace parentPlace = place.getDependentParentPlace ();
    if (!isActive (parentPlace))
    {
      activatePlaceRecursively (parentPlace);
    } else
    {
      updateActivePlacesForPlace (parentPlace);
    }

    activatePlace (place);
  }

  private void updateActivePlacesForPlace (AbstractPlace place)
  {
    while (!activePlaces.isEmpty ())
    {
      if (activePlaces.peek ().equals (place))
      {
        break;
      } else
      {
        activePlaces.pop ();
      }
    }
  }

  public List<AbstractPlace> getActivePlaces ()
  {
    return Collections.unmodifiableList (activePlaces);
  }

  private boolean isActive (AbstractPlace place)
  {
    return activePlaces.contains (place);
  }

  private void activatePlace (AbstractPlace place)
  {
    activePlaces.push (place);
    place.activate ();
  }

  public void reactivateCurrentPlace ()
  {
    if (!activePlaces.isEmpty ())
    {
      AbstractPlace currentPlace = activePlaces.pop ();
      activatePlaceRecursively (currentPlace);
    }
  }

  public void registerPlace (AbstractPlace place)
  {
    Preconditions.checkArgument (place != null);
    if (isPlaceRegistered (place.getName ())) { throw new IllegalArgumentException (
        "This place is already registered: " + place); }
    registeredPlaces.put (place.getName (), place);
  }

  public AbstractPlace getRegisteredPlace (String placeName)
  {
    Preconditions.checkArgument (placeName != null);
    if (!isPlaceRegistered (placeName)) { throw new IllegalArgumentException (
        "The requested place has not been registered with this place services: " + placeName); }
    return registeredPlaces.get (placeName);
  }

  public boolean isPlaceRegistered (String placeName)
  {
    return registeredPlaces.containsKey (placeName);
  }

  @Override
  protected void onServiceProviderSet ()
  {
    if (log.isDebugEnabled ())
    {
      log.debug ("Registering on event bus");
    }
    eventbus ().register (this);
  }
}

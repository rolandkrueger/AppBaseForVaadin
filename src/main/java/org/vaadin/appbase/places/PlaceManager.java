package org.vaadin.appbase.places;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.appbase.event.impl.places.PlaceRequestEvent;
import org.vaadin.appbase.service.AbstractUsesServiceProvider;

import com.google.common.eventbus.Subscribe;

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
@Configurable (preConstruction = true)
@Slf4j
public class PlaceManager extends AbstractUsesServiceProvider
{
  private Stack<AbstractPlace> activePlaces;

  public PlaceManager ()
  {
    activePlaces = new Stack<> ();
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
      log.debug ("Handling place request event " + place.getName ());
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

  @Override
  protected void onServiceProviderSet ()
  {
    if (log.isDebugEnabled ())
    {
      log.debug ("Registering on event bus");
    }
    eventbus ().register (this);
  }

  @Override
  public String toString ()
  {
    return "PlaceManager";
  }
}

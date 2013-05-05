package org.vaadin.appbase.event.impl.places;

import lombok.Getter;

import org.vaadin.appbase.event.Event;
import org.vaadin.appbase.places.AbstractPlace;

import com.google.common.base.Preconditions;

public class PlaceRequestEvent extends Event
{
  @Getter protected AbstractPlace place;

  public PlaceRequestEvent (Object source, AbstractPlace place)
  {
    super (source);
    Preconditions.checkNotNull (place);
    this.place = place;
  }

  @Override
  public String toString ()
  {
    return String.format ("[Place Request Event: %s]", place.getName ());
  }
}

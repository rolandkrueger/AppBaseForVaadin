package org.vaadin.appbase.places.testobjects;

import org.vaadin.appbase.places.AbstractPlace;

public class PlaceTestClass extends AbstractPlace
{
  private StringBuilder activePathBuilder;

  public PlaceTestClass (String name)
  {
    this (name, null);
  }

  public PlaceTestClass (String name, StringBuilder activePathBuilder)
  {
    super (name);
    this.activePathBuilder = activePathBuilder;
  }

  @Override
  public void activate ()
  {
    if (activePathBuilder != null)
    {
      activePathBuilder.append (getName ()).append ("/");
    }
  }
}

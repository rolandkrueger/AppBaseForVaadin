package org.vaadin.appbase.places;

import org.testng.annotations.Test;
import org.vaadin.appbase.places.testobjects.PlaceTestClass;

/**
 * Superklasse für alle Tests, die Places testen.
 * 
 * @author Roland Krüger
 */
public class AbstractPlaceTest
{
  @Test (expectedExceptions = IllegalArgumentException.class)
  public void testConstructor_NameIsNull ()
  {
    new PlaceTestClass (null);
  }

  @Test (expectedExceptions = IllegalArgumentException.class)
  public void testConstructor_NameIsEmpty ()
  {
    new PlaceTestClass ("");
  }

}

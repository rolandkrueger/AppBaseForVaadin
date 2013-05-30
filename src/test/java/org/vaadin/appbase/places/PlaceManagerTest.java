package org.vaadin.appbase.places;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.vaadin.appbase.event.impl.places.PlaceRequestEvent;
import org.vaadin.appbase.places.testobjects.PlaceTestClass;
import org.vaadin.appbase.testutils.TestUIServiceProvider;

public class PlaceManagerTest
{
  private PlaceManager testObj;

  @BeforeMethod
  public void setUp ()
  {
    testObj = new PlaceManager ();
    testObj.setUIServiceProvider (new TestUIServiceProvider ());
  }

  @Test
  public void testRegisterPlace ()
  {
    PlaceTestClass place = new PlaceTestClass ("place");
    testObj.registerPlace (place);
    assertEquals (testObj.getRegisteredPlace ("place"), place);
  }

  @Test (expectedExceptions = { IllegalArgumentException.class })
  public void testRegisterPlace_Fails ()
  {
    PlaceTestClass place1 = new PlaceTestClass ("place");
    PlaceTestClass place2 = new PlaceTestClass ("place");
    testObj.registerPlace (place1);
    testObj.registerPlace (place2);
  }

  @Test (expectedExceptions = { IllegalArgumentException.class })
  public void testGetRegisteredPlace_Fails ()
  {
    testObj.getRegisteredPlace ("Unregistered Place");
  }

  @Test (expectedExceptions = { IllegalArgumentException.class })
  public void testGetRegisteredPlace_FailsWithNullArgument ()
  {
    testObj.getRegisteredPlace (null);
  }

  @Test (expectedExceptions = { IllegalArgumentException.class })
  public void testRegisterPlace_FailsWithNullArgument ()
  {
    testObj.registerPlace (null);
  }

  @Test
  public void testIsPlaceRegistered ()
  {
    PlaceTestClass place = new PlaceTestClass ("place");
    testObj.registerPlace (place);
    assertTrue (testObj.isPlaceRegistered ("place"));
    assertFalse (testObj.isPlaceRegistered (null));
    assertFalse (testObj.isPlaceRegistered ("unregistered place"));
  }

  @Test
  public void testReceivePlaceRequestEvent ()
  {
    StringBuilder pathBuilder = new StringBuilder ();
    PlaceTestClass testPlace = new PlaceTestClass ("test", pathBuilder);
    PlaceTestClass parentPlace = new PlaceTestClass ("parent", pathBuilder);
    PlaceTestClass childPlace1 = new PlaceTestClass ("child-1", pathBuilder);
    PlaceTestClass childPlace2 = new PlaceTestClass ("child-2", pathBuilder);
    PlaceTestClass grandChildPlace1 = new PlaceTestClass ("grandchild-1", pathBuilder);
    PlaceTestClass grandChildPlace2 = new PlaceTestClass ("grandchild-2", pathBuilder);

    childPlace1.setDependentParentPlace (parentPlace);
    childPlace2.setDependentParentPlace (parentPlace);
    grandChildPlace1.setDependentParentPlace (childPlace1);
    grandChildPlace2.setDependentParentPlace (childPlace2);

    runPlaceRequest (pathBuilder, testPlace, "test/", testPlace);
    runPlaceRequest (pathBuilder, grandChildPlace1, "parent/child-1/grandchild-1/", parentPlace, childPlace1,
        grandChildPlace1);
    runPlaceRequest (pathBuilder, childPlace2, "child-2/", parentPlace, childPlace2);
    runPlaceRequest (pathBuilder, parentPlace, "", parentPlace);
    runPlaceRequest (pathBuilder, testPlace, "test/", testPlace);
  }

  private void runPlaceRequest (StringBuilder pathBuilder, AbstractPlace toPlace, String expectedActivationPath,
      AbstractPlace... expectedActivePlaces)
  {
    testObj.receivePlaceRequestEvent (new PlaceRequestEvent (null, toPlace));
    assertEquals (expectedActivationPath, pathBuilder.toString ());
    assertActivePlaces (testObj.getActivePlaces (), expectedActivePlaces);
    pathBuilder.setLength (0);
  }

  @Test (expectedExceptions = IllegalStateException.class)
  public void testSetUIServiceProvider_WithNull ()
  {
    testObj = new PlaceManager ();
    testObj.setUIServiceProvider (null);
  }

  private void assertActivePlaces (List<AbstractPlace> activePlaces, AbstractPlace... expectedPlaces)
  {
    assertEquals (activePlaces.size (), expectedPlaces.length,
        "Number of active places does not match expected number.");

    for (int i = 0; i < activePlaces.size (); ++i)
    {
      assertEquals (activePlaces.get (i), expectedPlaces[i]);
    }
  }
}

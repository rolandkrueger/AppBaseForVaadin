package org.vaadin.appbase.places;

import static org.testng.AssertJUnit.assertEquals;

import java.util.List;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.vaadin.appbase.event.impl.places.PlaceRequestEvent;
import org.vaadin.appbase.places.testobjects.PlaceTestClass;
import org.vaadin.appbase.testutils.TestUIServiceProvider;

public class PlaceManagerTest
{
  private PlaceManager testObj;

  @BeforeTest
  public void setUp ()
  {
    testObj = new PlaceManager ();
    testObj.setUIServiceProvider (new TestUIServiceProvider ());
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
    assertEquals ("Number of active places does not match expected number.", expectedPlaces.length,
        activePlaces.size ());

    for (int i = 0; i < activePlaces.size (); ++i)
    {
      assertEquals ("", expectedPlaces[i], activePlaces.get (i));
    }
  }
}

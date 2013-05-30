package org.vaadin.appbase.places;

import com.google.common.base.Preconditions;

/**
 * <p>
 * Ein Place ist die Abstraktion für eine einzelne Seite einer Multi-Page-Application.
 * Typischerweise gehört zu einem Place auch immer eine bestimmte URL, unter der der Place
 * aufgerufen werden kann.
 * </p>
 * <p>
 * Ein Place wird eindeutig über seinen Namen identifiziert. Jeder Place muss am
 * {@link PlaceManager} registriert werden, bevor er verwendet werden kann.
 * </p>
 */
public abstract class AbstractPlace
{
  private String        name;
  private AbstractPlace dependentParentPlace;

  public AbstractPlace (String name)
  {
    Preconditions.checkArgument (name != null && !"".equals (name.trim ()));
    this.name = name;
  }

  public abstract void activate ();

  /**
   * Setzt den Parent Place für diesen Place.
   * 
   * @param dependentParentPlace
   *          ein Parent Place oder <code>null</code>
   * @throws IllegalArgumentException
   *           wenn dieser Place selbst schon der Parent Place für den übergebenen Place ist
   */
  public void setDependentParentPlace (AbstractPlace dependentParentPlace)
  {
    if (dependentParentPlace != null && dependentParentPlace.getDependentParentPlace () == this) { throw new IllegalArgumentException (
        "This place is already the parent place of the given place. Cannot set cyclic parent relationship."); }
    this.dependentParentPlace = dependentParentPlace;
  }

  /**
   * Liefert den Parent Place für diesen Place.
   * 
   * @return den Parent Place oder <code>null</code> wenn ein solcher nicht definiert ist.
   */
  public AbstractPlace getDependentParentPlace ()
  {
    return dependentParentPlace;
  }

  /**
   * Liefert <code>true</code> falls ein Parent Place für diesen Place definiert wurde.
   */
  public boolean hasDependentParentPlace ()
  {
    return dependentParentPlace != null;
  }

  /**
   * Liefert den Namen des Places. Unter allen registrierten Places muss dieser Name eindeutig sein.
   */
  public String getName ()
  {
    return name;
  }

  @Override
  public int hashCode ()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode ());
    return result;
  }

  @Override
  public boolean equals (Object obj)
  {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass () != obj.getClass ()) return false;
    AbstractPlace other = (AbstractPlace) obj;
    if (name == null)
    {
      if (other.name != null) return false;
    } else if (!name.equals (other.name)) return false;
    return true;
  }

  @Override
  public String toString ()
  {
    return getClass ().getName () + " [" + name + "]";
  }
}

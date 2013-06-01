package org.vaadin.appbase.components;

import java.util.Arrays;
import java.util.Collection;

import com.google.common.base.Strings;
import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

public class HTMLListLabel extends Label
{
  private Property<String> dataSource;

  public HTMLListLabel ()
  {
    this (null);
  }

  public HTMLListLabel (Collection<String> content)
  {
    super ();
    setContentMode (ContentMode.HTML);
    dataSource = new ObjectProperty<String> (buildContents (content));
    setPropertyDataSource (dataSource);
  }

  public void setContent (Collection<String> content)
  {
    dataSource.setValue (buildContents (content));
  }

  @Override
  public void setValue (String newStringValue)
  {
    if (Strings.isNullOrEmpty (newStringValue)) { return; }
    setContent (Arrays.asList (newStringValue));
  }

  private String buildContents (Collection<String> content)
  {
    StringBuilder builder = new StringBuilder ("<ul>");
    if (content != null)
    {
      for (String errorMsg : content)
      {
        builder.append ("<li>").append (errorMsg).append ("</li>");
      }
    }
    builder.append ("</ul>");
    return builder.toString ();
  }
}

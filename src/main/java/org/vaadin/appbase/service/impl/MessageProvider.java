package org.vaadin.appbase.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.vaadin.appbase.service.IMessageProvider;
import org.vaadin.appbase.session.SessionContext;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MessageProvider implements IMessageProvider {
  private static final long serialVersionUID = 3500618248234078311L;

  @Autowired
  private SessionContext context;

  @Autowired
  private MessageSource messageSource;

  @Override
  public String getMessage(MessageSourceResolvable messageSourceResolvable) throws NoSuchMessageException {
    return messageSource.getMessage(messageSourceResolvable, context.getLocale());
  }

  @Override
  public String getMessage(String code, Object[] args) throws NoSuchMessageException {
    return messageSource.getMessage(code, args, context.getLocale());
  }

  @Override
  public String getMessage(String code, Object[] args, String defaultMessage) {
    return messageSource.getMessage(code, args, defaultMessage, context.getLocale());
  }

  public String getMessage(String code) throws NoSuchMessageException {
    return getMessage(code, null);
  }

}

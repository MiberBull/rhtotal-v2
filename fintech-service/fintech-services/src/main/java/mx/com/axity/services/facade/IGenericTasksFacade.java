package mx.com.axity.services.facade;

import mx.com.axity.commons.to.HeadersGenericTO;

public interface IGenericTasksFacade {
    HeadersGenericTO getHeader(String nameHeader);
}

package mx.com.axity.services.service;


import mx.com.axity.commons.to.SicoEmployeeSearchRequestTO;

import java.io.UnsupportedEncodingException;

public interface IPaysheetSicoService {

    String getUserDataByEmail(String email);

    String buildSearchUrl(SicoEmployeeSearchRequestTO searchData);

    String getUserDataByParams(String url);


}

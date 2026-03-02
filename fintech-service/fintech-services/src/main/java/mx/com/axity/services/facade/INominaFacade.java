package mx.com.axity.services.facade;

import mx.com.axity.commons.to.CustomFintechTO;
import mx.com.axity.commons.to.FintechTO;
import mx.com.axity.commons.to.SicoPaysheetEmployeeTO;

public interface INominaFacade {

    Object[] payrollInformation(int idUser);

    FintechTO payrollInformationUser(int idUser,Integer section) throws Exception;

    FintechTO payrollInformarionAdvanceUser( int idUser, Integer section );

    void saveFintechVeloCahs( CustomFintechTO customFintech );

    void saveFintechMyAdvanced( CustomFintechTO customFintech );

}

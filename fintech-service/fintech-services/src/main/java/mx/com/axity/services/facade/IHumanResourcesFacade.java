package mx.com.axity.services.facade;

import com.fasterxml.jackson.databind.JsonNode;
import mx.com.axity.commons.to.ActualPositionTO;

import java.util.List;
import java.util.Map;

public interface IHumanResourcesFacade {

    ActualPositionTO getActualPosition( long idUser );

    ActualPositionTO getPaymentDetailsByMount( long idUser,String mounth,String year );

    List<Map<String,String>> getCfdiByMount(long idUser, String mounth, String year );

}

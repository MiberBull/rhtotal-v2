package mx.com.axity.services.facade;

import mx.com.axity.commons.to.FintechMyAdvanceTO;

import java.util.List;

public interface IFintechFacade {

    List<FintechMyAdvanceTO> getPagedFintechMyAdvance(int page, String status);

}

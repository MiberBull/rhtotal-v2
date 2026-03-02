package mx.com.axity.services.facade;

import mx.com.axity.commons.to.FintechMyAdvanceTO;
import mx.com.axity.commons.to.FintechVeloCashTO;
import mx.com.axity.commons.to.ResponseSwapTO;
import mx.com.axity.model.FintechMyAdvanceDO;
import mx.com.axity.model.FintechVeloCashDO;

import java.time.LocalDateTime;
import java.util.List;

public interface ISchedulesEnviosSolPendFacade {

    ResponseSwapTO sendSolPendientes(List<FintechMyAdvanceTO> ids, List<FintechVeloCashTO> idsVeloCash);
    ResponseSwapTO sendSolApprovedWEBAdvance(FintechMyAdvanceDO ids);
    ResponseSwapTO sendSolApprovedWEBVeloCash(FintechVeloCashDO ids);

    List<FintechMyAdvanceTO> getSolicicitudesPendientesPayshetAdvanced(LocalDateTime nameParemeter);
    List <FintechVeloCashTO>  getSolicitudesPendientesFintechVeloCash(LocalDateTime nameParemeter);

}

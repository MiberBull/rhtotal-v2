package mx.com.axity.services.service;

import mx.com.axity.commons.to.FintechMyAdvanceTO;

import java.util.List;

public interface IFintechService {
    List<FintechMyAdvanceTO> getPagedFintechMyAdvance(int page, String status);
}

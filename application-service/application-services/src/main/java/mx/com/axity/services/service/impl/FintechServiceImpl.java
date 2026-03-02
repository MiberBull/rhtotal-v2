package mx.com.axity.services.service.impl;

import mx.com.axity.commons.to.FintechMyAdvanceTO;
import mx.com.axity.services.service.IFintechService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FintechServiceImpl implements IFintechService {
    @Override
    public List<FintechMyAdvanceTO> getPagedFintechMyAdvance(int page, String status) {
        return null;
    }
}

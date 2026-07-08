package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.to.FaqTO;
import mx.com.axity.model.FaqDO;
import mx.com.axity.services.impl.FaqServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FaqFacadeImpl {

    @Autowired private FaqServiceImpl faqService;

    public FaqTO create(FaqTO faqTO, String tenantId) {
        FaqDO d = toDO(faqTO);
        d.setTenantId(tenantId);
        return toTO(faqService.save(d));
    }

    public FaqTO getById(Long id) {
        return toTO(faqService.findById(id));
    }

    public List<FaqTO> getAll(String tenantId) {
        return faqService.findAll(tenantId).stream()
            .map(this::toTO).collect(Collectors.toList());
    }

    public List<FaqTO> getByCategory(String tenantId, String category) {
        return faqService.findByCategory(tenantId, category).stream()
            .map(this::toTO).collect(Collectors.toList());
    }

    public void delete(Long id) {
        faqService.delete(id);
    }

    private FaqTO toTO(FaqDO d) {
        FaqTO to = new FaqTO();
        to.setIdFaq(d.getIdFaq()); to.setTenantId(d.getTenantId());
        to.setDsCategory(d.getDsCategory()); to.setDsQuestion(d.getDsQuestion());
        to.setDsAnswer(d.getDsAnswer()); to.setNbOrder(d.getNbOrder());
        to.setFgActive(d.getFgActive());
        return to;
    }

    private FaqDO toDO(FaqTO to) {
        FaqDO d = new FaqDO();
        d.setDsCategory(to.getDsCategory()); d.setDsQuestion(to.getDsQuestion());
        d.setDsAnswer(to.getDsAnswer()); d.setNbOrder(to.getNbOrder());
        return d;
    }
}

package mx.com.axity.services.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.model.FaqDO;
import mx.com.axity.persistence.FaqDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FaqServiceImpl {

    @Autowired private FaqDAO faqDAO;

    @Transactional
    public FaqDO save(FaqDO faq) { return faqDAO.save(faq); }

    public FaqDO findById(Long id) {
        return faqDAO.findById(id).orElseThrow(() -> new BusinessException(404, "FAQ no encontrado: " + id));
    }

    public List<FaqDO> findAll(String tenantId) {
        return faqDAO.findAllByTenantIdAndFgActiveTrueOrderByNbOrderAsc(tenantId);
    }

    public List<FaqDO> findByCategory(String tenantId, String category) {
        return faqDAO.findAllByTenantIdAndDsCategoryAndFgActiveTrueOrderByNbOrderAsc(tenantId, category);
    }

    @Transactional
    public void delete(Long id) {
        FaqDO faq = findById(id);
        faq.setFgActive(false);
        faqDAO.save(faq);
    }
}

package mx.com.axity.services.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.model.MessageDO;
import mx.com.axity.persistence.MessageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl {

    @Autowired private MessageDAO messageDAO;

    @Transactional
    public MessageDO send(MessageDO message) {
        if (Boolean.TRUE.equals(message.getFgAnonymous())) {
            message.setIdEmployee(null);
        }
        return messageDAO.save(message);
    }

    public MessageDO findById(Long id) {
        return messageDAO.findById(id).orElseThrow(() -> new BusinessException(404, "Mensaje no encontrado: " + id));
    }

    public List<MessageDO> findByType(String tenantId, String type) {
        return messageDAO.findAllByTenantIdAndDsType(tenantId, type);
    }

    public List<MessageDO> findByEmployee(Long idEmployee, String tenantId) {
        return messageDAO.findAllByIdEmployeeAndTenantId(idEmployee, tenantId);
    }

    @Transactional
    public MessageDO respond(Long id, String response, String respondedBy) {
        MessageDO msg = findById(id);
        msg.setDsResponse(response);
        msg.setDsRespondedBy(respondedBy);
        msg.setDsStatus(Constants.MSG_RESPONDIDO);
        msg.setDtRespondedDate(LocalDateTime.now());
        return messageDAO.save(msg);
    }

    @Transactional
    public MessageDO markInReview(Long id) {
        MessageDO msg = findById(id);
        msg.setDsStatus(Constants.MSG_EN_REVISION);
        return messageDAO.save(msg);
    }

    @Transactional
    public MessageDO markAsRead(Long id) {
        MessageDO msg = findById(id);
        msg.setDsStatus("LEIDO");
        return messageDAO.save(msg);
    }

    @Transactional
    public MessageDO reply(Long id, String responseText, String respondedBy) {
        return respond(id, responseText, respondedBy);
    }
}

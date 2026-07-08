package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.to.MessageTO;
import mx.com.axity.model.MessageDO;
import mx.com.axity.services.impl.MessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageFacadeImpl {

    @Autowired private MessageServiceImpl messageService;

    public MessageTO send(MessageTO messageTO, String tenantId) {
        MessageDO d = toDO(messageTO);
        d.setTenantId(tenantId);
        return toTO(messageService.send(d));
    }

    public MessageTO getById(Long id) {
        return toTO(messageService.findById(id));
    }

    public List<MessageTO> getByType(String tenantId, String type) {
        return messageService.findByType(tenantId, type).stream()
            .map(this::toTO).collect(Collectors.toList());
    }

    public List<MessageTO> getByEmployee(Long idEmployee, String tenantId) {
        return messageService.findByEmployee(idEmployee, tenantId).stream()
            .map(this::toTO).collect(Collectors.toList());
    }

    public MessageTO markAsRead(Long id) {
        return toTO(messageService.markAsRead(id));
    }

    public MessageTO reply(Long id, String responseText, String respondedBy) {
        return toTO(messageService.reply(id, responseText, respondedBy));
    }

    private MessageTO toTO(MessageDO d) {
        MessageTO to = new MessageTO();
        to.setIdMessage(d.getIdMessage()); to.setTenantId(d.getTenantId());
        to.setIdEmployee(d.getIdEmployee()); to.setDsType(d.getDsType());
        to.setDsSubject(d.getDsSubject()); to.setDsContent(d.getDsContent());
        to.setFgAnonymous(d.getFgAnonymous()); to.setDsStatus(d.getDsStatus());
        to.setDsResponse(d.getDsResponse()); to.setDsRespondedBy(d.getDsRespondedBy());
        to.setDtRespondedDate(d.getDtRespondedDate()); to.setFgActive(d.getFgActive());
        return to;
    }

    private MessageDO toDO(MessageTO to) {
        MessageDO d = new MessageDO();
        d.setIdEmployee(to.getIdEmployee()); d.setDsType(to.getDsType());
        d.setDsSubject(to.getDsSubject()); d.setDsContent(to.getDsContent());
        d.setFgAnonymous(to.getFgAnonymous());
        return d;
    }
}

package mx.com.axity.services.facade;

import mx.com.axity.commons.to.*;
import mx.com.axity.commons.to.totree.BenefitsNotificationsTO;
import mx.com.axity.commons.to.totree.TreeClientTO;
import mx.com.axity.commons.to.totree.TreeEmployeeTO;
import mx.com.axity.commons.to.totree.TreeProjectTO;

import java.util.List;

public interface INotificationAssignmentFacade {
    BenefitsNotificationsTO getAssignmentBenefitsNotifications(Long idNotification, String typeNotification);
    List<TreeClientTO> listAssignmentBenefitsNotifications(List<TreeClientTO> listClients, Long idNotification, String typeNotification);
    List<TreeClientTO> filteredByCustomers(List<CustomerTO> allClients,Long idNotification,String typeNotification);
    List<TreeEmployeeTO> filteredByEmployee(Long idProject, Long idNotification, String typeNotification);
    Boolean saveAssignmentBenefitsNotifications(BenefitsNotificationsTO notificationsTree);
    List<TreeProjectTO> getProjects(Long idClient);
    List<TreeEmployeeTO> getEmployee(Long idProject);

}

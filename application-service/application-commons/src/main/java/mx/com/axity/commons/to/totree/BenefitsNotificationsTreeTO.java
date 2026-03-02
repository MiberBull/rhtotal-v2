package mx.com.axity.commons.to.totree;

import mx.com.axity.commons.to.NotificationTO;

public class BenefitsNotificationsTreeTO{
    NotificationTO notificationTO;
    BenefitsNotificationsTO benefitsNotificationsTO;

    public BenefitsNotificationsTO getBenefitsNotificationsTO() {
        return benefitsNotificationsTO;
    }

    public void setBenefitsNotificationsTO(BenefitsNotificationsTO benefitsNotificationsTO) {
        this.benefitsNotificationsTO = benefitsNotificationsTO;
    }

    public NotificationTO getNotificationTO() {
        return notificationTO;
    }

    public void setNotificationTO(NotificationTO notificationTO) {
        this.notificationTO = notificationTO;
    }
}

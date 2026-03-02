package mx.com.axity.commons.to.totree;

import mx.com.axity.commons.to.BannerTO;
import mx.com.axity.commons.to.ImageBannerTO;

import java.io.Serializable;
import java.util.List;

public class BenefitsBannersTreeTO implements Serializable {
    private List<ImageBannerTO> images;
    private BannerTO notificationTO;
    private BenefitsNotificationsTO benefitsNotificationsTO;

    public BannerTO getNotificationTO() {
        return notificationTO;
    }

    public void setNotificationTO(BannerTO notificationTO) {
        this.notificationTO = notificationTO;
    }

    public BenefitsNotificationsTO getBenefitsNotificationsTO() {
        return benefitsNotificationsTO;
    }

    public void setBenefitsNotificationsTO(BenefitsNotificationsTO benefitsNotificationsTO) {
        this.benefitsNotificationsTO = benefitsNotificationsTO;
    }
    public List<ImageBannerTO> getImages() {
        return images;
    }

    public void setImages(List<ImageBannerTO> images) {
        this.images = images;
    }
}

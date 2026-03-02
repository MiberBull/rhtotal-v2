package mx.com.axity.commons.to.totree;

import mx.com.axity.commons.to.DiscountTO;
import mx.com.axity.commons.to.ImageDiscountTO;

import java.io.Serializable;
import java.util.List;

public class BenefitsDiscountTreeTO implements Serializable {

    private List<ImageDiscountTO> images;
    private DiscountTO discount;
    private BenefitsNotificationsTO benefitsNotificationsTO;

    public List<ImageDiscountTO> getImages() {
        return images;
    }

    public void setImages(List<ImageDiscountTO> images) {
        this.images = images;
    }

    public DiscountTO getDiscount() {
        return discount;
    }

    public void setDiscount(DiscountTO discount) {
        this.discount = discount;
    }

    public BenefitsNotificationsTO getBenefitsNotificationsTO() {
        return benefitsNotificationsTO;
    }

    public void setBenefitsNotificationsTO(BenefitsNotificationsTO benefitsNotificationsTO) {
        this.benefitsNotificationsTO = benefitsNotificationsTO;
    }
}

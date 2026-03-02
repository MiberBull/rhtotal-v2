package mx.com.axity.commons.to.totree;

import mx.com.axity.commons.to.DiscountTO;
import mx.com.axity.commons.to.ImageDiscountTO;

import java.io.Serializable;
import java.util.List;

public class DiscountImageTO implements Serializable {
    DiscountTO discount;
    List<ImageDiscountTO> imageDiscountTO;

    public DiscountTO getDiscount() {
        return discount;
    }

    public void setDiscount(DiscountTO discount) {
        this.discount = discount;
    }

    public List<ImageDiscountTO> getImageDiscountTO() {
        return imageDiscountTO;
    }

    public void setImageDiscountTO(List<ImageDiscountTO> imageDiscountTO) {
        this.imageDiscountTO = imageDiscountTO;
    }
}

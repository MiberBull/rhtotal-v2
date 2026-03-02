package mx.com.axity.commons.to;

import java.io.Serializable;
import java.util.List;

public class BannerImageTO implements Serializable {

    private BannerTO bannerTO;
    private List<ImageBannerTO> images;

    public BannerTO getBannerTO() {
        return bannerTO;
    }

    public void setBannerTO(BannerTO bannerTO) {
        this.bannerTO = bannerTO;
    }

    public List<ImageBannerTO> getImages() {
        return images;
    }

    public void setImages(List<ImageBannerTO> images) {
        this.images = images;
    }
}

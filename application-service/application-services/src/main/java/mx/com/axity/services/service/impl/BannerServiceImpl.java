package mx.com.axity.services.service.impl;

import mx.com.axity.commons.to.BannerImageTO;
import mx.com.axity.commons.to.BannerTO;
import mx.com.axity.commons.to.totree.CountRowTO;
import mx.com.axity.commons.to.ImageBannerTO;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.model.BannerDO;
import mx.com.axity.model.ImageDO;
import mx.com.axity.persistence.BannerDAO;
import mx.com.axity.persistence.ImageDAO;
import mx.com.axity.services.service.IBannerService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import static mx.com.axity.commons.util.Constants.*;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


@Service
public class BannerServiceImpl implements IBannerService {
    @Autowired
    BannerDAO bannerDAO;

    @Autowired
    ImageDAO imageDAO;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CountRowTO getNumberRow(String title, String autor, String startDate, String enddate) {
        return new CountRowTO(this.bannerDAO.getNumberRow(CADENA_VACIA.equals(title) ? null : title, CADENA_VACIA.equals(autor) ? null : autor,
                CADENA_VACIA.equals(startDate) ? null : LocalDateTime.ofInstant(Instant.parse(startDate), ZoneId.of(ZoneOffset.UTC.getId())),
                CADENA_VACIA.equals(enddate) ? null : LocalDateTime.ofInstant(Instant.parse(enddate), ZoneId.of(ZoneOffset.UTC.getId())).plusDays(1)));
    }

    @Override
    public BannerImageTO getBanner(int idBanner) {
        BannerImageTO bannerImageTO = new BannerImageTO();
        var banner = modelMapper.map(this.bannerDAO.findById((long) idBanner).get(), BannerTO.class);
        bannerImageTO.setBannerTO(banner);
        bannerImageTO.setImages(this.modelMapper.map(this.imageDAO.findAllByIdBanner(banner.getIdBanner()),new TypeToken<List<ImageBannerTO>>(){}.getType()));
        return bannerImageTO;
    }

    @Override
    public BannerDO saveOrUpdateBanner(BannerTO banner) {
        if (banner.getIdBanner() == null) {
            banner.setCreationDate(LocalDateTime.now());
            banner.setLastModification(LocalDateTime.now());
            banner.setActive(Boolean.TRUE);
            return this.bannerDAO.save(this.modelMapper.map(banner, BannerDO.class));
        }
        banner.setCreationDate(banner.getCreationDate());
        banner.setLastModification(LocalDateTime.now());
        banner.setActive(Boolean.TRUE);
        return this.bannerDAO.save(this.modelMapper.map(banner, BannerDO.class));

    }

    @Override
    public void saveOrUpdateImageBanner(List<ImageBannerTO> image) {
        var listImage = (List<ImageDO>) this.modelMapper.map(image, new TypeToken<List<ImageDO>>() {
        }.getType());
        this.imageDAO.saveAll(listImage);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BannerTO> getPagedBanner(int page, String title, String autor, String startDate, String enddate) {

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        var allByOrderByLastModificationAsc = this.bannerDAO.findAllByOrderByLastModificationDesc(PageRequest.of(page, Constants.LIMIT_PAGE), CADENA_VACIA.equals(title) ? null : title, CADENA_VACIA.equals(autor) ? null : autor,
                CADENA_VACIA.equals(startDate) ? null :  LocalDate.parse(startDate, inputFormatter).atStartOfDay(),
                CADENA_VACIA.equals(enddate) ? null : LocalDate.parse(enddate, inputFormatter).atStartOfDay().plus(1, ChronoUnit.DAYS));
        allByOrderByLastModificationAsc.getContent().stream().map(b -> {
            b.setStatus(b.getStatus().equals(BANNERS_STATUS_ACTIVE) ? BANNERS_STATUS_ACTIVE_VIEW : b.getStatus().equals(BANNERS_STATUS_INACTIVO) ? BANNERS_STATUS_INACTIVO_VIEW : BANNERS_SIN_STATUS_VIEW);
            return b;
        }).collect(Collectors.toList());
        return (List<BannerTO>) this.modelMapper.map(allByOrderByLastModificationAsc.getContent(), new TypeToken<List<BannerTO>>() {
        }.getType());
    }

    @Override
    public List<String> getImagesBannerForMobile(int idUser) {
        return this.imageDAO.findImagesBannerByIdUser(PageRequest.of (Constants.PAGE_FOR_IMAGE,Constants.NUMBER_IMAGES_SHOW),(long)idUser,LocalDate.now(),LocalDateTime.now()).stream().collect(Collectors.toList());
    }

}

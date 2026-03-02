package mx.com.axity.services.service.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.SicoEmployeeSearchRequestTO;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.services.client.SicoAdapter;
import mx.com.axity.services.service.IPaysheetSicoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class PaysheetSicoServiceImpl implements IPaysheetSicoService {

    static final Logger LOG = LogManager.getLogger(PaysheetSicoServiceImpl.class);

    private static final String lastNameKey = "apellidoPaterno";
    private static final String lastMotherNameKey = "apellidoMaterno";
    private static final String namesKey = "nombres";
    private static final String birthdayKey = "fechaNacimiento";
    private static final String pageKey = "page";
    private static final String resultSizeListKey = "per_page";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");


    @Autowired
    SicoAdapter sicoAdapter;


    @Override
    public String getUserDataByEmail(String email) {

        String data = sicoAdapter.get(
                String.format(Constants.SICO_EMPLOYEE_SEARCH_BY_EMAIL_ENDPOINT, email)
        );

        return data;
    }

    @Override
    public String buildSearchUrl(SicoEmployeeSearchRequestTO searchData) {

        String url = "";
        String partialNameConstructionUri = "";


        try {

            boolean isMinimunDataToSearch = false;

            if (searchData.getNames().size() == 0) {
                throw new Exception(Constants.ERROR_NAMES_SHOULD_NOT_BE_EMPTY);
            }

            if (searchData.getLastMotherName() == null && searchData.getLastName() == null) {
                isMinimunDataToSearch = true;
            }

            if (isMinimunDataToSearch) {
                throw new Exception(Constants.ERROR_LAST_NAMES_BOTH_SHOULD_NOT_BE_EMPTY);
            }


            for (int i = 0; i < searchData.getNames().size(); i++) {
                String currentName = URLEncoder.encode(
                        searchData.getNames().get(i).toLowerCase(),
                        StandardCharsets.UTF_8.toString()
                );
                if (i == (searchData.getNames().size() - 1)) {
                    partialNameConstructionUri = partialNameConstructionUri + currentName;
                } else {
                    partialNameConstructionUri = partialNameConstructionUri + currentName + "+";
                }
            }

            url = String.format(
                    "%s?%s=%s",
                    Constants.SICO_EMPLOYEE_SEARCH_BY_PARAMS_ENDPOINT,
                    namesKey,
                    partialNameConstructionUri
            );

            if (searchData.getLastName() != null) {
                url = url + String.format(
                        "&%s=%s",
                        lastNameKey,
                        URLEncoder.encode(searchData.getLastName().toLowerCase(), StandardCharsets.UTF_8.toString())
                );
            }

            if (searchData.getLastMotherName() != null) {
                url = url + String.format(
                        "&%s=%s",
                        lastMotherNameKey,
                        URLEncoder.encode(searchData.getLastMotherName().toLowerCase(), StandardCharsets.UTF_8.toString())
                );
            }

            if (searchData.getBirthday() != null) {
                LocalDate parsedBirthDay = LocalDate.parse(
                        searchData.getBirthday(),
                        formatter
                );

                url = url + String.format(
                        "&%s=%s",
                        birthdayKey,
                        parsedBirthDay.format(DateTimeFormatter.ofPattern(
                                "dd/MM/yyyy"))
                );
            }
            return url;
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public String getUserDataByParams(String url) {
        String data = sicoAdapter.get(url);

        return data;
    }

}

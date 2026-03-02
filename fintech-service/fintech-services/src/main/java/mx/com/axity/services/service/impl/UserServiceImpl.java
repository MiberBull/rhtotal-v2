package mx.com.axity.services.service.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.UserTO;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.model.EmployeeDO;
import mx.com.axity.persistence.EmployeeDAO;
import mx.com.axity.persistence.UserDAO;
import mx.com.axity.services.service.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    static final Logger LOG = LogManager.getLogger(FintechServiceImpl.class);

    @Autowired
    public UserDAO userDAO;

    @Autowired
    public EmployeeDAO employeeDAO;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public UserTO getUserById(Long idUser) {
        var userById = this.userDAO.findById(idUser);
        if( null != userById ) {
            return this.modelMapper.map(userById.get(),UserTO.class);
        }
        return null;
    }

    @Override
    public Object[] getDataForQuerySico(Long idUser) {
            var data = this.userDAO.getDataByIdUser(idUser);
            return data;
    }

    @Override
    public String buildUrlForUser(Object[] data,int type) {
        StringBuilder str = new StringBuilder();
        str.append(Constants.URL_API_SICO);
        str.append(Constants.PATH_GET_INFOMATION);
        str.append(Constants.PARAM_NAME);
        str.append(String.valueOf(data[1]).toUpperCase().replace(" ","+") );
        str.append(Constants.PARAM_LAST_NAME);
        str.append(String.valueOf(data[2]).toUpperCase().replace(" ","+"));
        str.append(Constants.PARAM_RFC);
        str.append(data[5]);
        str.append(Constants.PARAM_TYPE);
        str.append(String.valueOf(type));

        LOG.info("URL construida buildUrlForUser "+str.toString());
        return str.toString();
    }

    @Override
    public EmployeeDO getEmployeByIdUser(long idUser) {
        return this.employeeDAO.getEmployeeByIdUser( idUser );
    }

    @Override
    public String buildUrlForUserWithMonth(Object[] data, int type, String mounth) {
        StringBuilder str = new StringBuilder();
        str.append(Constants.URL_API_SICO);
        str.append(Constants.PATH_GET_INFOMATION);
        str.append(Constants.PARAM_NAME);
        str.append(String.valueOf(data[1]).toUpperCase().replace(" ","+"));
        str.append(Constants.PARAM_LAST_NAME);
        str.append(String.valueOf(data[2]).toUpperCase().replaceAll(" ","+"));
        str.append(Constants.PARAM_RFC);
        str.append(data[5]);
        str.append(Constants.PARAM_TYPE);
        str.append(String.valueOf(type));
        str.append(Constants.PARAM_MOUNTH);
        str.append(mounth);

        LOG.info("URL construida buildUrlForUserWithMonth "+str.toString());
        return str.toString();
    }

    @Override
    public Long getIdEmployeeByIdUser(Long id) {
        return this.employeeDAO.getIdEmployeeByIdUser(id);
    }
}

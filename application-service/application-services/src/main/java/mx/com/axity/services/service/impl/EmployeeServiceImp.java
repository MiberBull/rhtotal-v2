package mx.com.axity.services.service.impl;

import mx.com.axity.commons.to.*;
import mx.com.axity.model.EmployeeComplementaryDO;
import mx.com.axity.persistence.*;
import mx.com.axity.services.service.IEmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImp implements IEmployeeService {

    @Autowired
    EmployeeDAO employeeDao;

    @Autowired
    EmployeeComplementaryDAO employeeComplementaryDAO;

    @Autowired
    EmployeeAddressDAO employeeAddressDAO;

    @Autowired
    AsignationDataDAO asignationDAO;

    @Autowired
    CompesationPackageDAO compensationPackageDAO;

    @Autowired
    ContratingDataDAO contratingDataDAO;

    @Autowired
    CivilStatusDAO civilStatusDAO;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserDAO userDAO;

    final static Logger LOG = LogManager.getLogger(EmployeeServiceImp.class);

    /////////////
    @Override
    public List<CivilStatusTO> getCivilStatus() {
        return (List<CivilStatusTO>) this.modelMapper.map(this.civilStatusDAO.getCivilStatusOrderByName(), new TypeToken<List<CivilStatusTO>>() {
        }.getType());
    }


    //////////
    @Override
    public EmployeeAddressTO getEmployeeAdressByIdUser(Long idUser)
    {
        var employeeAdressByIdUser = this.employeeAddressDAO.getEmployeeAdressByIdEmployee(idUser);
        if(null !=employeeAdressByIdUser)
        {
            return this.modelMapper.map(employeeAdressByIdUser,EmployeeAddressTO.class);
        }
       return null;
    }

    ///////////////////////////
    @Override
    public ContratingDataTO getContratingDataByIdUser(Long idUser)
    {
        var contratingDataByIdUser = this.contratingDataDAO.getEmployeeContratingDataByIdUser(idUser);
        if (null != contratingDataByIdUser){
            return this.modelMapper.map(contratingDataByIdUser,ContratingDataTO.class);
        }
        return null;
    }

    ///////////////////
    @Override
    public List<CompensationPackageTO> getEmployeeCompensationByIdUser(Long idUser)
    {
        var employeeCompensationByIdUser = this.compensationPackageDAO.getCompesationPackageByIdUser(idUser);
        if (null != employeeCompensationByIdUser){
            return this.modelMapper.map(employeeCompensationByIdUser,new TypeToken<List<CompensationPackageTO>>(){}.getType());
        }
        return null ;
    }

    ///////
    @Override
    public AsignationDataTO  getEmployeeAsignationByIdUser(Long idUser)
    {
        var employeeAsignationDataByIdUser = this.asignationDAO.getAsignationDataByIdUser(idUser);
        if (null != employeeAsignationDataByIdUser){
            return this.modelMapper.map(employeeAsignationDataByIdUser,AsignationDataTO.class);
        }
        return null ;
    }

    /////////////////////
    @Override
    public List<EmployeeComplementaryTO> getUserData() {
       var result =  this.employeeComplementaryDAO.findEmployeeByClientProyectCurp();
       modelMapper.getConfiguration().setAmbiguityIgnored(true);
     var resultMapper = (List<EmployeeComplementaryTO>) this.modelMapper.map(result,new TypeToken<List<EmployeeComplementaryTO>>(){}.getType());

      return resultMapper;

    }

}
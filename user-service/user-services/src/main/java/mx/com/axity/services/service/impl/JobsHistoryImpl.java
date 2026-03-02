package mx.com.axity.services.service.impl;

import mx.com.axity.commons.to.HistoryEmployeeTO;
import mx.com.axity.commons.to.JobsHistoryTO;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.model.HistoryEmployeeDO;
import mx.com.axity.model.JobsHistoryDO;
import mx.com.axity.persistence.HistoryEmployeeDAO;
import mx.com.axity.persistence.JobsHistoryDAO;
import mx.com.axity.services.service.IJobsHistoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class JobsHistoryImpl implements IJobsHistoryService {

    static final Logger LOG = LogManager.getLogger(JobsHistoryImpl.class);

    @Autowired
    JobsHistoryDAO jobsHistoryDAO;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public void saveOrUpdateHistoryEmployee(List<JobsHistoryTO> listJobHistoryEmployee) {

        listJobHistoryEmployee.forEach( jobHistoryEmployee -> {

            if( jobHistoryEmployee.getIdJobHistory() == null  ) {
                jobHistoryEmployee.setCreationDate(LocalDateTime.now());
                jobHistoryEmployee.setLastModification(LocalDateTime.now());
                jobHistoryEmployee.setActive(Boolean.TRUE);
            }else {
                jobHistoryEmployee.setCreationDate(jobHistoryEmployee.getCreationDate());
                jobHistoryEmployee.setLastModification(LocalDateTime.now());
                jobHistoryEmployee.setActive(Boolean.TRUE);
            }

            this.jobsHistoryDAO.save( this.modelMapper.map( jobHistoryEmployee, JobsHistoryDO.class) );

        });

    }

    @Override
    public List<JobsHistoryTO> getJobsHistoryByIdUser(Long idUser) {
        return this.modelMapper.map( this.jobsHistoryDAO.getJobsHistoryByIdUser(idUser),new TypeToken<List<JobsHistoryTO>>(){}.getType() );
    }

    @Override
    public void deleteJobsEmployee(List<Map<String, Integer>> idJobs) {
        for ( Map<String,Integer> a : idJobs) {
            LOG.info("ID JOB A ELIMINAR " + String.valueOf(a.get(Constants.KEY_ID_JOB)));
            this.jobsHistoryDAO.deleteById((long)a.get(Constants.KEY_ID_JOB));
        }
    }
}

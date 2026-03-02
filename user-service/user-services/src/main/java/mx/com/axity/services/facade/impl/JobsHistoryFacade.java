package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.JobsHistoryTO;
import mx.com.axity.services.facade.IJobsHistoryFacade;
import mx.com.axity.services.service.IJobsHistoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class JobsHistoryFacade implements IJobsHistoryFacade {

    static final Logger LOG = LogManager.getLogger(JobsHistoryFacade.class);

    @Autowired
    IJobsHistoryService jobsHistoryService;

    @Override
    public void saveOrUpdateHistoryEmployee(List<JobsHistoryTO> jobsHistoryTO) {
        try {
            this.jobsHistoryService.saveOrUpdateHistoryEmployee(jobsHistoryTO);
        }catch (BusinessException e) {
            throw new BusinessException(e.getMessage(),e);
        }
    }

    @Override
    public List<JobsHistoryTO> getJobsHistoryByIdUser(Long idUser) {
        try {
            return this.jobsHistoryService.getJobsHistoryByIdUser(idUser);
        } catch ( Exception e ) {
            throw  new BusinessException(e.getMessage(),e);
        }
    }

    @Override
    public void deleteJobsEmployee(List<Map<String, Integer>> idJobs) {
        try {
            this.jobsHistoryService.deleteJobsEmployee(idJobs);
        }catch ( Exception e ) {
            LOG.info("ERROR AL ELIMINAR EL PUESTO");
            throw new BusinessException(e.getMessage(),e);
        }
    }
}



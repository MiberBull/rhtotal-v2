package mx.com.axity.services.service;

import mx.com.axity.commons.to.HistoryEmployeeTO;
import mx.com.axity.commons.to.JobsHistoryTO;

import java.util.List;
import java.util.Map;

public interface IJobsHistoryService {

    void saveOrUpdateHistoryEmployee(List<JobsHistoryTO> jobHistoryEmployee);

    List<JobsHistoryTO> getJobsHistoryByIdUser( Long idUser );

    void deleteJobsEmployee(List<Map<String,Integer>> idJobs);

}

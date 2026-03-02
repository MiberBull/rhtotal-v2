package mx.com.axity.services.facade;

import mx.com.axity.commons.to.JobsHistoryTO;

import java.util.List;
import java.util.Map;

public interface IJobsHistoryFacade {

    void saveOrUpdateHistoryEmployee(List<JobsHistoryTO> jobsHistoryTO);

    List<JobsHistoryTO> getJobsHistoryByIdUser(Long idUser);

    void deleteJobsEmployee(List<Map<String, Integer>> idJobs);

}

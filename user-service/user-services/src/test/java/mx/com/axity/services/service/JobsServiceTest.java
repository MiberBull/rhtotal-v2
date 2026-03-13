package mx.com.axity.services.service;

import mx.com.axity.commons.to.JobsHistoryTO;
import mx.com.axity.services.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JobsServiceTest extends BaseTest {

    @Test
    public void should_save_one_item() {

        List<JobsHistoryTO> list = new ArrayList<>();

        JobsHistoryTO jobsHistoryTO = new JobsHistoryTO();

        jobsHistoryTO.setEmployeePosition("Developer");
        jobsHistoryTO.setCompany("Axity");
        jobsHistoryTO.setBossName("Urban");
        jobsHistoryTO.setBossEmail("urbano.ceron@axity.com");
        jobsHistoryTO.setBossTelephone("55-69795191");
        jobsHistoryTO.setAssigmentDtartDate(LocalDateTime.now());
        jobsHistoryTO.setAssigmentEndDate(LocalDateTime.now());
        jobsHistoryTO.setQtSalary(1919.12);
        jobsHistoryTO.setAssignmentEmail("urbano.ceron@axity.com");
        jobsHistoryTO.setProfessionalResume(":D");
        jobsHistoryTO.setLastUserModifier("Urbano");
        jobsHistoryTO.setLastModification(LocalDateTime.now());
        jobsHistoryTO.setCreationUser("Urban");
        jobsHistoryTO.setCreationDate( LocalDateTime.now() );
        jobsHistoryTO.setActive(true);

        list.add(jobsHistoryTO);

        this.jobsHistoryServiceTest.saveOrUpdateHistoryEmployee(list);
        var isSave = this.jobsHistoryDAOTest.findById((long)1);
        Assertions.assertNotNull(isSave);

    }

}

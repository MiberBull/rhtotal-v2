package mx.com.axity.web;

import mx.com.axity.services.facade.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest
@ContextConfiguration(classes = {Application.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class BaseTest {

    @Autowired
    public TestEntityManager entityManager;

    @Autowired
    public IClienteFacade  clienteFacadeTest;

    @Autowired
    public IBannerFacade bannerFacadeTest;

    @Autowired
    public IDiscountFacade discountFacadeTest;

    @Autowired
    public IGenericTasksFacade genericTasksFacadeTest;

    @Autowired
    public IInsuranceFacade insuranceFacadeTest;

    @Autowired
    public INotificationFacade notificationFacadeTest;

    @Autowired
    public INotificationAssignmentFacade notificationAssignmentFacade;

    @Autowired
    public ICompanyInformationFacade companyinformationFacadeTest;
}

package mx.com.axity.services;

import mx.com.axity.services.service.*;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
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
    public IFintechService fintechService;

    @Autowired
    public IUserService userService;

    @Autowired
    public IPaysheetRequestService paysheetRequestService;

    @Autowired
    public ILogsResponseSicoService logsResponseSicoService;

    @Autowired
    public IParameterService parameterService;

    @Autowired
    public IFintechVeloCahsService fintechVeloCahsService;

}

package mx.com.axity.web;

import mx.com.axity.services.facade.IEmailFacade;
import mx.com.axity.services.facade.ILoginFacade;
import mx.com.axity.services.facade.IParameterFacade;
import mx.com.axity.services.facade.IRolUserFacade;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@SpringBootTest
@ContextConfiguration(classes = {Application.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class BaseTest {

    @Autowired
    public TestEntityManager entityManager;


    @Autowired
    public IParameterFacade parameterFacade;

    @Autowired
    public ILoginFacade loginFacade;

    @Autowired
    public IRolUserFacade rolUserFacade;

    @Autowired
    public IEmailFacade emailFacade;

    @Autowired
    JavaMailSender javaMailSender;
}

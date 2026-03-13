package mx.com.axity.services.service;

import mx.com.axity.commons.to.RoleCompoundTO;
import mx.com.axity.commons.to.RolesUserTO;
import mx.com.axity.services.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;


public class RolUserServiceTest extends BaseTest {

    @Test
    public void get_Correct_Role_Test() {
        var testRoleTO = new RoleCompoundTO<RolesUserTO>();
        var toList = new ArrayList<RolesUserTO>();
        var testRole = new RolesUserTO();
        testRole.setIdRolAssig((long) 1);
        toList.add(testRole);
        testRoleTO.setRoleList(toList);
        var testGetRole = this.rolUserServise.getRole(testRoleTO);
        Assertions.assertNotNull(testGetRole);
    }

    @Test
    public void get_error_Role_Test() {
        this.rolUserServise.getRole(null);
    }


    @Test
    public void get_All_Catalogue_Test() {
        var testGetAllCatalogue = this.rolUserServise.getAllCatalogue();
        Assertions.assertNotNull(testGetAllCatalogue);
    }

    @Test
    public void get_Correct_Paged_Role_Test() {
        var testCorrectPagedRole = this.rolUserServise.getPagedRole(0);
        Assertions.assertNotNull(testCorrectPagedRole);
    }

    @Test
    public void get_Number_Row_Test() {
        var testCorrectNumberRow = this.rolUserServise.getNumberRow();
        Assertions.assertNotNull(testCorrectNumberRow);
    }

    @Test
    public void save_Correct_Role_Test() {
        var testCorrectIsSave = this.rolUserServise.saveOrUpdateRole(new RolesUserTO());
        Assertions.assertTrue(testCorrectIsSave);
    }

    @Test
    public void save_Error_Role_Test() {
        this.rolUserServise.saveOrUpdateRole(null);
    }

    @Test
    public void update_Correct_Role_Test() {
        var testCorrectUpdateRole = new RolesUserTO();
        testCorrectUpdateRole.setIdRolAssig(2L);
        var testCorrectIsUpdate = this.rolUserServise.saveOrUpdateRole(testCorrectUpdateRole);
        Assertions.assertNotNull(testCorrectIsUpdate);
    }

}

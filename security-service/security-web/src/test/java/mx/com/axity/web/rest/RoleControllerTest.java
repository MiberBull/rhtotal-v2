package mx.com.axity.web.rest;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.RoleCompoundTO;
import mx.com.axity.commons.to.RolesUserTO;
import mx.com.axity.web.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class RoleControllerTest extends BaseTest {

    @Test
    public void get_Correct_Role_Test() {
        var testRoleTO = new RoleCompoundTO<RolesUserTO>();
        var toList = new ArrayList<RolesUserTO>();
        var testRole = new RolesUserTO();
        testRole.setIdRolAssig((long) 1);
        toList.add(testRole);
        testRoleTO.setRoleList(toList);
        var testGetRole = this.rolUserFacade.getRole(testRoleTO);
        Assertions.assertNotNull(testGetRole);
    }

    @Test
    public void get_error_Role_Test() {
        this.rolUserFacade.getRole(null);
    }

    @Test
    public void get_Number_Row_Test() {
        var testCorrectNumberRow = this.rolUserFacade.getNumberRow();
        Assertions.assertNotNull(testCorrectNumberRow);
    }

    @Test
    public void save_Correct_Role_Test() {
       var testCorrectSave = new RoleCompoundTO<RolesUserTO>();
        var testListRole = new ArrayList<RolesUserTO>();
        var testRole = new RolesUserTO();
        testRole.setIdRolAssig((long) 1);
        testListRole.add(testRole);
        testCorrectSave.setRoleList(testListRole);
        var testCorrectIsSave = this.rolUserFacade.saveOrUpdateRole(testCorrectSave);
        Assertions.assertTrue(testCorrectIsSave);
    }

    @Test
    public void save_Error_Role_Test() {
        this.rolUserFacade.saveOrUpdateRole(null);
    }

    @Test
    public void update_Correct_Role_Test() {
        var testListRole= new ArrayList<RolesUserTO>();
        var testCorrectUpdateRole = new RolesUserTO();
        testCorrectUpdateRole.setIdRolAssig(2L);
        testListRole.add(testCorrectUpdateRole);
        var testCorrectUpdRoleCompound = new RoleCompoundTO<RolesUserTO>();
        testCorrectUpdRoleCompound.setRoleList(testListRole);
        var testCorrectIsUpdate = this.rolUserFacade.saveOrUpdateRole(testCorrectUpdRoleCompound);
        Assertions.assertNotNull(testCorrectIsUpdate);
    }
    @Test
    public void get_Correct_Paged_Role_Test() {
        var testCorrectPagedRole = this.rolUserFacade.getPagedRole(0);
        Assertions.assertNotNull(testCorrectPagedRole);
    }

    @Test
    public void get_All_Catalogue_Test() {
        var testGetAllCatalogue = this.rolUserFacade.getAllCatalogue();
        Assertions.assertNotNull(testGetAllCatalogue);
    }


}

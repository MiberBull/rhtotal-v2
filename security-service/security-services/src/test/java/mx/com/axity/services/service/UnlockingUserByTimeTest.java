package mx.com.axity.services.service;

import mx.com.axity.services.BaseTest;
import org.junit.Assert;
import org.junit.Test;

public class UnlockingUserByTimeTest extends BaseTest {

    @Test(expected = Exception.class)
    public void getParamIsNullInBDTest() {
        this.parameterService.getParameter(null);
    }


    @Test
    public void get_Param_retutn_Correctly(){
        var timeUnLock = this.parameterService.getParameter("timeUnlock");
        Assert.assertNotNull(timeUnLock);
    }

    @Test
    public void get_Validation_and_unlook_UserBloq_Time_OK(){
        var timeUnLock = this.parameterService.getParameter("timeUnlock");
        this.unlockingUserByTimeService.saveOrUpdateUnlockServices(timeUnLock);

    }

    @Test(expected = IllegalArgumentException.class)
    public void get_Validation_and_unlock_UserBloq_Time_Error(){
        var timeUnLock = this.parameterService.getParameter("timeUnlook");
        this.unlockingUserByTimeService.saveOrUpdateUnlockServices(timeUnLock);
    }


}

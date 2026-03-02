package mx.com.axity.commons.to;

import java.io.Serializable;

public class EmployeePersonalTO implements Serializable {

    EmployeeComplementaryTO complementary;
    EmployeeAddressTO addressTO;

    public EmployeeComplementaryTO getComplementary() {
        return complementary;
    }

    public void setComplementary(EmployeeComplementaryTO complementary) {
        this.complementary = complementary;
    }

    public EmployeeAddressTO getAddressTO() {
        return addressTO;
    }

    public void setAddressTO(EmployeeAddressTO addressTO) {
        this.addressTO = addressTO;
    }
}

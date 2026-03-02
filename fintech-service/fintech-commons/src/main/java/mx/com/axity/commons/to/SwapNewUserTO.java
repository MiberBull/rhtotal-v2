package mx.com.axity.commons.to;

import java.time.LocalDate;
import java.util.Date;

public class SwapNewUserTO {

    String email;
    String phone;
    String firstName;
    String lastName;
    LocalDate bithDate;
    String apikey;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBithDate() {
        return bithDate;
    }

    public void setBithDate(LocalDate bithDate) {
        this.bithDate = bithDate;
    }

    public String getApikey() {   return apikey; }

    public void setApikey(String apikey) {   this.apikey = apikey; }
}

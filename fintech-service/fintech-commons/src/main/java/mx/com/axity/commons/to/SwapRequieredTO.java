package mx.com.axity.commons.to;

import java.io.Serializable;

public class SwapRequieredTO implements Serializable {

    private String apiKey;//sk_live_ZOsBs3VhwywefSMKLzvabYtMxOsLihwR",
    private String transferId;//00002
    private String description;//Abono
    private double amount;//:1.00 ,
    private String userId;//":"ncV3buVYHO", (Al menos uno de userId, email o phone)
    private String email;//":"brunoramosberho@me.com",
    private String phone;
    private String name;
    private String lastname;
    private String birthdate;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone;  }

    public String getName() { return name;    }

    public void setName(String name) {this.name = name;}

    public String getLastname() {return lastname; }

    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getBirthdate() { return birthdate; }

    public void setBirthdate(String birthdate) { this.birthdate = birthdate; }
}

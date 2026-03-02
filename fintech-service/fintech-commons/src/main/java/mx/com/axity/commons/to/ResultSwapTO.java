package mx.com.axity.commons.to;

import java.io.Serializable;

public class ResultSwapTO implements Serializable {
    private String id;// "5FHWM4hIJk",
    private String transferId;//: "SWAP0016956877",
    private String status;// "paid",
    private String description;//DPVALE",
    private Long account;//": "002730701048689063",
    private Long numReference;// "1234567",
    private double amount;//": 0.01,
    private  Long fee;// 0,
    private  String bank;//"BANAMEX",
    private  String owner;//"JOSE MALIK DAUT ROSAS",
    private  Long createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    public Long getNumReference() {
        return numReference;
    }

    public void setNumReference(Long numReference) {
        this.numReference = numReference;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}

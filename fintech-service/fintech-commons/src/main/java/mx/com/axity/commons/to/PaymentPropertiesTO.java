package mx.com.axity.commons.to;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class PaymentPropertiesTO implements Serializable {


    private double correspondingAmount;
    private int workedDays;
    private double acomulatedAmount;
    private double nextAmount;
    private double commission;
    private String formadePago;
    private LocalDate siguientePago;
    private CommissionsTO commisiones;

    public double getCorrespondingAmount() {
        return correspondingAmount;
    }

    public void setCorrespondingAmount(double correspondingAmount) {
        this.correspondingAmount = correspondingAmount;
    }

    public int getWorkedDays() {
        return workedDays;
    }

    public void setWorkedDays(int workedDays) {
        this.workedDays = workedDays;
    }

    public double getAcomulatedAmount() {
        return acomulatedAmount;
    }

    public void setAcomulatedAmount(double acomulatedAmount) {
        this.acomulatedAmount = acomulatedAmount;
    }

    public double getNextAmount() {
        return nextAmount;
    }

    public void setNextAmount(double nextAmount) {
        this.nextAmount = nextAmount;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public String getFormadePago() {
        return formadePago;
    }

    public void setFormadePago(String formadePago) {
        this.formadePago = formadePago;
    }

    public LocalDate getSiguientePago() {
        return siguientePago;
    }

    public void setSiguientePago(LocalDate siguientePago) {
        this.siguientePago = siguientePago;
    }

    public CommissionsTO getCommisiones() {
        return commisiones;
    }

    public void setCommisiones(CommissionsTO commisiones) {
        this.commisiones = commisiones;
    }
}

package mx.com.axity.commons.to;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Request body para el endpoint de baja del colaborador.
 */
public class TerminationRequestTO implements Serializable {

    private String reason;
    private LocalDate terminationDate;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDate getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(LocalDate terminationDate) {
        this.terminationDate = terminationDate;
    }
}

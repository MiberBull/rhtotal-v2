package mx.com.axity.commons.to;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class SicoResponseWrapperTO implements Serializable {
    private SicoResponseTO response;
    private HttpStatus status;
    private boolean error = false;

    public SicoResponseTO getResponse() {
        return response;
    }

    public void setResponse(SicoResponseTO response) {
        this.response = response;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}

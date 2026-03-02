package mx.com.axity.commons.to;

public class ClienteFintechTO {

    private String urlAuthorization;
    private String urlTarget;
    private String token;
    private String payloadToDesencrypt;
    private String keyToDesencrypt;

    public String getUrlAuthorization() {
        return urlAuthorization;
    }

    public void setUrlAuthorization(String urlAuthorization) {
        this.urlAuthorization = urlAuthorization;
    }

    public String getUrlTarget() {
        return urlTarget;
    }

    public void setUrlTarget(String urlTarget) {
        this.urlTarget = urlTarget;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPayloadToDesencrypt() {
        return payloadToDesencrypt;
    }

    public void setPayloadToDesencrypt(String payloadToDesencrypt) {
        this.payloadToDesencrypt = payloadToDesencrypt;
    }

    public String getKeyToDesencrypt() {
        return keyToDesencrypt;
    }

    public void setKeyToDesencrypt(String keyToDesencrypt) {
        this.keyToDesencrypt = keyToDesencrypt;
    }
}

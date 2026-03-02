package mx.com.axity.commons.to;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class SicoAuthResponseTO implements Serializable {
    @JsonProperty("token")
    private SicoAuthTokenTO accessToken;

    public SicoAuthTokenTO getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(SicoAuthTokenTO accessToken) {
        this.accessToken = accessToken;
    }
}

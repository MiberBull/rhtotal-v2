package mx.com.axity.commons.to;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class SicoAuthTokenTO implements Serializable {

    @JsonProperty("token_acceso")
    private String token;
    @JsonProperty("tipo_token")
    private String tokenType;
    @JsonProperty("expira_en")
    private int expiration;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public int getExpiration() {
        return expiration;
    }

    public void setExpiration(int expiration) {
        this.expiration = expiration;
    }
}

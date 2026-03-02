package mx.com.axity.services.client;

import mx.com.axity.commons.to.SicoAuthResponseTO;
import mx.com.axity.commons.to.SicoAuthTokenTO;
import mx.com.axity.services.client.impl.SicoAdapterImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SicoAdapterMock extends SicoAdapterImpl {

    @Override
    protected ResponseEntity<String> makeApiCall(String url) {
        //return super.makeApiCall(url);

        return new ResponseEntity<String>("'q7jXzQK79OGF3FhCXmvdFl+Nr1oBur2A3XjSAyH/Soc2aom9VYvSHNamrmtPKWGvcufJDJzdtxmPR+j5VJF6RtRLtV3lJgqfZ2PcNNUncYaKSMeCMLOL14+CwVPIpYsPCtHwZKgHf1HNcETpAZmPNP9qNz+1fUmkJnvcUkXLflIvtZb59KfgEFoBIBZ+UodAloPvuqSxjDbhrfowFDF9evrZAR1INWnYpXQpV62qfq5z/peBFaEQH4uLrJNe9iIwVYJ5zrNBvaAzZcqnodyPWjIt9qH8XT546VKE2dDb7zUUfTL35kGYMBmBNzHc6l99zHHd2fHkZsHhrfowFDF9enN1jbRR+Ud1MRFITJ47BeQZRg7A+GOoDeGt+jAUMX16cSkH7G1DNSoXQOWTeVICUB9w5x+oV7O3eGia2kEArJ443Hjkx2pQU15mSHk7BPBrM492Jca1RMDXkpxrq11FFccDlD5/hYcO0RNJ3NG4LRuftLnzpOPWgEhbMSyCCTtFbb19VFR5wDgR2u9U3gGKUWTnxXiLebU0Ua4jHAMg2xrWnLR5yFqf4zNOXBpGuZPQ+9Gg6vQb8XHnUbN3V4wWHyek2oyf4um6Lp/z4ebfmxsDnd1soZMMvxZrCqyfYzMn9HS2lMB2o4q/xCzh0YtNC8jJxGOP1lmK4TYWJzMzTRfcWbK0QCJMaphwH/nx9k794dSyndppred7QcyEzzVj2sNORxzDkQp6zt8IN3sEXoSoYIs3PPhe8PHOApvYCpOl5QQK6Js9LHBwI0spYDtOlmou5+wqJOcXp/IX5xs5RS/myp2nPdUZuzVxs3U3A/vp5961ofFXBVI='", HttpStatus.OK);

    }


    @Override
    protected SicoAuthResponseTO makeAuthCall() {

        //return super.makeAuthCall();

        SicoAuthResponseTO authResponse = new SicoAuthResponseTO();

        SicoAuthTokenTO authToken = new SicoAuthTokenTO();

        authToken.setExpiration(3000);
        authToken.setToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ2YWwxIjoic2RrU1h6SWhaWTAxanZyNy9XSFprN0c3eEE9PSIsInZhbDEwIjoxLCJ2YWwxMSI6MSwidmFsMTIiOjUsInZhbDIiOiI2UitVQ2M1emNtcHlQUFNXZ3Q1MjEyNVZZWWlrSTVaMllZU24ydz09IiwidmFsMyI6IlVxRU1JakJWcE1FV1VoOG1uWGErSFlTN2hmM0RFb05RTmc9PSIsInZhbDQiOjEwMCwidmFsNSI6MTAsInZhbDYiOjMwMDAsInZhbDciOjMwMCwidmFsOCI6W3sibm9tYnJlX211ZXN0cmEiOiJjb250cmF0b19hc2lnbmFjaW9uIiwidmFsb3JfYnVzY2EiOiIifSx7Im5vbWJyZV9tdWVzdHJhIjoiY29udHJhdG9fY29uZmlkZW5jaWFsaWRhZCIsInZhbG9yX2J1c2NhIjoiIn1dLCJ2YWw5IjoiIiwiYXVkIjoidGIxMWtkaDRiZHgiLCJleHAiOjE1MzkxMjQ3ODMsImlhdCI6MTUzOTAzODM4MywiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo0MDAwIn0.rtzQ7PAbk_MKPn7p1UqekPMxnaRpVDv8lRNS2lV8120");

        authToken.setTokenType("Bearer");

        authResponse.setAccessToken(authToken);

        return authResponse;

    }
}

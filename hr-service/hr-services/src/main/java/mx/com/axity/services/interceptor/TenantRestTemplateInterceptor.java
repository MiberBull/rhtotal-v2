package mx.com.axity.services.interceptor;

import mx.com.axity.commons.context.TenantContext;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TenantRestTemplateInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
            ClientHttpRequestExecution execution) throws IOException {
        String tenant = TenantContext.getCurrentTenant();
        if (tenant != null) {
            request.getHeaders().set("X-Tenant-ID", tenant);
        }
        return execution.execute(request, body);
    }
}

package mx.com.axity.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class TenantResolutionFilter implements GlobalFilter, Ordered {

    private static final String TENANT_HEADER = "X-Tenant-ID";
    private static final String DEFAULT_TENANT = "dchkw";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String tenantId = exchange.getRequest().getHeaders().getFirst(TENANT_HEADER);

        if (tenantId == null || tenantId.isBlank()) {
            String host = exchange.getRequest().getURI().getHost();
            tenantId = extractTenantFromHost(host);
        }

        if (tenantId == null || tenantId.isBlank()) {
            tenantId = DEFAULT_TENANT;
        }

        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                .header(TENANT_HEADER, tenantId)
                .build();

        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    @Override
    public int getOrder() {
        return -100;
    }

    private String extractTenantFromHost(String host) {
        if (host == null) return null;
        String[] parts = host.split("\\.");
        return (parts.length >= 3) ? parts[0] : null;
    }
}

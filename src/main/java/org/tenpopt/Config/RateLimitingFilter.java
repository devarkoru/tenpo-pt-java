package org.tenpopt.Config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RateLimitingFilter implements Filter {
    private static final int MAX_REQUESTS_PER_MINUTE = 3; // Límite de requests por minuto
    private final ConcurrentHashMap<String, RequestCounter> clientRequestMap = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpRequest) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            // Aplicar el filtro solo a los métodos POST
            if ("POST".equalsIgnoreCase(httpRequest.getMethod())) {
                // Identificar al cliente (usamos la IP como ejemplo)
                String clientIp = httpRequest.getRemoteAddr();

                // Excluir las rutas de Swagger y el endpoint /transactionEdit del rate limiting
                String requestURI = httpRequest.getRequestURI();
                System.out.println(requestURI);
                if (requestURI.startsWith("/swagger-ui") || requestURI.startsWith("/v3/api-docs") || requestURI.startsWith("/swagger-resources") || requestURI.equals("/transactionEdit")) {
                    chain.doFilter(request, response);
                    return;
                }

                // Obtener o inicializar el contador de solicitudes para el cliente
                RequestCounter counter = clientRequestMap.computeIfAbsent(clientIp, k -> new RequestCounter());

                synchronized (counter) {
                    if (counter.getRequestCount() >= MAX_REQUESTS_PER_MINUTE) {
                        // Responder con 429 Too Many Requests si se supera el límite
                        httpResponse.setStatus(HttpServletResponse.SC_CONFLICT);
                        httpResponse.getWriter().write("Rate limit exceeded. Try again later.");
                        return;
                    } else {
                        counter.increment();
                    }
                }
            }
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
        {
            // Tarea programada para reiniciar los contadores cada minuto
            try (ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1)) {
                scheduler.scheduleAtFixedRate(clientRequestMap::clear, 0, 1, TimeUnit.MINUTES);
            }
        }
    }

    /**
     * Método para reiniciar manualmente los contadores de solicitudes (útil para pruebas unitarias).
     */
    public void resetRateLimiting() {
        clientRequestMap.clear();
    }

    // Clase auxiliar para contar las solicitudes de un cliente
    private static class RequestCounter {
        private final AtomicInteger requestCount = new AtomicInteger(0);

        public int getRequestCount() {
            return requestCount.get();
        }

        public void increment() {
            requestCount.incrementAndGet();
        }
    }
}
package org.workshop.task_management.pkg.middleware.logging;

import cn.hutool.core.text.AntPathMatcher;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Component
public class LoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private final List<String> ignoredEndpoints = List.of(
            "/health-check"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpRequest && response instanceof HttpServletResponse httpResponse) {

            String uri = httpRequest.getRequestURI();

            // Skip logging for specific endpoints
            if (shouldSkipLogging(uri)) {
                chain.doFilter(request, response);
                return;
            }

            long startTime = System.nanoTime(); // Record start time

            chain.doFilter(request, response); // Process the request

            long duration = System.nanoTime() - startTime; // Calculate duration
            logResponse(httpResponse, httpRequest, duration); // Log the response
        }
    }

    private void logResponse(HttpServletResponse response, HttpServletRequest request, long durationNano) {
        // Convert duration to milliseconds
        long durationMillis = durationNano / 1_000_000;

        // Get current timestamp in the desired format
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));

        // เรียก method เพื่อดึง client IP
        String clientIp = getClientIp(request);

        // Get the HTTP status code
        int statusCode = response.getStatus();

        // Build the log message
        String logMessage = String.format(
                "%s | %dms | %s - %d %s %s ",
                timestamp,
                durationMillis,
                clientIp,
                statusCode,
                request.getMethod(),
                request.getRequestURI()
        );

        if (statusCode >= 400 && statusCode < 600) {
            logger.error(logMessage); // Log as error
        } else {
            logger.info(logMessage); // Log as info
        }
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            return ip.split(",")[0].trim();
        }
        ip = request.getHeader("X-Real-IP");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }

        ip = request.getRemoteAddr();
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            return "127.0.0.1";
        }
        return ip;
    }


    private boolean shouldSkipLogging(String uri) {
        return ignoredEndpoints.stream().anyMatch(pattern -> pathMatcher.match(pattern, uri));
    }

}
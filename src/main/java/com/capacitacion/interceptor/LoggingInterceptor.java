package com.capacitacion.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    public static final String TENANT_HEADER_NAME = "X-TENANT-ID";

    public static final String USER_ID_HEADER_NAME = "X-USER-ID";

    public static final String ROLE_ID_HEADER_NAME = "X-ROLE-ID";

    public static final String CURRENCY_HEADER_NAME = "X-CURRENCY";

    public static final String LOCALE_HEADER_NAME = "X-LOCALE";

    public static final String BRANCH_ID_HEADER_NAME = "X-BRANCH-ID";

    public static final String PROFESSIONAL_ID_HEADER_NAME = "X-PROFESSIONAL-ID";

    public static final String PROVIDER_ID_HEADER_NAME = "X-PROVIDER-ID";

    public static final String PROFESSIONAL_BRANCH_REL_ID_HEADER_NAME = "X-PROFESSIONAL-BRANCH-REL-ID";

    public static final String CSV_FORMAT_HEADER_NAME = "X-CSV-FORMAT";

    public static final String TIMEZONE_HEADER_NAME = "X-TIMEZONE";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("Petición entrante: {} {}", request.getMethod(), request.getRequestURI());
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        fillHeaders(request);
        return true; // Continúa con la ejecución del handler
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("Handler ejecutado para: {} {}", request.getMethod(), request.getRequestURI());
        fillHeaders(request);
        Context.clear();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        logger.info("Petición completada en: {} ms para {} {}", (endTime - startTime), request.getMethod(), request.getRequestURI());
        if (ex != null) {
            logger.error("Se produjo una excepción durante la petición: {} {}", request.getMethod(), request.getRequestURI(), ex);
        }
    }

    private void fillHeaders(final HttpServletRequest request) {
        String tenantId = request.getHeader(TENANT_HEADER_NAME);
        Context.setTenantId(tenantId);

        String userId = request.getHeader(USER_ID_HEADER_NAME);
        Context.setUserId(userId);

        String roleId = request.getHeader(ROLE_ID_HEADER_NAME);
        Context.setRoleId(roleId);

        String currency = request.getHeader(CURRENCY_HEADER_NAME);
        Context.setCurrency(currency);

        String locale = request.getHeader(LOCALE_HEADER_NAME);
        Context.setLocale(locale);

        String branchId = request.getHeader(BRANCH_ID_HEADER_NAME);
        Context.setBranchId(branchId);

        String professionalId = request.getHeader(PROFESSIONAL_ID_HEADER_NAME);
        Context.setProfessionalId(professionalId);

        String providerId = request.getHeader(PROVIDER_ID_HEADER_NAME);
        Context.setProviderId(providerId);

        String csvFormat = request.getHeader(CSV_FORMAT_HEADER_NAME);
        Context.setCsvFormat(csvFormat);

        String professionalBranchRelId = request.getHeader(PROFESSIONAL_BRANCH_REL_ID_HEADER_NAME);
        Context.setProfessionalBranchRelId(professionalBranchRelId);

        String timeZone = request.getHeader(TIMEZONE_HEADER_NAME);
        Context.setTimezone(timeZone);
    }

}

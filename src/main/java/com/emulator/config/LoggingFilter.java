package com.emulator.config;

import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

public class LoggingFilter extends AbstractRequestLoggingFilter {

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        return logger.isDebugEnabled();
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String s) {
        String logMessage = "Request";
        logMessage += " method={" + request.getMethod() + "}";
        logMessage += " URI={" + request.getRequestURI() + "}";
        logger.debug(logMessage);
    }

    @Override
    protected void afterRequest(HttpServletRequest httpServletRequest, String s) {

    }
}

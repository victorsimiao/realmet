package br.com.victor.realmeet.filter;

import br.com.victor.realmeet.domain.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class VerifyApiKeyFilter extends GenericFilterBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerifyApiKeyFilter.class);
    private static final String HEADER_API_KEY = "api-key";

    private final ClientRepository clientRepository;

    public VerifyApiKeyFilter(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String apiKey = httpServletRequest.getHeader(HEADER_API_KEY);

        if (!isBlank(apiKey) && isValidApiKey(apiKey)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            sendUnauthorizedError(httpServletResponse, apiKey);
        }
    }

    private boolean isValidApiKey(String apiKey) {
        boolean isPresent = clientRepository
                .findById(apiKey)
                .filter(c -> c.getActive())
                .stream()
                .peek(c -> LOGGER.info("Valid API KEY: '{}' ({})", c.getApiKey(), c.getDescription()))
                .findFirst()
                .isPresent();

        return isPresent;
    }

    private void sendUnauthorizedError(HttpServletResponse response, String apiKey) throws IOException {
        String errorMessage = isBlank(apiKey) ? "API Key is missing" : "API Key is invalid";
        LOGGER.error(errorMessage);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentLength(errorMessage.length());
        response.setContentType("plain/text");

        try (Writer out = response.getWriter()) {
            out.write(errorMessage);
        }
    }
}

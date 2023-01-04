package br.com.victor.realmeet.config;

import br.com.victor.realmeet.domain.repository.ClientRepository;
import br.com.victor.realmeet.filter.VerifyApiKeyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    private final ClientRepository clientRepository;

    public FilterConfiguration(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Bean
    public FilterRegistrationBean<VerifyApiKeyFilter> verifyApiKeyFilter() {
        FilterRegistrationBean<VerifyApiKeyFilter> filterFilterRegistrationBean = new FilterRegistrationBean<VerifyApiKeyFilter>();
        filterFilterRegistrationBean.setFilter(new VerifyApiKeyFilter(clientRepository));
        filterFilterRegistrationBean.addUrlPatterns("/rooms/*", "/allocations/*");
        return filterFilterRegistrationBean;
    }
}

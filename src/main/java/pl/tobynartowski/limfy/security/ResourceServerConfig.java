package pl.tobynartowski.limfy.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${spring.data.rest.base-path}")
    private String prefix;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/oauth/**").permitAll()
                    .antMatchers(HttpMethod.POST, prefix + "/users").permitAll()
                    .antMatchers(HttpMethod.DELETE, prefix + "/users/**").permitAll()
                .and()
                .authorizeRequests()
                    .antMatchers(prefix + "/**").authenticated();
    }
}

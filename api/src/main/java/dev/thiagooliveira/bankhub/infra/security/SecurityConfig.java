package dev.thiagooliveira.bankhub.infra.security;

import dev.thiagooliveira.bankhub.infra.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired private Environment environment;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    String[] activeProfiles = environment.getActiveProfiles();
    boolean isDefaultProfileActive = activeProfiles.length == 0;

    // Define os endpoints públicos
    var permitAllMatchers = new java.util.ArrayList<String>();
    permitAllMatchers.add("/login");
    permitAllMatchers.add("/assets/**");
    permitAllMatchers.add("/error");

    if (isDefaultProfileActive) {
      permitAllMatchers.add("/h2-console/**");
    }
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(permitAllMatchers.toArray(new String[0]))
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .formLogin(
            form ->
                form.loginPage("/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/", true)
                    .permitAll())
        .logout(logout -> logout.logoutSuccessUrl("/login.html?logout").permitAll());
    if (isDefaultProfileActive) {
      http.headers(headers -> headers.frameOptions().disable());
    }
    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService(UserRepository userRepository) {
    return new CustomUserDetailsService(userRepository);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

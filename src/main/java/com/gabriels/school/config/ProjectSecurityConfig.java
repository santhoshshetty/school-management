package com.gabriels.school.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        //Permit All and Deny All
      /* http.authorizeHttpRequests((requests) ->
                requests.anyRequest().denyAll()).
                    formLogin(Customizer.withDefaults()).
                        httpBasic(Customizer.withDefaults());*/

        http.authorizeHttpRequests((requests) ->
                        requests.requestMatchers("/courses").permitAll()
                                .requestMatchers(("/admin/**")).hasRole("ADMIN")
                                .requestMatchers("/displayMessages").hasRole("ADMIN")
                                .requestMatchers("/closeMsg/**").hasRole("ADMIN")
                                .requestMatchers("/home").permitAll()
                                .requestMatchers("/about").permitAll()
                                .requestMatchers("/assets/**").permitAll()
                                .requestMatchers("/public/**").permitAll()
                                //.requestMatchers(PathRequest.toH2Console()).permitAll() - Disable h2, as we migrated to AWS MySQL
                                .anyRequest().authenticated())
                //.csrf(AbstractHttpConfigurer::disable)
                //From Spring 7 CSRF is by default enabled and for GET methods its automatically disabled
                //Here we are disabling CSRF for SaveMsg request
                .csrf(csrf -> csrf.ignoringRequestMatchers("/saveMsg").ignoringRequestMatchers(PathRequest.toH2Console()).ignoringRequestMatchers("/public/**"))
                .formLogin(form -> form.loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true)
                            .failureUrl("/login?error=true").permitAll())
                                .logout(logout -> logout.logoutSuccessUrl("/login?logout=true")
                                        .invalidateHttpSession(true).permitAll())
                                            .httpBasic(Customizer.withDefaults());
       /* http.headers(headersConfigurer -> headersConfigurer --Disable H2 Frame Options as we migrated to AWS MySQL
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));*/
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //As we are going to do login authentication via DB credentials, hence commenting the below code
   /* @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails user = User.withUsername("james").password(encoder.encode("password")).roles("USER").build();
        UserDetails admin = User.withUsername("john").password(encoder.encode("password")).roles("ADMIN").build();
        return new InMemoryUserDetailsManager(user, admin);
    }*/
}

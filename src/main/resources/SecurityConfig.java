import com.almaz.almazbank.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Autowired
    UserService userDetailsService;
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.csrf(csrf->csrf.ignoringRequestMatchers("/api/user/all"))
                .authorizeHttpRequests((authorizationManagerRequestMatcherRegistry) ->{
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/api/user/all").authenticated();
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/api/user/create").anonymous();
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/api/bank/**").authenticated();
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/api/user/update/*").authenticated();
                });
        httpSecurity.authenticationProvider(authenticationProvider());
        return httpSecurity.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

package com.robert.configuration;


import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.robert.LoggingAccessDeniedHandler;


import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	//@Resource(name = "userDetailService")
	//private UserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
		
	// ===========================================================================
	// gestion des authorisations
	// 
	// ===========================================================================

    @Autowired
    private LoggingAccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers(
                            "/",
                            "/js/**",
                            "/css/**",
                            "/img/**",
                            "/fragments",
                            "/error",
                            "/webjars/**").permitAll()
                    .antMatchers("/user/**").hasRole("ADMIN")
                    .antMatchers("/user**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .permitAll()
                .and()
                .logout()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login?logout")
                    .permitAll()
                .and()
                .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler);
    }
	// ===========================================================================
	// gestion des utilisateurs
	// les données sont dans la base
	// le cas des utilisateurs en mémoire est mis en commentaire
	// ===========================================================================
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth, DataSource dataSource) throws Exception {
    	/*
        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER")
            .and()
                .withUser("manager").password("password").roles("MANAGER");
        */
        	// ==============================================================
     		// authentification à partir de la mémoire sans accès DB ou LDAP
     		// ==============================================================
     		/*
     		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN","PROF");
     		auth.inMemoryAuthentication().withUser("prof1").password("123").roles("PROF");
     		auth.inMemoryAuthentication().withUser("etd1").password("123").roles("ETUDIANT");
     		auth.inMemoryAuthentication().withUser("sco1").password("123").roles("SCOLARITE");
     		*/  
        
        	// ==============================================================
     		// acces à partir de la base de données
     		// ==============================================================
        
        auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("select username as principal, password as credentials, actived from usersecurity where username = ? ")
			.authoritiesByUsernameQuery("select user_security_username as principal, roles_role as role from users_roles where user_security_username =?")
			.rolePrefix("ROLE_")
			.passwordEncoder(bCryptPasswordEncoder);   
    }
    // ===========================================================================
    /*
    @Bean
    public PasswordEncoder passwordEncoder() {
    	PasswordEncoder encoder = new BCryptPasswordEncoder();
    	return encoder;  	
    }
    */
    // ===========================================================================
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
 
        // Setting Service to find User in the database.
        // And Setting PassswordEncoder
        //auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		// BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //auth.userDetailsService(myAppUserDetailsService).passwordEncoder(passwordEncoder);
 
    }
	
}
//================================================================






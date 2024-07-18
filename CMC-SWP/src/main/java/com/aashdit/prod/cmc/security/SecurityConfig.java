package com.aashdit.prod.cmc.security;

import com.aashdit.prod.cmc.service.umt.UserDetailsServiceImpl;
import com.aashdit.prod.cmc.utils.HelperFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter.ReferrerPolicy;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.aashdit.dms.utils.DMSConstants;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = {"com.aashdit.*"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private JwtFilter jwtFilter;


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}

	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.headers() // Security Fix #3 - Vishnoo
				.contentTypeOptions()
				.and().xssProtection()
				.and().cacheControl()
				.and().httpStrictTransportSecurity()
				.and().frameOptions()
				.and().contentSecurityPolicy("script-src 'self' 'unsafe-eval' 'unsafe-inline'")
				.and().referrerPolicy(ReferrerPolicy.SAME_ORIGIN);
		
		// Security Fix #2 - Vishnoo
		http.authorizeRequests().antMatchers(HttpMethod.TRACE, "/**").denyAll() 
				.antMatchers(HttpMethod.PATCH, "/**").denyAll()
				.antMatchers(HttpMethod.PUT, "/**").denyAll()
				.antMatchers(HttpMethod.DELETE, "/**").denyAll()
				.antMatchers(HttpMethod.HEAD, "/**").denyAll()
				.antMatchers("/umt/login").permitAll()
				.antMatchers("/overwrite/**").permitAll()
				.antMatchers("/captcha/**").permitAll()
				.antMatchers("/loginPage/**").permitAll()
				.antMatchers("/assets/**").permitAll()
				.antMatchers("/privacyPolicy").permitAll()
				.antMatchers("/api/**").permitAll()
				.antMatchers("/system/**").hasAnyRole("SYSTEM")
				//.antMatchers("/admin/**").hasAnyRole("SYSTEM", "ADMIN")
				.antMatchers("/public/**").permitAll()
				.antMatchers("/reports/**").permitAll()
				.antMatchers("/report/**").permitAll()
				.antMatchers("/reset/**").permitAll()
				.antMatchers("/mst/**").permitAll()
				.antMatchers("/inventory/**").permitAll()
				.antMatchers("/Bonus-Incentive/save-bonus-incntv-dtls").permitAll()
				.antMatchers("/scheduleTimeTableManagement/timetable/savePeriod").permitAll()
				.antMatchers(HelperFunction.AUTH_WHITELIST).permitAll()
				.antMatchers(DMSConstants.DMS_WHITELIST_URLS).permitAll()
//				.antMatchers("/master/**").hasRole("TECH_AGENCY")
//				.antMatchers("/nodalAgency/**").hasRole("NODAL_AGENCY")
				.anyRequest()
				.authenticated()
				.and()
				.formLogin()
				.loginPage("/login").permitAll()
				.and()
				.logout()
				.logoutSuccessUrl("/login");
		http.cors().and().csrf().ignoringAntMatchers("/api/**", "/api/login","/scheduleTimeTableManagement/timetable/savePeriod");
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public SessionRegistry sessionRegistry() {
		SessionRegistry sessionRegistry = new SessionRegistryImpl();
		return sessionRegistry;
	}

	@Bean
	public HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository() {
		return new HttpSessionCsrfTokenRepository();
	}

}

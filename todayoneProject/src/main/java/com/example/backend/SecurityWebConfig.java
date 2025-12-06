package com.example.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityWebConfig {
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()) // 개발 단계에서 CSRF 비활성화
				.cors(cors -> {}) // cors 허용
				.logout(logout -> logout
						.logoutUrl("/api/logout")
						.logoutSuccessHandler((request, response, authentication) -> {
							response.setStatus(HttpServletResponse.SC_OK);
							response.setContentType("application/json");
							response.getWriter().write("{\"message\":\"로그아웃 완료\"}");
						})
						.deleteCookies("JSESSIONID")
						.invalidateHttpSession(true)
				)
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/**").permitAll()
						.anyRequest().permitAll() // 나머지 API 전부 허용
				);

		return http.build();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**") // 모든 API 경로
						.allowedOrigins("http://localhost:3000") // React 개발 서버 허용
						.allowedMethods("*") // GET, POST, PUT, DELETE 등 모두 허용
						.allowedHeaders("*") // 모든 헤더 허용
						.allowCredentials(true); // 쿠키/인증정보 허용
			}
		};
	}
}

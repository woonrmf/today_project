package com.example.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.backend.member.Member;
import com.example.backend.member.MemberRepository;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityWebConfig {
	
	private final MemberRepository memberRepository;
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()) // 개발 단계에서 CSRF 비활성화
				.cors(cors -> {}) // cors 허용
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)) //필요할 때 생성
				.formLogin(form -> form
						.loginProcessingUrl("/api/members/login")
						.usernameParameter("userid")
						.passwordParameter("password")
						.successHandler((req, res, auth) -> {
							res.setStatus(HttpServletResponse.SC_OK);
							res.setContentType("application/json;charset=UTF-8");
							
							String userid = auth.getName();
							Member member = memberRepository.findByUserid(userid)
									.orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
							
							String json = String.format(
	                                "{\"userno\": %d, \"username\": \"%s\", \"userid\": \"%s\", \"birth\": %s, \"role\": \"%s\"}",
	                                member.getUserno(),
	                                member.getUsername(),
	                                member.getUserid(),
	                                member.getBirth() != null ? "\"" + member.getBirth().toString() + "\"" : "null",
	                                member.getRole().name()
	                            );
	                            res.getWriter().write(json);
						})
						.failureHandler((req, res, ex) -> {
							res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
							res.setContentType("application/json;charset=UTF-8");
							res.getWriter().write("{\"message\":\"아이디 또는 비밀번호가 일치하지 않습니다.\"}");
						})
				)
				.logout(logout -> logout
						.logoutUrl("/api/members/logout")
						.logoutSuccessHandler((request, response, authentication) -> {
							response.setStatus(HttpServletResponse.SC_OK);
							response.setContentType("application/json;charset=UTF-8");
							response.getWriter().write("{\"message\":\"로그아웃 완료\"}");
						})
						.deleteCookies("JSESSIONID")
						.invalidateHttpSession(true)
				)
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/members/signup", "/api/members/login").permitAll()
						.requestMatchers("/api/posts/**").permitAll()
						.requestMatchers("/api/members/list").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/api/words/**").authenticated()
						.requestMatchers("/api/words/**").hasRole("ADMIN")
						.requestMatchers("/api/posts/**").authenticated()
		              .anyRequest().authenticated()
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

package com.bowen.blog.config;

import com.bowen.blog.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfing extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {


    /*用于记住我存如数据库*/
    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private UserDetailsService userDetailsService;

    /*用于记住我存如数据库*/
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // 如果token表不存在，使用下面语句可以初始化该表；若存在，请注释掉这条语句，否则会报错。
        //tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    /**
     * 加密文
     **/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
        /**不加密**/
        auth.userDetailsService(myUserDetailsService).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return s.equals(charSequence.toString());
            }
        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 如果有允许匿名的url，填在下面
                .antMatchers("/",
                        "/static/**",
                        "/static/lib/**",
                        "/static/css/**",
                        "/static/js/**",
                        "/static/fonts/**",
                        "/static/images/**",
                        "/static/languages/**",
                        "/static/plugins/**").permitAll()
                .anyRequest()
                .authenticated()
                // 自动登录
                .and()
                // 设置登陆页
                .formLogin()
                .loginPage("/admin/login")
                // 设置登陆成功页
                .defaultSuccessUrl("/admin/save").permitAll()
                // 登录失败Url
                //.failureUrl("/admin/login/error")
                .failureForwardUrl("/admin/login/error")
                .and()
                .logout()
                .permitAll()
                .and()

                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                // 有效时间：单位s
                .tokenValiditySeconds(3600)
                .userDetailsService(userDetailsService);

        http.csrf().disable();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置拦截忽略文件夹，可以对静态资源放行
        web.ignoring()
                .antMatchers("static/bower_components/**",
                        "static/dist/**",
                        "static/plugins/**",
                        "static/css/**",
                        "static/js/**",
                        "static/lib/**",
                        "static/fonts/**",
                        "static/images/**",
                        "static/languages/**",
                        "static/plugins/**");
    }

    /*解决th查找静态文件404*/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}

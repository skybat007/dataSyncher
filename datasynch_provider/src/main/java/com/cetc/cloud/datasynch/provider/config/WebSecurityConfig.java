package com.cetc.cloud.datasynch.provider.config;

//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser("zdk")
//                .password("Password@1")
//                .roles("USER");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/index").hasRole("admin")
//                .antMatchers("/test").hasAnyRole("read")
//                .anyRequest().authenticated()
//                .and()
//                .csrf().disable()//关闭csrf
//                .httpBasic()
//                .and()
//                .logout()
//                .permitAll()
//                .and()
//                .sessionManagement()
//        ;
//        http.headers().cacheControl();
////        http.addFilterBefore(new MyOauth2Filter(), UsernamePasswordAuthenticationFilter.class);
//
//    }
//
//}
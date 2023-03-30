//package com.example.bejv007.security;
//
//import com.example.bejv007.user.UserModel;
//import com.example.bejv007.user.entities.PrivilegeEntity;
//import com.example.bejv007.user.entities.RoleEntity;
//import com.example.bejv007.user.exceptions.EmailDontExistException;
//import com.example.bejv007.user.repositories.RoleRepository;
//import com.example.bejv007.user.repositories.UserJpaRepository;
//import com.example.bejv007.user.services.impl.UserServiceImpl;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.MessageSource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
//import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.List;
//
//@Service("userDetailsService")
//@Transactional
//@RequiredArgsConstructor
//public class MyUserDetailsService implements UserDetailsService {
//    private final UserJpaRepository userRepository;
//
//    private final UserServiceImpl service;
//
//    private final MessageSource messages;
//
//    private final RoleRepository roleRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email)
//            throws UsernameNotFoundException {
//
//        UserModel user = null;
//        try {
//            user = userRepository.findByEmail(email).orElseThrow(EmailDontExistException::new);
//        } catch (EmailDontExistException e) {
//            throw new RuntimeException(e);
//        }
//        if (user == null) {
//            return new org.springframework.security.core.userdetails.User(
//                    " ", " ", true, true, true, true,
//                    getAuthorities(Arrays.asList(
//                            roleRepository.findByName("ROLE_USER"))));
//        }
//
//        return new org.springframework.security.core.userdetails.User(
//                user.getEmail(), user.getPassword(), user.isEnabled(), true, true,
//                true, getAuthorities(user.getRoles()));
//    }
//
//    private Collection<? extends GrantedAuthority> getAuthorities(
//            Collection<RoleEntity> roles) {
//
//        return getGrantedAuthorities(getPrivileges(roles));
//    }
//
//    private List<String> getPrivileges(Collection<RoleEntity> roles) {
//
//        List<String> privileges = new ArrayList<>();
//        List<PrivilegeEntity> collection = new ArrayList<>();
//        for (RoleEntity role : roles) {
//            privileges.add(role.getName());
//            collection.addAll(role.getPrivileges());
//        }
//        for (PrivilegeEntity item : collection) {
//            privileges.add(item.getName());
//        }
//        return privileges;
//    }
//
//    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (String privilege : privileges) {
//            authorities.add(new SimpleGrantedAuthority(privilege));
//        }
//        return authorities;
//    }
//
//    @Bean
//    public RoleHierarchy roleHierarchy(){
//        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
//        String hierarchy = "ROLE_ADMIN > ROLE_USER";
//        roleHierarchy.setHierarchy(hierarchy);
//        return roleHierarchy;
//    }
//
//    @Bean
//    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
//        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
//        expressionHandler.setRoleHierarchy(roleHierarchy());
//        return expressionHandler;
//    }
//
////    @Bean
////    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////        http.csrf()
////                .disable()
////                .authorizeRequests()
////                .expressionHandler(webSecurityExpressionHandler())
////                .antMatchers(HttpMethod.GET, "/roleHierarchy")
////                .hasRole("STAFF");
////    }
//}

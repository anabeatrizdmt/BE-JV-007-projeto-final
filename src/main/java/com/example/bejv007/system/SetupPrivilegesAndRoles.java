//package com.example.bejv007.security;
//
//import com.example.bejv007.user.UserModel;
//import com.example.bejv007.user.entities.PrivilegeEntity;
//import com.example.bejv007.user.entities.RoleEntity;
//import com.example.bejv007.user.repositories.PrivilegeRepository;
//import com.example.bejv007.user.repositories.RoleRepository;
//import com.example.bejv007.user.repositories.UserJpaRepository;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class SetupPrivilegesAndRoles implements ApplicationListener<ContextRefreshedEvent> {
//
//    boolean alreadySetup = false;
//
//    private final UserJpaRepository userRepository;
//    private final RoleRepository roleRepository;
//    private final PrivilegeRepository privilegeRepository;
//    private final PasswordEncoder passwordEncoder;
//
//
//
//
//    @Override
//    @Transactional
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//        if (alreadySetup)
//            return;
//        PrivilegeEntity userPrivilege
//                = createPrivilegeIfNotFound("USER_PRIVILEGE");
//        PrivilegeEntity adminPrivilege
//                = createPrivilegeIfNotFound("ADMIN_PRIVILEGE");
//
//        List<PrivilegeEntity> adminPrivileges = Arrays.asList(
//                adminPrivilege, userPrivilege);
//        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
//        createRoleIfNotFound("ROLE_USER", Arrays.asList(userPrivilege));
//
//        RoleEntity adminRole = roleRepository.findByName("ROLE_ADMIN");
//        UserModel user = new UserModel();
//        user.setName("Test");
//        user.setPassword(passwordEncoder.encode("test"));
//        user.setEmail("test@test.com");
//        user.setRoles(Collections.singletonList(adminRole));
//        user.setEnabled(true);
//        userRepository.save(user);
//
//        alreadySetup = true;
//
//    }
//
//    @Transactional
//    PrivilegeEntity createPrivilegeIfNotFound(String name) {
//
//        PrivilegeEntity privilege = privilegeRepository.findByName(name);
//        if (privilege == null) {
//            privilege = new PrivilegeEntity(name);
//            privilegeRepository.save(privilege);
//        }
//        return privilege;
//    }
//
//    @Transactional
//    RoleEntity createRoleIfNotFound(
//            String name, Collection<PrivilegeEntity> privileges) {
//
//        RoleEntity role = roleRepository.findByName(name);
//        if (role == null) {
//            role = new RoleEntity(name);
//            role.setPrivileges(privileges);
//            roleRepository.save(role);
//
//        }
//        return role;
//    }
//
//}

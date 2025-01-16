package com.my_jewellers.my_jewellers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.my_jewellers.my_jewellers.role.Role;
import com.my_jewellers.my_jewellers.role.RoleRepository;

@SpringBootApplication
@EnableJpaAuditing
public class MyJewellersApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyJewellersApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findRoleByRoleName("USER").isEmpty()) {
                roleRepository.save(Role.builder().roleName("USER").build());
            }
        };
    }
}

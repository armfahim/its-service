package com.its.service;

import com.its.service.entity.auth.User;
import com.its.service.repository.UserRepository;
import com.its.service.request.RegisterRequest;
import com.its.service.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

import static com.its.service.enums.Role.ADMIN;
import static com.its.service.enums.Role.EMPLOYEE;

@SpringBootApplication
@RequiredArgsConstructor
public class ItsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItsServiceApplication.class, args);
    }

    private final UserRepository userRepository;

    /**
     * Create admin and employee base user by system (auto creation)
     *
     * @param service
     * @return
     */
    @Bean
    public CommandLineRunner commandLineRunner(AuthenticationService service) {
        return args -> {
            Optional<User> adminUser = this.userRepository.findByUsername("admin");
            if (adminUser.isEmpty()) {
                var admin = RegisterRequest.builder()
                        .firstname("Admin")
                        .lastname("Admin")
                        .email("admin@mail.com")
                        .username("admin")
                        .password("password")
                        .role(ADMIN)
                        .build();
                System.out.println("Admin token: " + service.register(admin).getAccessToken());
                Thread.sleep(1000);
            }
            Optional<User> employeeUser = this.userRepository.findByUsername("employee");
            if (employeeUser.isEmpty()) {
                var employee = RegisterRequest.builder()
                        .firstname("Employee")
                        .lastname("Employee")
                        .email("employee@mail.com")
                        .username("employee")
                        .password("password")
                        .role(EMPLOYEE)
                        .build();
                System.out.println("Employee token: " + service.register(employee).getAccessToken());
            }

        };
    }

}

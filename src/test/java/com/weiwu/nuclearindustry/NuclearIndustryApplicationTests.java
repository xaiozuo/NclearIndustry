package com.weiwu.nuclearindustry;

import com.weiwu.nuclearindustry.entity.User;
import com.weiwu.nuclearindustry.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NuclearIndustryApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {
        User user1 = userRepository.findUserByUsername("weiwu");
        System.out.println(user1);

        User user2 = userRepository.findUserByUsername("weiwu2");
        System.out.println(user2);
    }

}

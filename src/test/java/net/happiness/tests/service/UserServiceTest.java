package net.happiness.tests.service;

import net.happiness.tests.dto.UserDTO;
import net.happiness.tests.dto.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void testHelloWorld() {
        final String helloWorld = userService.helloWorld();
        assertThat(helloWorld).isNotNull();
        assertThat(helloWorld).isEqualTo("hello world");
    }

    @Test
    void testGetAllUserNames() {
        final List<String> names = userService.getAllUserNames();
        assertThat(names)
                .hasSize(3)
                .startsWith("James Bond")
                .doesNotContainNull()
                .doesNotContain("888")
                .containsAnyOf("James Bond", "Not There")
                .containsExactly("James Bond", "Frank Castle", "T Pain")
                .containsExactlyInAnyOrder("Frank Castle", "James Bond", "T Pain")
                .contains("Frank Castle");
        assertThat("Frank Castle").isIn(names);
    }

    @Test
    void testGetAllUsers() {
        final List<UserDTO> allUsers = userService.getAllUsers();
        assertThat(allUsers)
                .hasSize(3)
                .extracting(UserDTO::getName)
                .containsExactly("James Bond", "Frank Castle", "T Pain");
        assertThat(allUsers)
                .extracting(UserDTO::getAge)
                .allMatch(age -> age > 20);

        final List<UserDTO> copyOfUsers = List.of(allUsers.get(1), allUsers.get(0), allUsers.get(2));
        assertThat(allUsers).hasSameElementsAs(copyOfUsers);
    }

    @Test
    void testGetAdminAndModerator() {
        final List<UserDTO> allAdminOrModUsers = userService.getAllAdminOrModUsers();
        assertThat(allAdminOrModUsers)
                .hasSize(2)
                .extracting(UserDTO::getType)
                .containsExactlyInAnyOrder(UserType.ADMIN, UserType.MODERATOR);
    }

}

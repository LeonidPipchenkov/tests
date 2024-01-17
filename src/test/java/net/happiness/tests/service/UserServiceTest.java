package net.happiness.tests.service;

import net.happiness.tests.dto.UserDTO;
import net.happiness.tests.dto.UserInfoDTO;
import net.happiness.tests.dto.UserType;
import net.happiness.tests.helper.UserProviderHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

class UserServiceTest {

    private UserService userService;
    private UserInfoService userInfoService;

    @BeforeEach
    void setUp() {
        userInfoService = mock(UserInfoService.class);
        doAnswer(a -> {
            final String userId = a.getArgument(0);
            if (userId.equals("not-there")) {
                return null;
            }
            return UserProviderHelper.getUserInfo(userId);
        }).when(userInfoService).getUserInfo(anyString());

        userService = new UserService(userInfoService);
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

    @Test
    void testWithDesc() {
        final UserDTO dto = userService.getAllUsers().get(0);
        assertThat(dto.getAge())
                .as("Checking the age of user with name %s failed", dto.getName())
                .isEqualTo(21); // Change to some other value to see the description
    }

    @Test
    void testWithErrorMessageOverride() {
        final UserDTO dto = userService.getAllUsers().get(0);
        final int expectedAge = 21;
        assertThat(dto.getAge())
                .withFailMessage("Users age should be %s", expectedAge)
                .isEqualTo(expectedAge);
    }

    @Test
    void testExceptions() {
        assertThatThrownBy(() -> {
            UserDTO dto = userService.getAllUsers().get(55);
        }).isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessageContaining("Index 55")
                .hasMessage("Index 55 out of bounds for length 3")
                .hasMessageStartingWith("Index 55 out")
                .hasMessageEndingWith("length 3")
                .hasStackTraceContaining("java.lang.ArrayIndexOutOfBoundsException");
        assertThatCode(() -> {
            UserDTO dto = userService.getAllUsers().get(1);
        }).doesNotThrowAnyException();
    }

    @Test
    void testGetUserInfos_NoUserIds() {
        assertThat(userService.getUserInfos(null)).isEmpty();
        assertThat(userService.getUserInfos(new ArrayList<>())).isEmpty();
    }

    @Test
    void testGetUserInfos() {
        final List<UserDTO> allUsers = userService.getAllUsers();
        final List<UserInfoDTO> userInfos = userService.getUserInfos(
                List.of(allUsers.get(1).getId(), "not-there"));
        assertThat(userInfos).hasSize(1);
    }

}

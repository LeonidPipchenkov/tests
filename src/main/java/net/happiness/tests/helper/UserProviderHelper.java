package net.happiness.tests.helper;

import net.happiness.tests.dto.UserDTO;
import net.happiness.tests.dto.UserInfoDTO;
import net.happiness.tests.dto.UserType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class UserProviderHelper {

    private static List<UserDTO> USERS = List.of(
            new UserDTO(UUID.randomUUID().toString(), "James Bond", "007", 21, UserType.ADMIN),
            new UserDTO(UUID.randomUUID().toString(), "Frank Castle", "punisher", 40, UserType.MODERATOR),
            new UserDTO(UUID.randomUUID().toString(), "T Pain", "MrT", 46, UserType.USER)
    );

    private UserProviderHelper() {
    }

    public static List<UserDTO> getUsers() {
        return USERS;
    }

    public static UserDTO getUser(final String userId) {
        return USERS.stream()
                .filter(x -> x.getId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    public static UserInfoDTO getUserInfo(final String userId) {
        return getUserInfos().stream()
                .filter(x -> x.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    public static List<UserInfoDTO> getUserInfos() {
        final List<UserInfoDTO> userInfos = new ArrayList<>(3);

        int i = 0;
        for (final UserDTO user : USERS) {
            userInfos.add(new UserInfoDTO(user.getId(), "A" + i, "0043" + i));
            i++;
        }
        return userInfos;
    }

}

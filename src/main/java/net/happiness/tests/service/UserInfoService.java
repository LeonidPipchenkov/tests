package net.happiness.tests.service;

import net.happiness.tests.dto.UserDTO;
import net.happiness.tests.dto.UserInfoDTO;
import net.happiness.tests.helper.UserProviderHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoService {

    public UserInfoDTO getUserInfo(final String userId) {
        final UserDTO user = UserProviderHelper.getUser(userId);
        if (user == null) {
            return null;
        }
        final List<UserInfoDTO> userInfos = UserProviderHelper.getUserInfos();
        return userInfos.stream()
                .filter(x -> x.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

}

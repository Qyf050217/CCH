package com.zust.cch.service;

import com.zust.cch.dto.IdentityLoginDTO;
import com.zust.cch.dto.MailAuthDTO;
import com.zust.cch.dto.UpdateProfileDTO;
import com.zust.cch.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    String login(IdentityLoginDTO loginDTO);

    String loginOrRegisterByMail(MailAuthDTO authDTO);

    void updateProfile(Integer userId, UpdateProfileDTO profileDTO);

    User userInfoById(Integer id);

    User findById(Integer userId);
}

package com.zust.cch.service;

import com.zust.cch.dto.IdentityLoginDTO;
import com.zust.cch.dto.MailAuthDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    String login(IdentityLoginDTO loginDTO);

    String loginOrRegisterByMail(MailAuthDTO authDTO);
}

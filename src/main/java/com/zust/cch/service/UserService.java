package com.zust.cch.service;

import com.zust.cch.dto.IdentityLoginDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    String login(IdentityLoginDTO loginDTO);
}

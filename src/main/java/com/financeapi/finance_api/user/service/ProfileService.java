package com.financeapi.finance_api.user.service;

import com.financeapi.finance_api.user.mapper.UserMapper;
import com.financeapi.finance_api.user.repository.ProfileRepository;
import com.financeapi.finance_api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {
	private final ProfileRepository profileRepository;
	private final UserRepository userRepository;
	private final UserMapper mapper;
}

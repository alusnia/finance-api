package com.financeapi.finance_api.user.internal.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
	private final ProfileMapper profileMapper;

}

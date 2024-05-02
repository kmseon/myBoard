package com.example.myboard.service;

import com.example.myboard.config.PrincipalDetails;
import com.example.myboard.entity.UserAccount;
import com.example.myboard.repository.UserAccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.Principal;
import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {
    private final UserAccountRepository accountRepository;

    public UserDetailService(UserAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<UserAccount> account = accountRepository.findById(username);
       if(account.isEmpty()){
           throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
       }
       UserAccount userAccount = account.get();
        return new PrincipalDetails(userAccount);
    }
}

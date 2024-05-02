package com.example.myboard.service;

import com.example.myboard.constant.UserRole;
import com.example.myboard.dto.UserCreateForm;
import com.example.myboard.entity.UserAccount;
import com.example.myboard.repository.UserAccountRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    //비밀번호 암호화
    PasswordEncoder passwordEncoder;

            @Autowired
            @PersistenceContext
            //테이블과 연결되어있는 데이터=entity 관리
            EntityManager em;

    private final UserAccountRepository userAccountRepository;

    public UserService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Transactional
    public void createUser(UserCreateForm userCreateForm) {
        UserAccount account = new UserAccount();
        account.setUserId(userCreateForm.getUsername());
        account.setUserPassword(passwordEncoder.encode(
                userCreateForm.getPassword1()
        ));
        account.setEmail(userCreateForm.getEmail());
        account.setNickname(userCreateForm.getNickname());
        if("ADMIN".equals(userCreateForm.getUsername().toUpperCase())){
            account.setUserRole(UserRole.ADMIN);
        }else{
            account.setUserRole(UserRole.USER);
        }
        em.persist(account);
    }
}

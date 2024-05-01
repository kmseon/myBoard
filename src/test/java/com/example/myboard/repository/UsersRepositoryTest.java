package com.example.myboard.repository;

import com.example.myboard.constant.Gender;
import com.example.myboard.entity.Users;
import com.example.myboard.service.ArticleService;
import jakarta.transaction.InvalidTransactionException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import org.apache.catalina.User;
import org.assertj.core.util.Lists;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.hibernate.query.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class

UsersRepositoryTest {

    @Autowired
    UsersRepository usersRepository;

////    @MockBean
////    private ArticleService articleService;

    @Test
    void findByName테스트(){
        String findName = "Hugo";
        usersRepository.findByName(findName).forEach(users -> System.out.println(users));
    }

    @Test
    void findTop3ByLikeColor테스트(){
        String color = "Pink";
        usersRepository.findTop3ByLikeColor(color).forEach(users -> System.out.println(users));
    }

    @Test
    void findByGenderAndLikeColor테스트(){
        String color = "Pink";
        usersRepository.findByGenderAndLikeColor(Gender.Male, color).forEach(users -> System.out.println(users));
    }

    @Test
    void findByCreatedAtAfter테스트(){
        usersRepository.findByCreatedAtAfter(LocalDateTime.now().minusDays(30L)).forEach(users -> System.out.println(users));
    }

    @Test
    @DisplayName("findByCreatedAtGreaterThanEqual 테스트")
    void findByCreatedAtGreaterThanEqualTest(){
        usersRepository.findByCreatedAtGreaterThanEqual(LocalDateTime.now().minusDays(15L)).forEach(users -> System.out.println(users));
    }

    @Test
    @DisplayName("findByCreatedaAtBetween 테스트")
    void findByCreatedaAtBetween(){
//        usersRepository.findByCreatedAtBetween(LocalDateTime.now().minusDays(31L),
//                LocalDateTime.now()).forEach(users -> System.out.println(users));

        usersRepository.findByIdBetween(1L, 5L).forEach(users -> System.out.println(users));
    }

    @Test
    @DisplayName("findByUpdatedAtIsNotNUll 테스트")
    void findByUpdatedAtIsNotNull(){
        System.out.println("--- InNotNull Count : "+ usersRepository.findByUpdatedAtIsNotNull().stream().count());
    }

//    @Test
//    @DisplayName("findByLikeColorln 테스트")
//    void findByLikeColorlnTest(){
//        usersRepository.findByLikeColorln(Lists.newArrayList("Red", "Orange")).forEach(users -> System.out.println(users));
//    }

//    @Test
//    @DisplayName("StringSearch 테스트")
//    void stringSearechTest(){
//        usersRepository.findByNameStartingWith("D").forEach(users -> System.out.println("D로 시작 : "+users));
//        usersRepository.findByNameEndingWith("s").forEach(users -> System.out.println("s로 끝 : "+users));
//        usersRepository.findByNameSContains("k").forEach(users -> System.out.println("k 포함 : "+users));
//        usersRepository.findByNameLike("%k%").forEach(users -> System.out.println("k 포함 : "+users));
//    }

    @Test
    @DisplayName("findByIdBetweenOrderByNameDesc 테스트")
    void findByIdBetweenOrderByNameDescTest(){
        usersRepository.findByIdBetweenOrderByNameDesc(1L, 5L).forEach(users -> System.out.println(users));
    }

    @Test
    @DisplayName("findTop10OrderByNameAscCreatedAtDesc 테스트")
    void findTop10OrderByNameAscCreatedAtDescTest(){
        usersRepository.findTop10ByLikeColorOrderByGenderDescCreatedAtAsc("Orange").forEach(users -> System.out.println(users));
    }

    @Test
    @DisplayName("findByLikeColor 테스트")
    void findByLikeColorTest(){
        usersRepository.findByLikeColor("Orange", Sort.by(Sort.Order.desc("gender"), Sort.Order.asc("createdAt"))).forEach(users -> System.out.println(users));
    }

//    @Test
//    void findByColorAndSort(){
//        usersRepository.findByLikeColor("Orange", Sort.by(Sort.Order.asc("gender"), Sort.Order.desc("createdAt"))).forEach(users -> System.out.println(users));
//    }

    //전체 페이징 처리
    @Test
    void pagingTest(){
        System.out.println("페이지 = 0 , 페이지당 리스트 수 : 5");
        usersRepository.findAll(
                //페이징 처리해서 findAll
                PageRequest.of(0, 5, Sort.by(Sort.Order.desc("id"))))
                .getContent()
                .forEach(users -> System.out.println(users));

        System.out.println("페이지 = 1, 페이지당 리스트 수 : 5");
        usersRepository.findAll(
                        //페이징 처리해서 findAll
                        PageRequest.of(0, 5, Sort.by(Sort.Order.desc("id"))))
                .getContent()
                .forEach(users -> System.out.println(users));

        System.out.println("페이지 = 2, 페이지당 리스트 수 : 5");
        usersRepository.findAll(
                        //페이징 처리해서 findAll
                        PageRequest.of(0, 5, Sort.by(Sort.Order.desc("id"))))
                .getContent()
                .forEach(users -> System.out.println(users));
    }


    @Test
    void pagingTest2(){
        Pageable pageable = PageRequest.of(4,10);
        Page<Users> result = usersRepository.findByIdGreaterThanEqualOrderByIdDesc(
                200L, pageable);
        result.getContent().forEach(users -> System.out.println(users));
        //총페이지수
        System.out.println("TotalPages" + result.getTotalPages());
        //전체 데이터 수
        System.out.println("Total Contents Count" + result.getTotalElements());
        //현재 페이지 번호
        System.out.println("Current Page Number" + result.getNumber());
        //다음 페이지 존재 여부
        System.out.println("Next Page" + result.hasNext());
        //시작인지 페이지 여부 : 이전페이지 여부
        System.out.println("Is First Page" + result.isFirst());
    }

//======================================================문제1=============================================================
    @Test
    void findByGenderAndNameLike(){
        usersRepository.findByGenderAndNameContainsOrNameContains(Gender.Female, "w", "m")
                .forEach(users -> System.out.println("이름 w포함 여자 회원 정보 : "+users));
    }

    @Test
    void 문제1(){
        List<Users> usersList = usersRepository.findByNameLikeOrNameLike("%w%", "%m%");
        for(Users users : usersList){
            if(users.getGender().equals(Gender.Female)){
                System.out.println(users);
            }
        }

        //2번째 방법
        usersRepository.findByNameLikeAndGenderOrNameLikeAndGender("%w%", Gender.Female, "%m%", Gender.Female)
                .forEach(users -> System.out.println(users));
    }
//======================================================문제2=============================================================
    @Test
    void countByEmailContains(){
        Long countEmail = usersRepository.countByEmailContains("net");
        System.out.println("net 포함 email : "+ countEmail);
    }

    @Test
    void 문제2(){
        System.out.println("Count : " +  usersRepository.findByEmailLike("%net%").stream().count());
    }
    //======================================================문제3=============================================================
    @Test
    void findByUpdatedAtGreaterThanEqual(){
        usersRepository.findByUpdatedAtGreaterThanEqualAndNameStartingWith(LocalDateTime.now().minusMonths(1L), "J");
    }

    @Test
    void 문제3(){
        LocalDateTime date = LocalDateTime.now().minusMonths(1L);
        usersRepository.findByUpdatedAtGreaterThanEqualAndNameLike(date,"E%").forEach(users -> System.out.println(users));
    }

    //======================================================문제4============================================================
    @Test
    void 문제4(){
        List<Users> users = usersRepository.findTop10ByOrderByCreatedAtDesc();
        for(Users user : users){
            System.out.println("ID = " + user.getId());
            System.out.println(", Name = " + user.getName());
            System.out.println(", Gender = " + user.getGender());
            System.out.println(", CreatedAt = " + user.getCreatedAt());
        }
    }

    //======================================================문제5============================================================

    @Test
    void 문제5(){
        List<Users> users = usersRepository.findByLikeColorAndGender("Red", Gender.Male);
        for(Users user : users){
            String email = user.getEmail();
            String account = email.substring(0, email.indexOf("@"));
            System.out.println("email : " + email + "Email Account : " + account);
        }
    }

    //======================================================문제6============================================================
    @Test
    void 문제6(){
        List<Users> users = usersRepository.findAll();
        for (Users user : users){
            if(user.getUpdatedAt().isBefore(user.getCreatedAt())){
                System.out.println(user);
            }
        }

    }
    //======================================================문제7============================================================
    @Test
    void 문제7(){
        usersRepository.findByGenderAndEmailLikeOrderByCreatedAtDesc(Gender.Female, "%edu%")
                .forEach(users -> System.out.println(users));
    }
//======================================================문제8===========================================================
    @Test
    void 문제8(){
        usersRepository.findAll(
                Sort.by(Sort.Order.asc("likeColor"),
                        Sort.Order.desc("name"))
                ).forEach(users -> System.out.println(users));
    }
//======================================================문제9============================================================
    @Test
    void 문제9(){
        usersRepository.findAll(
                PageRequest.of(0,10,Sort.by(Sort.Order.desc("createdAt")))
                ).forEach(users -> System.out.println(users));
    }
//======================================================문제10============================================================
    @Test
    void 문제10(){
        Pageable pageable = PageRequest.of(1,3);
        Page<Users> result = usersRepository.findByGenderOrderByIdDesc(Gender.Male,pageable);
        result.getContent().forEach(users -> System.out.println(users));
    }
//======================================================문제11============================================================
    @Test
    void 문제11(){
        //기준일
        LocalDate baseDate = LocalDate.now().minusMonths(1L);
        //시작일
        LocalDateTime startDate = baseDate.withDayOfMonth(1).atTime(0,0,0);
        //종료일
        LocalDateTime lastDate = baseDate.withDayOfMonth(baseDate.lengthOfMonth()).atTime(23,59,59);
        //검색
        System.out.println("startDate = " + startDate + "lastDate" + lastDate);
        usersRepository.findByCreatedAtBetween(startDate,lastDate).forEach(users -> System.out.println(users));
    }

//========================================================================
    //test 강의 내용
//    @Autowired
//    UsersRepository usersRepository;

//    @Test
//    @DisplayName("Users 테이블 입력_테스트")
//    void inputUsers(){
//        Users users = Users.builder()
//                .name("홍길동")
//                .email("test@test.com")
//                .gender(Gender.Male)
//                .likeColor("Red")
//                .build();
//        usersRepository.save(users);
//
//    }
//
//    @Test
//    @DisplayName("Users 테이블 수정_테스트")
//    void usersUpdate(){
//        Users users = Users.builder()
//                .id(1L)
//                .name("홍길순")
//                .email("test@test.com")
//                .gender(Gender.Female)
//                .likeColor("Yellow")
//                .build();
//        usersRepository.save(users);
//    }

    //=========================================게시판 페이징 처리===========================================

}
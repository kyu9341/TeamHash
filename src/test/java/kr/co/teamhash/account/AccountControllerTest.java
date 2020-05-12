package kr.co.teamhash.account;

import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Transactional // 테스트 완료 후 자동으로 롤백처리
@SpringBootTest
@AutoConfigureMockMvc //  MVC테스트 외 모든 설정을 같이 올림., @SpringBootTest와 함께 사용
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @DisplayName("회원 가입 처리 - 입력값 오류")
    @Test
    void signUpSubmit_with_wrong_input() throws Exception {
        mockMvc.perform(post("/sign-up")
                .param("nickname", "teamhash")
                .param("email", "email...")
                .param("password", "1234")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("account/sign-up"))
                .andExpect(unauthenticated());
                // 회원가입이 실패했으므로 인증되지 않은 상태
    }

    @DisplayName("회원 가입 처리 - 입력값 정상")
    @Test
    void signUpSubmit_with_correct_input() throws Exception{
        mockMvc.perform(post("/sign-up")
                .param("nickname", "teamhash")
                .param("email", "teamhash@email.com")
                .param("password", "123456")
                .with(csrf()))
                .andExpect(status().is3xxRedirection()) // 리다이렉션 응답
                .andExpect(view().name("redirect:/main"));
                // .andExpect(authenticated().withUsername("teamhash")); // teamhash 라는 username 으로 인증이 되었는지 확인

        // 패스워드가 정상적으로 인코딩되었는지 확인
        Account account = accountRepository.findByEmail("teamhash@email.com");
        assertNotNull(account);
        assertNotEquals(account.getPassword(), "123456"); // 입력된 비밀번호와 다르면 인코딩이 된 것.


    }


}
package kr.co.teamhash.project.validator;

import kr.co.teamhash.domain.repository.AccountRepository;
import kr.co.teamhash.project.form.MemberForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class MemberValidator implements Validator {

    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return MemberForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MemberForm memberForm = (MemberForm) target;
        String nickname = memberForm.getMemberNickname();
        if (!accountRepository.existsByNickname(nickname)) {
            errors.rejectValue("members", "wrong.value","존재하지 않는 닉네임입니다.");
        }
    }
}

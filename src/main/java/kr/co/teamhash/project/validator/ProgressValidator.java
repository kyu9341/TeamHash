package kr.co.teamhash.project.validator;

import kr.co.teamhash.project.form.ProgressForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
@Component
public class ProgressValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ProgressForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProgressForm progressForm = (ProgressForm) target;
        String progress = progressForm.getProgress();
        log.info("progress : " + progress);
        int progressPer = Integer.parseInt(progress);
        if (progressPer < 0 || progressPer > 100) {
            errors.rejectValue("progress", "wrong.value","0 ~ 100의 값만 입력하세요.");
        }
    }
}

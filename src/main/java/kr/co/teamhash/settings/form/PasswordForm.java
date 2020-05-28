package kr.co.teamhash.settings.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class PasswordForm {

    @Length(min = 6, max = 50)
    private String newPassword;

    @Length(min = 6, max = 50)
    private String newPasswordConfirm;

}

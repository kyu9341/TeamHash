package kr.co.teamhash.settings;

import kr.co.teamhash.domain.entity.Account;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class Profile {

    @Length(max = 35)
    private String introduction;

    @Length(max = 50)
    private String school;

    private String profileImage;

    public Profile(Account account) {
        this.introduction = account.getIntroduction();
        this.school = account.getSchool();
        this.profileImage = account.getProfileImage();
    }
}

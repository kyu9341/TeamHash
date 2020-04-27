package kr.co.teamhash.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

//권한 설정을 위한 Enum
@AllArgsConstructor
@Getter
public enum Role {

    MEMBER("ROLE_MEMBER"),
    ADMIN("ROLE_ADMIN");

    private String value;

}
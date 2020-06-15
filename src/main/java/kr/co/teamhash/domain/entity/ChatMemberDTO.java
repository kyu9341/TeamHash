package kr.co.teamhash.domain.entity;

import lombok.*;


// JavaScript에서 Account객체를 JSON 변환 시 무한 루프에 빠지는 문제를 해결하기 위해
// DTO 객체를 생성하여 전송해준다
@Getter @Setter  @AllArgsConstructor @NoArgsConstructor
public class ChatMemberDTO {

    private String email;
    
    private String nickname;

    private String introduction;

    private String school;

    private String profileImage;
}
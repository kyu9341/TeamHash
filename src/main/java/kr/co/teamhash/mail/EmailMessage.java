package kr.co.teamhash.mail;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailMessage {

    private String to; // 수신인

    private String subject; // 제목

    private String message; // 메세지

}

# Board

게시판 구현

https://velog.io/@max9106/series/Spring-Boot-%EA%B0%84%EB%8B%A8%ED%95%9C-%EA%B2%8C%EC%8B%9C%ED%8C%90-%EB%A7%8C%EB%93%A4%EA%B8%B0


# Controller

## BoardController.java
url을 통해 요청을 매핑하는 handler를 @Controller 어노테이션을 이용하여 구현  

루트 요청일 경우 메인페이지 list.html로 연결해주고, 글쓰기를 클릭하면 write.html로 연결해준다.  

Service를 주입받아서 사용한다.

# domain
## Entity 
DB 테이블과 매핑되는 객체
### Board.java  

데이터 조작 시 자동으로 날짜를 수정해주는 JPA의 Auditing 기능을 사용하는 TimeEntity를 extends함  

## Repository

데이터 조작을 담당하며 interface로 생성됨 
extends JpaRepository

# Service
repository를 이용하여 service 구현  
실제로 비즈니스 로직을 시행해주는 역할

## BoardService.java

form에서 내용 입력 후, 등록을 누르면 post형식으로 요청이 오고 BoardService의 savePost를 실행하므로 savePost를 구현해야 함  

savePost는 Repository를 통해 실제로 데이터를 저장한다.

# DTO
Controller와 Service 사이에서 데이터를 주고 받는 역할  

## BoardDto.java   
DTO를 통해 service의 savePost에서 Repository에 데이터를 저장  

toEntity()는 dto에서 필요한 부분을 빌더패턴을  통해 entity로 만드는 역할이다.




## 오류
lombok에 오류 발생하여 builder()를 찾을 수 없다

### 해결방법
lombok extension 설치 후 적용


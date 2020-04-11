

### org.springframework.boot.web.server.PortInUseException: Port 8080 is already in use

- 톰캣을 실행시키는데 다음과 같은 에러가 발생한다.
- 이미 8080포트가 사용중이라고 한다.
- 자주 볼 것 같은 에러이니 정리를 해두자.

- 에러 전문
```
org.springframework.boot.web.server.PortInUseException: Port 8080 is already in use
	at org.springframework.boot.web.embedded.tomcat.TomcatWebServer.start(TomcatWebServer.java:213) ~[spring-boot-2.2.6.RELEASE.jar:2.2.6.RELEASE]
	at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.startWebServer(ServletWebServerApplicationContext.java:297) ~[spring-boot-2.2.6.RELEASE.jar:2.2.6.RELEASE]
	at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.finishRefresh(ServletWebServerApplicationContext.java:163) ~[spring-boot-2.2.6.RELEASE.jar:2.2.6.RELEASE]
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:553) ~[spring-context-5.2.5.RELEASE.jar:5.2.5.RELEASE]
	at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:141) ~[spring-boot-2.2.6.RELEASE.jar:2.2.6.RELEASE]
	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:747) ~[spring-boot-2.2.6.RELEASE.jar:2.2.6.RELEASE]
	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:397) ~[spring-boot-2.2.6.RELEASE.jar:2.2.6.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:315) ~[spring-boot-2.2.6.RELEASE.jar:2.2.6.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1226) ~[spring-boot-2.2.6.RELEASE.jar:2.2.6.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1215) ~[spring-boot-2.2.6.RELEASE.jar:2.2.6.RELEASE]
	at com.kwon.demospringmvc.DemospringmvcApplication.main(DemospringmvcApplication.java:11) ~[classes/:na]
```
- 해결 방법으로는 사용중이 아닌 다른 포트번호를 사용하거나, 현재 8080포트로 동작중인 프로세스를 강제로 종료하는 방법이 있다.
  - 나는 포트번호를 바꾸고 싶지 않으니 강제로 8080포트의 프로세스를 종료시킬 것이다.

- cmd에서 netstat 라는 명령어를 사용하면 현재 TCP/IP 네트워크 연결 상태를 확인할 수 있다.
- `netstat -ano` 명령어로 어떤 프로세스가 8080포트를 사용중인지 확인해보자.
  - -a : 모든 연결 및 수신 대기 포트를 표시
  - -n : 주소 및 포트 번호를 숫자 형식으로 표시
  - -o : 각 연결의 소유자 프로세스 ID를 표시


  <div style="width: 500px; height: 120px;">
      <img src="https://github.com/kyu9341/TeamHash_Practice/blob/master/kwon/image/cmd1.png" style="width: 600px
      ; height: 80px;">
  </div>

- 위와 같이 `35436` 라는 PID(프로세스 아이디)의 프로세스가 8080포트를 사용중인 것을 볼 수 있다.
- 프로세스를 종료하는 방법은 두 가지가 있다.
1. 작업관리자를 열어 **세부정보** 로 이동하면 현제 프로세스들의 PID를 포함한 여러 정보를 볼 수 있다. 여기서 8080포트를 사용중이었던 `35436`이라는 PID를 가지는 프로세스를 종료시켜주면 된다.
2. cmd에서 taskkill 명령어를 사용하여 종료시킬 수 있다. `taskkill /f /pid 35436`과 같이 종료하고 싶은 PID를 함께 입력하면 원하는 프로세스를 종료할 수 있다.

- 만약 리눅스 기반 환경이라면
  - `lsof -i :"포트 번호"`를 통해 프로세스 번호를 찾고 `kill -9 "프로세스 번호"`로 프로세스를 종료할 수 있다.
  -  또는 `lsof -i tcp:8080`명령어로 확인하고 `kill $(lsof -t -i:8080)`명령으로 프로세스를 종료할 수 있다.



> 참조
> <https://it-jin-developer.tistory.com/16>
> <https://seablue.tistory.com/95>
> <https://new93helloworld.tistory.com/138>

## 스프링 부트 - 인메모리 데이터베이스
- 인메모리 데이터베이스는 애플리케이션 서버의 메모리를 이용하는 데이터베이스 시스템이다.
  - 데이터베이스 서버가 꺼지게 되면 저장된 데이터가 모두 사라진다.
- 스프링 부트가 지원하는 인-메모리 데이터베이스는 다음과 같이 세가지가 있다.
  - H2
  - HSQL
  - Derby

- Spring-JDBC가 클래스패스에 있으면 자동 설정이 필요한 빈을 설정해준다.
  - DataSource  
  - JdbcTemplate

- 의존성 추가
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>

<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```
- H2 db를 의존성에 추가하고 다른 설정을 하지 않으면 자동으로 H2 db를 기본 데이터베이스로 채택한다.
- spring-boot-starter-jdbc 의존성을 추가하면 DataSource, JdbcTemplate를 빈 주입으로 사용 가능하다.

---

- `SpringbootjdbcApplication.java`
```java
@SpringBootApplication
public class SpringbootjdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootjdbcApplication.class, args);
    }

}
```
- `ApplicationRunner.java`
```java
@Component
public class H2Runner implements ApplicationRunner {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try(Connection connection = dataSource.getConnection()){
            System.out.println(connection.getMetaData().getURL());
            System.out.println(connection.getMetaData().getUserName());

            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE USER(ID INTEGER NOT NULL, name VARCHAR(255), PRIMARY KEY (id))";
            statement.executeUpdate(sql);
        }

        jdbcTemplate.execute("INSERT INTO USER VALUES (1, 'kwon')");
    }
}
```
- dataSource를 사용하여 H2에 접속해 쿼리를 수행한다.
- jdbcTemplate을 사용하면 좀 더 편리하게 쿼리를 작성할 수 있다.

- `application.properties`에 `spring.h2.console.enabled=true`를 추가하면 h2콘솔을 이용할 수 있다.
- `http://localhost:8080/h2-console`로 접속하여 아래와 같이 JDBC URL을 `jdbc:h2:mem:testdb`로 입력하고 connect를 누르면 접속할 수 있다.

<div style="width: 450px; height: 310px;">
    <img src="https://github.com/kyu9341/TeamHash_Practice/blob/master/kwon/image/jdbc_1.png" style="width: 450px
    ; height: 310px;">
</div>


- 위의 소스 코드에서 작성한 쿼리문이 실행되어 USER테이블이 생성되고 하나의 행이 정상적으로 삽입된 것을 볼 수 있다.
<div style="width: 540px; height: 300px;">
    <img src="https://github.com/kyu9341/TeamHash_Practice/blob/master/kwon/image/jdbc_2.png" style="width: 540px
    ; height: 300px;">
</div>


> 참조
> <https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8/dashboard>

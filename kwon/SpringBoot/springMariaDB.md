## 스프링 부트 - DBCP, MariaDB 연동하기
- **DBCP(Database Connection Pool)** : DataBase와 Connection을 맺고 있는 객체를 관리하기 위한 Connection
- DB와 연결된 커넥션(connection)을 미리 생성해서 풀(pool)속에 저장해두고 있다가 필요할 때 가져다 쓰고 반환한다.
- 미리 생성해두기 때문에 데이터베이스에 부하를 줄이고 유동적으로 연결을 관리할 수 있다.
- DBCP는 애플리케이션의 성능에 큰 영향을 미친다.
- 스프링 부트는 HikariCP를 기본 DBCP로 사용한다.

---
- MariaDB는 설치가 되어있다고 가정하고 진행한다.

- 의존성 추가(MariaDB)
```xml
<dependency>
    <groupId>org.mariadb.jdbc</groupId>
    <artifactId>mariadb-java-client</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
```


- `appication.properties`
```properties
# 커넥션 객체의 최대 수를 4개로 설정하겠다는 의미
spring.datasource.hikari.maximum-pool-size=4

spring.datasource.driverClassName=org.mariadb.jdbc.Driver
# database명이 springboot이다.
spring.datasource.url=jdbc:mariadb://localhost:3307/springboot
# 자신의 userid (루트 계정이라면 root)
spring.datasource.username=userid
# 자신의 password
spring.datasource.password=password
```

- `SpringbootjdbcApplication.java`
```java
@SpringBootApplication
public class SpringbootjdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootjdbcApplication.class, args);
    }

}
```

- `MariaDBRunner.java`
```java
@Component
public class implements ApplicationRunner {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try(Connection connection = dataSource.getConnection()){
            System.out.println(dataSource.getClass()); // 어떤 DBCP를 사용하는지 확인
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

- `MySQL Client (MariaDB 10.4 (x64))`를 사용하여 접속하여 확인하면 다음과 같이 생성한 테이블과 삽입한 행을 확인할 수 있다.

<div style="width: 358px; height: 155px;">
    <img src="https://github.com/kyu9341/TeamHash_Practice/blob/master/kwon/image/maria1.png" style="width: 358px
    ; height: 155px;">
</div>


> 참조
> <https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8/dashboard>

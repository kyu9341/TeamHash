## 스프링 부트 - Spring-Data-JPA

#### ORM과 JPA
- **ORM(Object-Relational Mapping)**
  - 객체와 릴레이션을 맵핑할 때 발생하는 개념적 불일치를 해결하는 프레임워크
  - 객체와 DB의 테이블이 매핑을 이루는 것. 즉, 객체가 테이블이 되도록 매칭시키는 것을 말한다.
  - ORM을 이용하면 SQL Query가 아닌 직관적인 코드, 메소드로서 데이터를 조작할 수 있다.

- **JPA(Java Persistence API)** : ORM을 위한 자바 (EE) 표준
  - 자바 ORM기술에 대한 API표준 명세를 말한다.
  - JPA는 ORM을 사용하기 위한 인터페이스를 모아둔 것이며, JPA를 구현한 Hibernate, EclipeLink, DataNucleus같은 ORM프레임 워크를 사용해야 한다.
- Spring-Data-JPA는 JPA를 쉽게 사용하기 위해 스프링에서 제공하는 프레임 워크이다.
  - 추상화 정도 : Spring-Data-JPA -> JPA -> Hibernate -> DataSource (구체화)
    - DataSource : 스프링과 연결된 DB를 연결하는 인터페이스

#### Spring-Data-JPA 연동 (MariaDB)

- 의존성 추가
```xml
<dependency>
    <groupId>org.mariadb.jdbc</groupId>
    <artifactId>mariadb-java-client</artifactId>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```
- H2 데이터베이스를 추가하는 이유는 인메모리 데이터베이스로서 테스트를 위해 사용된다.

- `appication.properties` 마리아 DB 설정 추가
```properties
spring.datasource.driverClassName=org.mariadb.jdbc.Driver
# database명이 springboot이다.
spring.datasource.url=jdbc:mariadb://localhost:3307/springboot
# 자신의 userid (루트 계정이라면 root)
spring.datasource.username=userid
# 자신의 password
spring.datasource.password=password
```

---

- 소스 코드
- `SpringbootjpaApplication.java`
```java
@SpringBootApplication
public class SpringbootjpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootjpaApplication.class, args);
    }

}
```

- `account/Account.java`
```java
package com.kwon.springbootjpa.account;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Account {
    // @GeneratedValue : Primary Key의 값을 자동 생성하기 위해 명시하는데 사용되는 어노테이션
    @Id @GeneratedValue
     private Long id;

    private String username;

    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) &&
                Objects.equals(username, account.username) &&
                Objects.equals(password, account.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }
}

```
- `@Entity` : 엔티티 클래스임을 지정하며 DB 테이블과 매핑하는 객체를 나타내는 어노테이션.
  - DB상에서 table로서 표현
- `@Id` : entity의 PRIMARY KEY를 나타냄
- `@GeneratedValue` : PRIMARY KEY의 값을 자동 생성하기 위해 명시하는 어노테이션

- `AccountRepository.java` 인터페이스
```java
// <Entity Type, Id Type>
public interface AccountRepository extends JpaRepository<Account, Long> {
    // 해당 db의 username에 대한 객체를 반환
    Account findByUsername(String username);
}
```
- AccountRepository의 구현체를 따로 작성하지 않아도 Spring-Data-JPA가 자동적으로 Username의 객체를 받아 자동적으로 DB Table과 매핑한다.


- `AccountRepositoryTest.java` 테스트 코드
```java
@RunWith(SpringRunner.class)
@DataJpaTest // 슬라이싱 테스트 : Repository와 관련된 빈들만 등록을 하여 테스트를 만드는 것
public class AccountRepositoryTest { // 슬라이싱 테스트에서는 인메모리 데이터베이스가 필요
    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void di() throws SQLException {
        try(Connection connection = dataSource.getConnection()){
            DatabaseMetaData metaData = connection.getMetaData();
            System.out.println(metaData.getURL());
            System.out.println(metaData.getDriverName());
            System.out.println(metaData.getUserName());
        }
    }

    @Test
    public void accountTest(){
        Account account = new Account();
        account.setUsername("kwon");
        account.setPassword("1234");

        Account newAccount = accountRepository.save(account);

        assertThat(newAccount).isNotNull();

        Account existingAccount = accountRepository.findByUsername(newAccount.getUsername());
        assertThat(existingAccount).isNotNull();

        Account nonExistingAccount = accountRepository.findByUsername("kyu");
        assertThat(nonExistingAccount).isNull();
    }

}
```
- `@DataJpaTest` : 슬라이싱 테스트 시 필요한 빈을 등록시키고 의존성을 추가한다.
- `@DataJpaTest`를 통한 테스트 시에는 H2 인메모리 데이터베이스를 사용하고, `@SpringBootTest`를 사용하여 테스트를 하는 경우 모든 테스트에 필요한 빈을 등록하므로 MariaDB를 사용한다.



> 참조
> <https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8/dashboard>
> <https://victorydntmd.tistory.com/195>
> <https://engkimbs.tistory.com/790?category=767865>

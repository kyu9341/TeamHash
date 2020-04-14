## 스프링 부트 - 데이터베이스 초기화

#### JPA를 사용한 데이터베이스 초기화

- `appication.properties`
```properties
spring.datasource.driverClassName=org.mariadb.jdbc.Driver
# database명이 springboot이다.
spring.datasource.url=jdbc:mariadb://localhost:3307/springboot
# 자신의 userid (루트 계정이라면 root)
spring.datasource.username=root
# 자신의 password
spring.datasource.password=asas1207

spring.jpa.hibernate.ddl-auto=update
# 기본값 : false
spring.jpa.generate-ddl=true
spring.jpa.show-sql=true
```
- **spring.jpa.hibernate.ddl-auto** 의 옵션에는 다음과 같은 값들을 넣을 수 있다.
  - `update` : 기존의 스키마를 유지하며 JPA에 의해 변경된 부분만 추가한다.
  - `validate` : 엔티티와 테이블이 정상적으로 매핑되어있는지만 검증한다.
  - `create` : 기존에 존재하는 스키마를 삭제하고 새로 생성한다.
  - `create-drop` : 스키마를 생성하고 애플리케이션이 종료될 때 삭제한다.
  - `none` : 초기화 동작을 하지 않는다.
- **spring.jpa.generate-ddl** 은 위의 **spring.jpa.hibernate.ddl-auto** 속성을 사용할지 말지를 결정하는 옵션이다. 기본값이 false이기 JPA에 의한 자동 초기화 기능을 사용하려면 true로 바꾸고 사용한다.
- **spring.jpa.show-sql** 를 true로 하면 JPA가 생성한 SQL문을 보여준다.

---

```
spring.jpa.hibernate.ddl-auto=create
spring.jpa.generate-ddl=true
spring.jpa.show-sql=true
```
- 다음 이미지는 위와 같이 설정을 주고 실행한 결과이다.

<div style="width: 1050px; height: 120px;">
    <img src="https://github.com/kyu9341/TeamHash_Practice/blob/master/kwon/image/dbinit1.png" style="width: 1050px
    ; height: 120px;">
</div>
- 기존에 존재하는 테이블이 drop되고 새로운 테이블이 생성되는 것을 볼 수 있다.

---

- 서비스 운영 시에는 다음과 같은 설정을 주로 사용한다.
```
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.generate-ddl=false
spring.jpa.show-sql=true
```
- 위와 같이 설정한 후 아래와 같이 기존 스키마를 가지는 Account클래스에 email필드를 추가한다.
<div style="width: 450px; height: 50px;">
    <img src="https://github.com/kyu9341/TeamHash_Practice/blob/master/kwon/image/dbinit3.png" style="width: 450px
    ; height: 50px;">
</div>

- 다시 서버를 실행하면 다음과 같은 에러가 발생한다.

<div style="width: 1200px; height: 150px;">
    <img src="https://github.com/kyu9341/TeamHash_Practice/blob/master/kwon/image/dbinit2.png" style="width: 1200px
    ; height: 150px;">
</div>
- account 테이블에 email컬럼을 찾을 수 없다고 하고 애플리케이션이 바로 종료되는 것을 볼 수 있다.

---

- 이번에는 다음과 같이 설정하고 실행해보자.(개발할 때만 이런 설정을 주로 한다고 한다.)
```
spring.jpa.hibernate.ddl-auto=update
spring.jpa.generate-ddl=true
spring.jpa.show-sql=true
```

- 다음과 같이 테이블에 email 컬럼이 추가되어 alter 가 적용된 것을 볼 수 있다.
<div style="width: 600px; height: 100px;">
    <img src="https://github.com/kyu9341/TeamHash_Practice/blob/master/kwon/image/dbinit4.png" style="width: 600px
    ; height: 100px;">
</div>

div style="width: 550px; height: 50px;">
    <img src="https://github.com/kyu9341/TeamHash_Practice/blob/master/kwon/image/dbinit5.png" style="width: 550px
    ; height: 50px;">
</div>
- 위와 같이 변경 사항이 적용된 것을 볼 수 있다. 하지만 이미 존재하는 컬럼의 이름을 바꾸는 것은 JPA가 자동으로 업데이트 해주지는 않는다.


- `Account.java`
```java
@Entity
public class Account {
    // @GeneratedValue : Primary Key의 값을 자동 생성하기 위해 명시하는데 사용되는 어노테이션
    @Id @GeneratedValue
     private Long id;

    private String username;

    private String password;

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

#### SQL 스크립트를 사용한 데이터베이스 초기화
- 다음과 같이 schema.sql 또는 schema-${platform}.sql 과 같은 파일을 생성하여 자동으로 데이터베이스를 초기화할 수 있다.

- `resources/schema.sql`
```sql
drop table account if exists
drop sequence if exists hibernate_sequence
create sequence hibernate_sequence start with 1 increment by 1
create table account (id bigint not null, email varchar(255), password varchar(255), username varchar(255), primary key (id))
```
- 위와 같이 SQL 스크립트 파일로 초기화를 한다면 아래와 같이 `application.properties`를 설정해도 스프링 부트에서 자동으로 `schema.sql`의 sql을 실행하기 때문에 테이블이 삭제되었다가 다시 생성된다.
```
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.generate-ddl=false
spring.jpa.show-sql=true
```

> 참조
> <https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8/dashboard>
> <https://engkimbs.tistory.com/794?category=767865>

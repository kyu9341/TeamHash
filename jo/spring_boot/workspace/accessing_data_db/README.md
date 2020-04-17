# Accessing Data with DataBase

외부 데이터 베이스와 연결하여 동작하는 Spring Application을 제작하는 과정을 진행한다.  

https://spring.io/guides/gs/accessing-data-mysql/

## Create the @Entity Model

엔터티 모델이 필요하다.

Hibernate는 자동적으로 해당 엔터티를 테이블에 전송할 것이다.  

## Create the Repository  

유저의 기록을 잡아 둘 저장소가 필요하다. 

스프링은 자동적으로 저장소 인터페이스를 bean 안에 구현해준다.  

## Create a Controller  

어플리케이션에서 HTTP 요청을 핸들링 할 컨트롤러가 필요하다.  

요청 방식에는 POST와 GET이 있는데 이 두가지를 모두 포함하는 @RequestMapping 어노테이션이 있다.  

## Create an Application Class

이전 내용과 같으므로 생략  

spring application을 실행시켜줄 클래스가 필요하다.  

## Make Some Security Changes

우리가 생산 환경을 구성할 때 SQL 인젝션 공격을 당할 수 있다.

해커가 DROP TABLE 을 주입하거나 파괴적인 SQL 명령을 내릴 수 있다.

그래서 봉나 단계로 우리는 유저에게 들어나기 전에 데이터베이스를 변경해야 한다.

???

When you are on a production environment, you may be exposed to SQL injection attacks. A hacker may inject DROP TABLE or any other destructive SQL commands. So, as a security practice, you should make some changes to your database before you expose the application to your users.

The following command revokes all the privileges from the user associated with the Spring application:

~~~
mysql> revoke all on db_example.* from 'springuser'@'%';
~~~

Now the Spring application cannot do anything in the database.

The application must have some privileges, so use the following command to grant the minimum privileges the application needs:

~~~
mysql> grant select, insert, delete, update on db_example.* to 'springuser'@'%';
~~~

Removing all privileges and granting some privileges gives your Spring application the privileges necessary to make changes to only the data of the database and not the structure (schema).

When you want to make changes to the database:

1. Regrant permissions.

2. Change the spring.jpa.hibernate.ddl-auto to update.

3. Re-run your applications.

Then repeat the two commands shown here to make your application safe for production use again. Better still, use a dedicated migration tool, such as Flyway or Liquibase.
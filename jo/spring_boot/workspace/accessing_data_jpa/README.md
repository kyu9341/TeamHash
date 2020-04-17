# Accessing Data with JPA

데이터베이스와 연계하여데이터를 저장하고 검색하는 과정을 진행한다.
해당 어플리케이션은 Spring Data JPA를 사용하여 진행된다.

https://spring.io/guides/gs/accessing-data-jpa/

## What we will build

이번 어플리케이션에서 저장할 데이터는 데이터 베이스 메모리에 기반한 Customer POJOs(Plain Old Java Objects)이다.

## Simple Entity Customer.java

id, firstName, lastName의 값을 가진 간단한 Customer 클래스를 생성한다.

해당 클래스는 두개의 생성자가 있으며 하나는 JPA를 위해 존재한다. 이 생성자는 직접 사용해서는 안되기 때문에 protected로 접근 지정자를 설정해두었다.

다른 하나는 Customer의 인스턴스를 생성하여 데이터베이스에 저장하기 위해 생성되었다. 

Customer 클래스는 @Entity 어노테이션과 함께 있는데 이는 JPA entitiy임을 나타내는 것이다.  

Customer 객채의 속성 중 id는 @Id 어노테이션과 함께 선언되었으며 이때문에 JPA는 이것을 ID 객체로 인식한다.

또한 id는 @GeneratedValue 와 함께 어노테이션 되어있는데 이는 ID가 자동으로 생성되는것을 나타낸다.

다른 두개의 속성인 firstName과 LastName은 어노테이션 되지 않았는데 이는  It is assumed that they are mapped to columns that share the same names as the properties themselves.

마지막으로 toString()메서드는 customer의 속성을 나타내기 편리하다.

## Create Simple Queries

Spring Data JPA는 데이터를 관계형 데이터베이스에 저장하기 위해 초첨이 맞춰져있다. 이것은 가장 효과적인 저장소의 자동적인 구현의 방법이다.

CustomerRepository는 CrudRepository 인터페이스를 상속받아 구현된다.  

The type of entity and ID that it works with, Customer and Long, are specified in the generic parameters on CrudRepository  

CrudRepository, CustomerRepository을 상속받으면 몇몇의 메소드를 사용할 수 있다. 저장하고 지우고 찾는 그러한 기능들을 말이다.

자바와 같은 타입의 어플리케이션에서는 우리는 반드시 Repository를 구현해야 한다.  

Spring Data JPA의 강점으로 우리는 저장소 인터페이스를 직접 구현할 필요가 없다. 

## Create an Application Class

- @SpringBootApplication 어노테이션은 아래의 어노테이션을 모두 포함하고 있다.

- @Configuration: Tags the class as a source of bean definitions for the application context.

- @EnableAutoConfiguration: Tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings. For example, if spring-webmvc is on the classpath, this annotation flags the application as a web application and activates key behaviors, such as setting up a DispatcherServlet.

- @ComponentScan: Tells Spring to look for other components, configurations, and services in the com/example package, letting it find the controllers.

메인 메서드 안에서 사용되는 SpringApplication.run() 메서드는 어플리케이션을 실행시키기 위한 매서드이다.

이것에는 단 한줄의 XML 코드도 없는데 이곳에는 web.xml파일이 없으며 해당 웹 어플리케이션은 100% 자바로 이루어져 있기 때문이다. 우리는 하부구조의 구성을 다루지 않아도 된다.

이제 콘솔에 로그를 남기기 위한 이니셜라이져 클래스가 하나 필요하다. 위에서 생성한 어플리케이션 클래스를 수정하자.

./mvnw spring-boot:run으로 실행

# 마치며

해당 가이드는 JPA를 활용하여 H2 데이터베이스에 접근한 뒤 데이터를 저장하고 검색하거나 출력하는 과정을 설명한 것 같다.  

mariaDB와 연동할 예정인데 위의 내용을 적용할 수 있을까?
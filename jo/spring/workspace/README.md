java 프로젝트나 jsp 프로젝트와는 달리 spring 프로젝트는 생성 과정부터 까다로운 점이 있으므로
각 파일들의 역할을 파악할 것

# src/main
## java
java파일 관리
실제로 자바 프로그램을 작성하는 공간

## resources
자원파일 관리
자바 프로그램을 생성 하면서 보조적으로 필요한 파일들을 저장하는 공간
스프링 설정 파일(XML) 또는 프로퍼티 파일 등이 관리된다.


# pom.xml
메이븐 설정파일로 메이븐은 라이브러리를 연결해주고 빌드를 위한 플랫폼이다.

# 객체 생성
spring framework에서는 new를 이용하여 객체를 생성하지 않고 applicationContext.xml의 bean을 통해 객체를 생성하여 사용한다.


# DI (Dependency injection)

## DI란?

객체를 분리하여 개발한 뒤 필요할때 주입하여 사용 하는 것

## 스프링 DI 설정 방법

스프링 설정파일(applicationContext.xml) 에서 설정된 Bean을 genericXmlApplication으로 Spring Contatiner 안에 객체를 생성하여 getBean()메서드로 호출하여 사용한다.

Spring Container 안에서도 객체들끼리 서로 의존하고 있는 객체도 있다. 즉 객체가 주입이 된 상태 DI라고 할 수 있다.

### 객체 생성

~~~xml
<bean id="studentDao" class="ems.member.dao.StudentDao" ></bean>
~~~

### 객체 주입

~~~xml
<bean id="registerService" class="ems.member.service.StudentRegisterService">
		<constructor-arg ref="studentDao" ></constructor-arg>
</bean>
~~~

# 다양한 의존 객체 주입

## 생성자를 이용한 의존 객체 주입

### Java
~~~java
public StudentRegisterService(StudentDao studentDao){
	this.studentDao = studentDao;
}
~~~

### applicationContext.xml
~~~xml
<bean id="studentDao" class="ems.member.dao.StudentDao"></bean>

<baen id="registerService" class="ems.member.service.StudentRegisterService" >
	<constructor-arg ref="studentDao"></constructor-arg>
</bean>
~~~

## setter를 이용한 의존 객체 주입
name 속성을 사용
### Java
~~~java
public void setJdbcUrl(Stirng jdbcurl){
	this.jdbcUrl = jdbcUrl;
}

public void setUserId(Stirng userId){
	this.userId = userId;
}

public void setUserPw(Stirng userPw){
	this.userPw = userPw;
}
~~~

### applicationContext.xml
~~~xml
<bean id="dataBaseConnectionInfoDev" class="ems.member.DataBaseConnectionInfo">
	<property name="jdbcUrl" value""/>
	<property name="userId" value""/>
	<property name="userPw" value""/>
</baen>
~~~

## List타입 의존 객체 주입

### Java
~~~java
public void setDevelopers(List<String> developers){
	this.developers = developers;
}
~~~

### applicationContext.xml
~~~xml
<property name="developers">
	<list>
		<value>Cheney.</value>
		<value>Eloy.</value>
		<value>Jasper.</value>
		<value>Dillon.</value>
		<value>Kian.</value>
	</list>
</property>
~~~

## Map 타입 의존 객체 주입

Key와 Value를 사용

### Java

~~~java
public void setAdministrators(Map<String, String> administrators){
	this.administrators = administrators;
}
~~~
### applicationContext.xml

~~~xml
<property name="administrators">
	<map>
		<entry>
			<key>
				<value>Cheney</value>
			</key>
			<value>Cheney@springPjt.org</value>
		</entry>
		
		<entry>
			<key>
				<value>Jasper</value>
			</key>
			<value>jasper@springPjt.org</value>
		</entry>
	</map>

</property>
~~~

# 스프링 설정 파일 분리
파일이 많이질 경우 가독성이 떨어지면 구동에 문제가 생길 수 있기 때문에 나눠준다.

## 스프링 설정 파일 분리
설정 파일인 applicationContext.xml을 나눈다.  

appctx1,appctx2,appctx3으로 나눴을 경우  

### MainClassUseXMLs.java
~~~java
String[] appCtxs = {"classpath:appCtx1.xml", "classpath:appCtx2.xml", "classpath:appCtx3.xml"};
		GenericXmlApplicationContext ctx = 
				new GenericXmlApplicationContext(appCtxs);
		
~~~

또는 설정 파일을 다른 설정파일에서 import 할 수 있다.

### appCtximport.xml
~~~xml
<import resource="classpath:appCtx2.xml"/>
<import resource="classpath:appCtx3.xml"/>
~~~

### MainClassUseXMLsimport.java
~~~java
GenericXmlApplicationContext ctx = 
				new GenericXmlApplicationContext(appCtxs);
~~~

## 빈(Bean)의 범위

### 싱글톤(Singleton)
스프링 설정 파일에 의해 Spring Contatiner가 생성되며 이곳에 빈 객체가 존재하게 된다.

동일한 타입에 대해서는 기본적으로 하나만 생성되며 getBean()매서드를 이용해 빈 객체를 가져오게 되면 동일한 객체가 반환된다.

### 프로토타입(Prototype)
싱글톤의 반대되는 개념이며 개발자가 별도로 설정해 줘야 한다.

~~~xml
<bean id="dependencyBean" class="scope.ex.DependencyBean" scope="prototype"></bean>
~~~
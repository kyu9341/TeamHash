package testpjt2;

import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {

	public static void main(String args[]) {
		//TestApp testapp = new TestApp();
		//testapp.move();
		
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationContext.xml");
		
		TestApp testapp = ctx.getBean("test", TestApp.class);
		
		testapp.move();
		
		ctx.close();
		
	}
}

package scope.ex;

public class DependencyBean {

	private InjectionBean injectionBean;
	
	public DependencyBean(InjectionBean injectionBean) {
		System.out.println("DependencyBean : constructor");
		this.injectionBean = injectionBean;
	}
	
	public void setInjectionBean(InjectionBean injectionBean) {
		System.out.println("DependencyBean : setter");
		this.injectionBean = injectionBean;
	}
	
}
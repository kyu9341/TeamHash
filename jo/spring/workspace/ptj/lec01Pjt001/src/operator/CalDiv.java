package operator;

public class CalDiv implements Calculator {

	@Override
	public int sum(int firstNum, int secondNum) {
		return secondNum != 0 ? (firstNum / secondNum) : 0;
	}

}
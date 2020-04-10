package lec01Pjt001;


import operator.CalAdd;
import operator.CalDiv;
import operator.CalMul;
import operator.CalSub;

public class MainClass {

	public static void main(String[] args) {

		MyCalculator calculator;

		calculator = new MyCalculator(10, 5, new CalAdd());
		calculator.result();

		calculator = new MyCalculator(10, 5, new CalSub());
		calculator.result();

		calculator = new MyCalculator(10, 5, new CalMul());
		calculator.result();

		calculator = new MyCalculator(10, 5, new CalDiv());
		calculator.result();

	}

}

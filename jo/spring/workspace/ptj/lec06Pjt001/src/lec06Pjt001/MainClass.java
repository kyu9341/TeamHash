package lec06Pjt001;

import lec06Pjt001.battery.ChargeBattery;
import lec06Pjt001.toy.ElectronicCarToy;
import lec06Pjt001.toy.ElectronicRobotToy;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ElectronicCarToy carToy = new ElectronicCarToy();
		
		ElectronicRobotToy robotToy = new ElectronicRobotToy();
		
		
		ChargeBattery b = new ChargeBattery();
		
		
		robotToy.setBattery(b);
	}

}

package lec06Pjt001.toy;

import lec06Pjt001.battery.Battery;
import lec06Pjt001.battery.NormalBattery;

public class ElectronicCarToy {
	
	private Battery battery;
	
	public ElectronicCarToy() {
		battery = new NormalBattery();
	}
	
}
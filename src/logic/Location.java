package logic;

import java.io.Serializable;

public class Location implements Serializable {
	
	private String name;
	
	public Location(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}

}

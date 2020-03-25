package SCIT_OS;

public class ListElement {
	private int Value;

	// Constructors
	public ListElement() {
		Value=0;

	}
	public ListElement(int value) {
		Value=value;

	}

	// Getters/Setters
	public int getValue() {
		return Value;
	}

	public void setValue(int value) {
		Value = value;
	}

	public void display() {
		System.out.print(Value+"\n");
	}

}

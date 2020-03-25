package SCIT_OS;

public class SharedListNode {

	private static int counter = 0;

	private int Key;
	private ListElement Data;
	private SharedListNode NextNode;

	// default
	public SharedListNode() {
		Key = 0;
		Data = null;
		NextNode = null;
	}

	public SharedListNode(int value) {
		// unique key value needed
		Key = counter;
		counter++;
		Data = new ListElement(value);
		NextNode = null;
	}

	public SharedListNode(ListElement data) {
		// unique key value needed
		Key = counter;
		counter++;
		setData(data);
		NextNode = null;
	}

	// Getters/Setters
	public int getKey() {
		return Key;
	}

	public void setKey(int key) {
		Key = key;
	}

	public ListElement getData() {
		return Data;
	}

	public void setData(ListElement data) {
		Data = data;
	}

	public SharedListNode getNextNode() {
		return NextNode;
	}

	public void setNextNode(SharedListNode nextNode) {
		NextNode = nextNode;
	}

	public void displayNode() {
		System.out.println(Key + "\t\t" + Data.getValue());
	}

	public void writeSharedListNode() {
		WriteFile.printwriter.println(Key + "\t\t" +Data.getValue());
	}

}
package SCIT_OS;

public class ProcessWaitingQueue{
	private static ProcessNode Front;
	private static ProcessNode Back;
	static int Count;

	
	public ProcessWaitingQueue() {
		Front = null;
		Back = null;
		Count = 0;
	}

	public ProcessWaitingQueue(ProcessNode back) {
		Back = back;
		Front = back;
		Count = 0;
	}

	public static ProcessNode getFront() {
		return Front;
	}

	public static void setFront(ProcessNode head) {
		Front = head;
	}

	public static ProcessNode getBack() {
		return Back;
	}

	public static void setBack(ProcessNode back) {
		Back = back;
	}
	
	public static int getCount() {
		return Count;
	}

	public static void setCount(int count) {
		Count = count;
	}

	public static boolean isEmpty() {

		if (Front != null) {
			return false;
		}
		return true;
	}

	public static void enqueue(Process data) {
		ProcessNode temp = new ProcessNode(data);
		if (isEmpty()) {
			Front = temp;
		} else {
			// If tail is null, this is the second element
			if (Back == null) {
				Back = temp;
				Front.setNextNode(Back);
			} else {
				// More than two elements present
				// If this is the case, set the current tail
				// to point to the newly inserted node
				// Afterwards, set the newly inserted node as the new tail.
				// We need a tail to make sure insertion to our queue can
				// be done in O(1) constant time.
				ProcessNode previousTail = Back;
				ProcessNode newTail = temp;
				previousTail.setNextNode(newTail);
				Back = newTail;
			}
		}
		Count++;
	}

	public static Process dequeue() {
		ProcessNode nodeToReturn = Front;
        if (isEmpty()) {
            return null;
        } else {
            if (Back == null) {
                Front = null;
            } else {
                Front = Front.getNextNode();
            }
            Count--;
        }
        return nodeToReturn.getData();
	}
	public static void display() {
		if (isEmpty() == false) {
			ProcessNode temp = Front;

			while (temp != null) {
				temp.getData().displayShort();
				temp = temp.getNextNode();
			}
		} else {
			System.out.println("Waiting Queue is empty");
		}
	}
	public static void writeProcessWaitingQueue() {
		if (isEmpty() == false) {
			ProcessNode temp = Front;

			while (temp != null) {
				temp.getData().writeProcessShort();
				temp = temp.getNextNode();
			}
		} else {
			WriteFile.printwriter.println("Waiting Queue is empty");
		}
	}
}
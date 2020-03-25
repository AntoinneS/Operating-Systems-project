package SCIT_OS;

public class ProcessNode {
	private Process Data;
	private ProcessNode NextNode;
	
	
	public ProcessNode (){
		Data=new Process();
		NextNode=null;
	}
	
	public ProcessNode(Process data){
		Data=data;
		NextNode=null;
	}
	
	public Process getData() {
		return Data;
	}


	public void setData(Process data) {
		Data = data;
	}


	public ProcessNode getNextNode() {
		return NextNode;
	}


	public void setNextNode(ProcessNode nextNode) {
		NextNode = nextNode;
	}	
	
}

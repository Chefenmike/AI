package group3.world;

import group3.world.definitions.OperatorEnum;

public class ObjectOperator implements ObjectInterface{	
	private final OperatorEnum operator;
	
	public ObjectOperator(OperatorEnum operator) {
		this.operator = operator;
	}
	
	public OperatorEnum getOperator() {
		return operator;
	}
}

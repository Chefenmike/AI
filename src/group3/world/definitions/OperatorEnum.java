package group3.world.definitions;

public enum OperatorEnum {
	AND("and"), OR("or");
	
	private String operator;
	
	private OperatorEnum(String operator) {
		this.operator = operator; 
	}
	
	public static OperatorEnum getOperatorFromString(String operatorString) {
		if(operatorString != null) {
			for(OperatorEnum operator : OperatorEnum.values()) {
				if(operatorString.equalsIgnoreCase(operator.operator)) {
					return operator;
				}
			}
		}
		throw new IllegalArgumentException("No operator with description " + operatorString + " found");
	}
}

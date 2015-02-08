package ed.x07;

/**
 * Información en un nodo de un árbol de expresión.
 * 
 * @author profesor
 *
 */
public class ExpressionComponent {
	
	//	Constantes para indicar el tipo de nodo
	static int OPERATOR_OBJECT = 0;
	static int OPERAND_OBJECT = 1;
	
	//	Crea un nodo "operador" con el operador dado "+", "-", etc.
	static ExpressionComponent createOperator(Character op) {
		
		ExpressionComponent result = new ExpressionComponent();
		
		result.operator = op; result.operand = null;
		
		result.type = ExpressionComponent.OPERATOR_OBJECT;
		
		return result;
	}
	
	//	Crea un nodo "operando" con la información dada
	static ExpressionComponent createOperand(String value) {
		
		ExpressionComponent result = new ExpressionComponent();
		
		result.operator = null; result.operand = Integer.parseInt(value);
		
		result.type = ExpressionComponent.OPERAND_OBJECT;
		
		return result;
	}

	int type;
	
	Character operator;
	Integer operand;
	
	@Override
	public String toString() {
		if (type == ExpressionComponent.OPERATOR_OBJECT) {
			return operator.toString();
		} else {
			return operand.toString();
		}
	}

}

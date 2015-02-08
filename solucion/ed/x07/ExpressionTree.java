package ed.x07;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.StringTokenizer;

import ed.x07.BinaryTree;

/**
 * Árbol binario de expresión sobre operandos enteros mayores o iguales a cero.
 * 
 * Operadores binarios "+", "-", "*" y "/". Operadores unarios "i", "d"
 * (incremento, decremento).
 * 
 * Los operadores unarios se representarán como un árbol de expresión
 * con el sub-árbol izquierdo vacío y el derecho con el operando
 * correspondiente.
 * 
 * La implementación se basa en árboles binarios donde los nodos
 * almacenan objetos del tipo {@link ExpressionComponent}.
 * 
 * @author profesor
 *
 */
public class ExpressionTree extends BinaryTree<ExpressionComponent> {
	
	//	Constante con los caracteres que representan operadores binarios
	private static ArrayList<Character> BINARY_OPERATORS = new ArrayList<Character>(Arrays.asList('d','i','+','-','*','/'));

	//	Constante con los caracteres que representan operadores unarios
	private static ArrayList<Character> UNARY_OPERATORS = new ArrayList<Character>(Arrays.asList('d','i'));
	
	/**
	 * Devuelve la expresión a la izquierda del operador.
	 * 
	 * @return expresión a la izquierda.
	 */
	public ExpressionTree leftHandSide() {
		return (ExpressionTree) left;
	}
	
	/**
	 * Devuelve la expresión a la derecha del operador, o la
	 * expresión a la que aplicar un operador unario.
	 * 
	 * @return
	 */
	public ExpressionTree rightHandSide() {
		return (ExpressionTree) right;
	}
	
	/**
	 * Calcula y devuelve el resultado de la expresión.
	 * 
	 * Calcula (de forma recursiva) el valor del resultado de
	 * evaluar la expresión que el árbol representa.
	 * 
	 * Sólo se permiten valores enteros mayores o iguales a 
	 * cero. Si en algún momento se obtiene un resultado
	 * negativo, se lanza una excepción.
	 *  
	 * De igual forma, se lanza una excepción del tipo indicado
	 * a continuación si se produce una división por cero.
	 * 
	 * @return valor de la expresión
	 * @throws ExprEvaluationException error en la evaluación
	 * @throws DomainException alguno de los resultados es negativo
	 * @throws InvalidOperationException división por cero
	 */	
	public int value() throws ExprEvaluationException {
		//	Si el árbol es un operando, el valor de la expresión
		//	es directamente el valor del operando
		if (content.type == ExpressionComponent.OPERAND_OBJECT) {
			//	Convierte la cadena a entero
			return content.operand.intValue(); 
		} else {
			//	Si es un operador unario, aplicarlo al valor de
			//	la expresión a su derecha
			if (ExpressionTree.UNARY_OPERATORS.contains(content.operator)) {
				//	Calcula el valor del sub-árbol derecho (recursividad)
				int b = rightHandSide().value();
				
				//	Aplica el operando de este árbol al resultado
				switch (content.operator) {
				case 'i':
					return (b + 1);
				case 'd':
					if (b == 0) throw new DomainException("Negative value");
					return (b - 1);
				default:
					throw new ExprEvaluationException("Undefined operator");
				}
			} else {
				//	Es un operador binario, primero resuelve
				//	ambos sub-árboles (expresión izquierda y derecha)
				int a = leftHandSide().value();
				int b = rightHandSide().value();
				
				//	Aplica el operador
				switch (content.operator) {
				case '+':
					return a + b;
				case '-':
					if (b > a) throw new DomainException("Negative result");
					
					return a - b;
				case '*':
					return a * b;
				case '/':
					if (b == 0) throw new InvalidOperationException("Division by zero");
					
					return a / b;
				default:
					throw new ExprEvaluationException("Undefined operator");
				}
			}
		}
	}
	
	/**
	 * Construye un árbol de expresión a partir de una cadena en notación postfija.
	 * 
	 * @param expr expresión a analizar en notación postfija.
	 * @return árbol de expresión resultante.
	 * @throws ExprParsingException error en la expresión/análisis.
	 */
	public static ExpressionTree parsePostfixExpression(String expr) throws ExprParsingException {
		//	Almacena árboles parciales que son combinados con operadores	
		Stack<ExpressionTree> stack = new Stack<ExpressionTree>();
		
		//	Para recorrer por partes la cadena
		StringTokenizer tokens = new StringTokenizer(expr);

		//	Analiza la cadena de principio a fin
		while (tokens.hasMoreTokens()) {
			
			//	Cada componente, sin espacios en blanco	
			String token = tokens.nextToken().trim();
			
			//	Si es un operador
			if (ExpressionTree.BINARY_OPERATORS.contains(token.charAt(0))) {
				//	Unario
				if (ExpressionTree.UNARY_OPERATORS.contains(token.charAt(0))) {
					//	Si es un operador (unario), sacar su operando (árbol parcial) de la pila
					try {
						ExpressionTree a = stack.pop();
						//	Y almacenar el nuevo árbol parcial
						stack.push(new ExpressionTree(token.charAt(0), emptyExpressionTree(), a));
					} catch (EmptyStackException e) {
						throw new ExprParsingException("Expresión incorrecta");
					}					
				} else {
					//	Si es un operador, sacar operandos (árboles parciales) de la pila
					try {
						ExpressionTree a = stack.pop();
						ExpressionTree b = stack.pop();
						//	Y almacenar el nuevo árbol parcial
						stack.push(new ExpressionTree(token.charAt(0), b, a));
					} catch (EmptyStackException e) {
						throw new ExprParsingException("Expresión incorrecta");
					}
				}
			} else {
				//	Si es operando, almacenar el árbol parcial correspondiente
				//
				//	La raíz almacena el token, que en su momento será convertido a entero
				//	cuando se pida el valor del árbol de expresión
				stack.push(new ExpressionTree(token));
			}
		}
		
		//	El resultado está en la cima de la pila y debe ser el único elemento
		if (!stack.isEmpty()) {
			ExpressionTree result = stack.pop();
			
			if (!stack.isEmpty()) {
				throw new ExprParsingException("Expresión incompleta");
			} else {
				return result;
			}
		} else {
			throw new ExprParsingException("Expresión incorrecta");
		}
	}
	
	/**
	 * Devuelve un árbol vacío.
	 * 
	 * @return árbol vacío.
	 */
	public static ExpressionTree emptyExpressionTree() {
		return new ExpressionTree();
	}
	
	//	No se permite al usuario utilizar el constructor de árboles
	//	vacíos; tiene que usar el método factoría anterior.
	private ExpressionTree() {
	}
	
	/**
	 * Construye un árbol de expresión de un operando.
	 * 
	 * @param value operando para el nodo raíz.
	 */
	public ExpressionTree(String value) {
		super(ExpressionComponent.createOperand(value));
	}
	
	/**
	 * Construye un árbol de expresión de un operador.
	 * 
	 * @param op operador en la raíz.
	 * @param left sub-árbol (sub-expresión) izquierdo al operador.
	 * @param right sub-árbol derecho.
	 */
	public ExpressionTree(Character op, ExpressionTree left, ExpressionTree right) {
		super(ExpressionComponent.createOperator(op), left, right);
	}
}

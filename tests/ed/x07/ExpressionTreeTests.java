package ed.x07;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import ed.x07.DomainException;
import ed.x07.ExprEvaluationException;
import ed.x07.ExprParsingException;
import ed.x07.ExpressionComponent;
import ed.x07.ExpressionTree;
import ed.x07.GatheringVisitor;
import ed.x07.InvalidOperationException;


public class ExpressionTreeTests {

	@Test
	public void testNumberOfLeaves() throws ExprParsingException {

		//	En árboles de expresión, los operandos son hojas
		
		ExpressionTree E1 = ExpressionTree.parsePostfixExpression("1 1 +");
		Assert.assertEquals(2, E1.getNumberOfLeaves());
		
		E1 = ExpressionTree.parsePostfixExpression("4 2 / 8 4 / 16 8 / + +");
		Assert.assertEquals(6, E1.getNumberOfLeaves());
		
		E1 = ExpressionTree.parsePostfixExpression("1 i 1 i +");
		Assert.assertEquals(2, E1.getNumberOfLeaves());				
	}

	@Test
	public void testNumberOfNodes() throws ExprParsingException {

		ExpressionTree E1 = ExpressionTree.parsePostfixExpression("1 1 +");
		Assert.assertEquals(3, E1.getNumberOfNodes());
		
		E1 = ExpressionTree.parsePostfixExpression("4 2 / 8 4 / 16 8 / + +");
		Assert.assertEquals(11, E1.getNumberOfNodes());		
	}

	@Test
	public void testValueOfBasics() throws ExprParsingException, ExprEvaluationException {
		
		ExpressionTree E1 = ExpressionTree.parsePostfixExpression("1 1 +");
		Assert.assertEquals(2, E1.value());
		
		E1 = ExpressionTree.parsePostfixExpression("4 2 /");
		Assert.assertEquals(2, E1.value());
		
		E1 = ExpressionTree.parsePostfixExpression("4 2 / 100 +");
		Assert.assertEquals(102, E1.value());
		
		E1 = ExpressionTree.parsePostfixExpression("4 2 / 8 4 / 16 8 / + +");
		Assert.assertEquals(6, E1.value());
	}

	@Test
	public void testValueIntegerDiv() throws ExprParsingException, ExprEvaluationException {
		
		ExpressionTree E1 = ExpressionTree.parsePostfixExpression("2 4 /");
		Assert.assertEquals(0, E1.value());
		
		E1 = ExpressionTree.parsePostfixExpression("14 5 /");
		Assert.assertEquals(2, E1.value());
	}	

	@Test
	public void testValueOfComplex() throws ExprParsingException, ExprEvaluationException {
		
		ExpressionTree E1 = ExpressionTree.parsePostfixExpression("3 3 + 2 2 + * 2 2 + 3 3 + * +");
		Assert.assertEquals(48, E1.value());
		
		E1 = ExpressionTree.parsePostfixExpression("2 4 /");
		Assert.assertEquals(0, E1.value());
	}
	
	@Test(expected = DomainException.class)
	public void testDomainErrors() throws ExprParsingException, ExprEvaluationException {
		//	((10+10)-(20+20)) * (2+2) = (20-40)... = -20 (?!)
		ExpressionTree E1 = ExpressionTree.parsePostfixExpression("10 10 + 20 20 + - 2 2 + *");
		E1.value();
	}
	
	@Test(expected = InvalidOperationException.class)
	public void testDivisionByZero() throws ExprParsingException, ExprEvaluationException {
		//	((1+1)/(1-1))+(5*5) = (2/0)+... ?!
		ExpressionTree E1 = ExpressionTree.parsePostfixExpression("1 1 + 1 1 - / 5 5 * +");
		E1.value();
	}
	
	@Test
	public void preOrder() throws ExprParsingException {
		ExpressionTree E1 = ExpressionTree.parsePostfixExpression("3 3 + 2 2 + * 2 2 + 3 3 + * +");

		GatheringVisitor<ExpressionComponent> V1 = new GatheringVisitor<ExpressionComponent>();
		E1.preOrder(V1);
		
		Assert.assertEquals("[+, *, +, 3, 3, +, 2, 2, *, +, 2, 2, +, 3, 3]", V1.toString());
	}
	
	@Test
	public void inOrder() throws ExprParsingException {
		ExpressionTree E1 = ExpressionTree.parsePostfixExpression("3 3 + 2 2 + * 2 2 + 3 3 + * +");

		GatheringVisitor<ExpressionComponent> V1 = new GatheringVisitor<ExpressionComponent>();
		E1.inOrder(V1);
		
		Assert.assertEquals("[3, +, 3, *, 2, +, 2, +, 2, +, 2, *, 3, +, 3]" ,V1.toString());
	}

	@Test
	public void postOrder() throws ExprParsingException {
		ExpressionTree E1 = ExpressionTree.parsePostfixExpression("3 3 + 2 2 + * 2 2 + 3 3 + * +");

		GatheringVisitor<ExpressionComponent> V1 = new GatheringVisitor<ExpressionComponent>();
		E1.postOrder(V1);
		
		Assert.assertEquals("[3, 3, +, 2, 2, +, *, 2, 2, +, 3, 3, +, *, +]", V1.toString());
	}

	@Test
	public void testToString() throws ExprParsingException {
		ExpressionTree E1 = ExpressionTree.parsePostfixExpression("3 3 +");
		
		Assert.assertEquals("{+, {3, ∅, ∅}, {3, ∅, ∅}}", E1.toString());
	}
	
	@Test
	public void testBreadthFirst() throws ExprParsingException {
		
		ExpressionTree E1 = ExpressionTree.parsePostfixExpression("3 3 + 2 2 + * 2 2 + 3 3 + * +");

		//	Almacena la información en el recorrido
		final GatheringVisitor<ExpressionComponent> V1 = new GatheringVisitor<ExpressionComponent>();
		
		E1.breadthOrder(V1);
		
		Assert.assertEquals("[+, *, *, +, +, +, +, 3, 3, 2, 2, 2, 2, 3, 3]", V1.toString());
	}
	
	@BeforeClass
	public static void showOutput() throws ExprParsingException {
		
		System.out.println("Sea la expresión ((3+3)·(2+2))+((2+2)·(3+3))");
		
		System.out.println("En notación postfija: 3 3 + 2 2 + * 2 2 + 3 3 + * +");
		
		// Se puede construir el árbol de expresión a partir de la cadena
		ExpressionTree E1 = ExpressionTree.parsePostfixExpression("3 3 + 2 2 + * 2 2 + 3 3 + * +");
		
		System.out.println("Su árbol de expresión: " + E1.toString());
		
		System.out.println("Mostrado de forma más 'gráfica':");
		
		System.out.println(E1.render());
	}
}

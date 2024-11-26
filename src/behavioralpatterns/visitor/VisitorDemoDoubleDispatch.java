package behavioralpatterns.visitor;

interface ExpressionVisitor {
	void visit(DoubleMathExpression e);
	void visit(AdditionMathExpression e);
}

abstract class MathExpression {	
	public abstract void accept(ExpressionVisitor visitor); 
	
}// 1 + 2

class DoubleMathExpression extends MathExpression {
	double value;
	
	public DoubleMathExpression(double value) {
		this.value = value;
	}

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visit(this);
	}
}

class AdditionMathExpression extends MathExpression {
	MathExpression left, right;
	
	public AdditionMathExpression(MathExpression left, MathExpression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visit(this);
	}
}

class MathExpressionPrinter implements ExpressionVisitor {

	private StringBuilder sb = new StringBuilder();
	
	@Override
	public void visit(DoubleMathExpression e) {
		vis
	}

	@Override
	public void visit(AdditionMathExpression e) {
		sb.append("(");
		e.left.accept(this);
		sb.append("+");
		e.right.accept(this);
		sb.append(")");
	}
	
	@Override
	public String toString() {
		return sb.toString();
	}
}

class MathExpressionCalculation implements ExpressionVisitor {
	
	public double result;

	@Override
	public void visit(DoubleMathExpression e) {
		result = e.value;
	}

	@Override
	public void visit(AdditionMathExpression e) {
		e.left.accept(this);
		double a = result;
		e.right.accept(this);
		double b = result;
		result = a+b;
	}
	
}


public class VisitorDemoDoubleDispatch {
	
	public static void main(String[] args) {
		// 1+(8+3)
		AdditionMathExpression ae = new AdditionMathExpression(
				new DoubleMathExpression(1), 
				new AdditionMathExpression(
						new DoubleMathExpression(8), 
						new DoubleMathExpression(3)
				)
		);
		
		MathExpressionPrinter printer = new MathExpressionPrinter();
		printer.visit(ae);
		System.out.println(printer);
		
		MathExpressionCalculation calculator = new MathExpressionCalculation();
		calculator.visit(ae);
		System.out.println(printer + "=" + calculator.result);
	}
	
}


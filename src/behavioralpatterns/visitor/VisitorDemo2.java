package behavioralpatterns.visitor;

abstract class NewExpression {	
	
	
}// 1 + 2

class DoubleNewExpression extends NewExpression {
	double value;
	
	public DoubleNewExpression(double value) {
		this.value = value;
	}
}

class AdditionNewExpression extends NewExpression {
	NewExpression left, right;
	
	public AdditionNewExpression(NewExpression left, NewExpression right) {
		this.left = left;
		this.right = right;
	}
}

class ExpressionPrinter {
	
	public static void print(NewExpression e, StringBuilder sb) {

		if(e.getClass() == DoubleNewExpression.class) {
			sb.append(((DoubleNewExpression) e).value);
		} else if (e.getClass() == AdditionNewExpression.class) {
			AdditionNewExpression ane = (AdditionNewExpression) e;
			sb.append("(");
			print(ane.left, sb);
			sb.append("+");
			print(ane.right, sb);
			sb.append(")");
		}
		
	}
	
}

public class VisitorDemo2 {
	
	public static void main(String[] args) {
		// 1+(2+3)
		AdditionNewExpression ae = new AdditionNewExpression(
				new DoubleNewExpression(1), 
				new AdditionNewExpression(
						new DoubleNewExpression(8), 
						new DoubleNewExpression(3)
				)
		);
		
		StringBuilder sb = new StringBuilder();
		ExpressionPrinter.print(ae, sb);
		System.out.println(sb);
	}

}

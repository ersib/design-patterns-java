package behavioralpatterns.visitor;

interface Visitor {}

interface ExprVisitor extends Visitor {
	void visit(Expr obj);
}

interface DblExprVisitor extends Visitor {
	void visit(DblExpr obj);
}

interface AddExprVisitor extends Visitor {
	void visit(AddExpr obj);
}

abstract class Expr {
	public void accept(Visitor visitor) {
		if (visitor instanceof ExprVisitor) {
			((ExprVisitor) visitor).visit(this);
		}
	}
}

class DblExpr extends Expr {
	double value;
	
	public DblExpr(double value) {
		this.value = value;
	}
	
	@Override
	public void accept(Visitor visitor) {
		if (visitor instanceof DblExprVisitor) {
			((DblExprVisitor) visitor).visit(this);
		}
	}
}

class AddExpr extends Expr {
	Expr left, right;
	
	public AddExpr(Expr left, Expr right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public void accept(Visitor visitor) {
		if (visitor instanceof AddExprVisitor) {
			((AddExprVisitor) visitor).visit(this);
		}
	}
}


class ExprPrinter implements /*DblExprVisitor,*/ AddExprVisitor {

	private StringBuilder sb = new StringBuilder();
	
	//@Override
	public void visit(DblExpr e) {
		sb.append(e.value);
	}

	@Override
	public void visit(AddExpr e) {
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

public class VisitorAcyclic {

	public static void main(String[] args) {
		AddExpr ae = new AddExpr(
				new DblExpr(1), 
				new AddExpr(
						new DblExpr(8), 
						new DblExpr(3)
				)
		);
		
		ExprPrinter printer = new ExprPrinter();
		printer.visit(ae);
		System.out.println(printer);
	}

}

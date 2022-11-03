import java.util.*;
public class PIP {
	
	public int evaluatePostfix(String e){
		Stack<Integer> stack = new Stack<>();
		
		for(int i=0; i<e.length(); i++) {
			char current = e.charAt(i);
			
			//check if current is operand or operator
			if(Character.isDigit(current))
				stack.push(current-'0');
			else {
				if (stack.isEmpty()) 
					throw new IllegalArgumentException("This is an invalid postfix expression");
				int r1 = stack.pop();
				if (stack.isEmpty()) 
					throw new IllegalArgumentException("This is an invalid postfix expression");
				int r2 = stack.pop();
				switch(current) {
					case '^':
						stack.push((int) Math.pow(r2,r1));
						break;
					case '*':
						stack.push(r2*r1);
						break;
					case '/':
						stack.push(r2/r1);
						break;
					case '+':
						stack.push(r2+r1);
						break;
					case '-':
						stack.push(r2-r1);
						break;
				}
			}
		}
		int num = stack.pop();
		if(!stack.isEmpty())
			throw new IllegalArgumentException("This is an invalid postfix expression");
		return num;
	}
	
	public String infixToPostfix(String e) {
		Stack <Character> stack = new Stack <>();
		String output = "";
		int lastIndex = e.length()-1;
		
		//check to see if the infix expression is invalid at the beginning or end
		if(!Character.isLetterOrDigit(e.charAt(0))) {
			//if the first element is a operator and not ( or )
			if(e.charAt(0)!='(')
				throw new IllegalArgumentException("This is an invalid infix expression");
		}
		if(!Character.isLetterOrDigit(e.charAt(lastIndex))) {
			//if the last character is a operator (and not a space or )) then invalid expression
			if(!(e.charAt(lastIndex)!=')' || e.charAt(lastIndex)!=' '))
				throw new IllegalArgumentException("This is an invalid infix expression");
		}
		
		//translating
		for(int i=0; i<e.length(); i++) {
			char current = e.charAt(i);
			//check if current is operand or operator
			if(Character.isLetterOrDigit(current)) {
				//if adjacent elements in the expression are both numbers/letters (invalid infix)
				if(i>=2 && output.length()>0 && Character.isLetterOrDigit(output.charAt(output.length()-1))) {
					throw new IllegalArgumentException("This is an invalid infix expression");
				}
				output += current + " ";
			}else if(current == '(') 
				stack.push(current);
			else if(current == ')') {
				
				//empty the stack back to the '('
				while(stack.peek()!='(' && !stack.isEmpty()) {
					//if there are still elements and it is not '('
					output += stack.pop() + " ";
				}
				//if '(' never shows up and there are no more elements to peek
				if(stack.peek()!='(' && !stack.isEmpty()) 
					throw new IllegalArgumentException("This is an invalid infix expression");
				// '(' does show up
				else 
					stack.pop();
			}else {
				//current is a normal operator -> check precedences
				while(!stack.isEmpty() && checkPrecedence(current)<=checkPrecedence(stack.peek()))
					output += stack.pop() + " ";
				//if current is of higher precedence than top
				stack.push(current);
			}
		}	
		//end of input, pop rest of operators
		while(!stack.isEmpty()) {
			output += stack.pop() + " ";
		}
		return output;
	}
	
	public int checkPrecedence(char operator) {
		//returns the 'precedence' of an operator
		switch(operator) {
		case '+': case '-':
			//lowest precedence
			return 1;
		case '*': case '/':
			return 2;
		case '^':
			return 3;	
		}
		return 0;
	}
	
	public static void main(String[] args) {
		//take in user input
		Scanner obj = new Scanner(System.in);
		PIP o = new PIP();
		String check = "";
		System.out.println("Enter \"evaluate postfix\" or \"infix to postfix\"");
		while(!check.equalsIgnoreCase("stop")) {
			check = obj.nextLine();
			if(check.equalsIgnoreCase("evaluate postfix")) {
				System.out.println("Enter the postfix expression you want to evaluate:");
				System.out.println("The result is: " + o.evaluatePostfix(obj.nextLine()));
			}
			else if(check.equalsIgnoreCase("infix to postfix")){
				System.out.println("Enter the infix expression you want to convert to postfix:");
				System.out.println("This is the postfix expression: " + o.infixToPostfix(obj.nextLine()));
			}
			System.out.println("Enter \"evaluate postfix\" or \"infix to postfix\"");
		}
	}
}

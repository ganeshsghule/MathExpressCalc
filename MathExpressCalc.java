package ganeshsghule.mathexpress;

import java.util.stream.Stream;
import java.util.Stack;
import java.util.Scanner;
/**
* author : Ganesh Shivajirao Ghule
**/
public class MathExpressCalc{

    private StackTokenUtility operatorStack;
    private StackTokenUtility valueStack;
    private boolean error;
    
    public MathExpressCalc() {
        operatorStack = new StackTokenUtility();
        valueStack = new StackTokenUtility();
        error = false;
    }

    private void checkOperator(TokenHandler t) {
        TokenHandler tokenValA= null, tokenValB = null;
        if (valueStack.isEmpty()) {
            System.out.println("Invalid Expression");
            error = true;
            return;
        } else {
            tokenValB = valueStack.top();
            valueStack.pop();
        }
        if (valueStack.isEmpty()) {
            System.out.println("Invalid Expression");
            error = true;
            return;
        } else {
            tokenValA = valueStack.top();
            valueStack.pop();
        }
        TokenHandler tokenHandler = t.operate(tokenValA.getValue(), tokenValB.getValue());
        valueStack.push(tokenHandler);
    }

    public void evaluateInput(String userInput) {
        String[] inputString = new String[userInput.length()];
        for (int n = 0; n < userInput.length(); n++){
          inputString[n] = userInput.charAt(n)+"";
        }
      	TokenHandler[] tokens = new TokenHandler[inputString.length];
        for (int n = 0; n < inputString.length; n++) {
            tokens[n] = new TokenHandler(inputString[n]);
        }

        // Process Input Token
        for (int n = 0; n < tokens.length; n++) {
            TokenHandler nextToken = tokens[n];
            if (nextToken.getType() == TokenHandler.NUMBER) {
                valueStack.push(nextToken);
            } else if (nextToken.getType() == TokenHandler.OPERATOR) {
                if (operatorStack.isEmpty() || nextToken.getPrecedence() > operatorStack.top().getPrecedence()) {
                    operatorStack.push(nextToken);
                } else {
                    while (!operatorStack.isEmpty() && nextToken.getPrecedence() <= operatorStack.top().getPrecedence()) {
                        TokenHandler toProcess = operatorStack.top();
                        operatorStack.pop();
                        checkOperator(toProcess);
                    }
                    operatorStack.push(nextToken);
                }
            } else if (nextToken.getType() == TokenHandler.LEFT_PARENTHESIS) {
                operatorStack.push(nextToken);
            } else if (nextToken.getType() == TokenHandler.RIGHT_PARENTHESIS) {
                while (!operatorStack.isEmpty() && operatorStack.top().getType() == TokenHandler.OPERATOR) {
                    TokenHandler toProcess = operatorStack.top();
                    operatorStack.pop();
                    checkOperator(toProcess);
                }
                if (!operatorStack.isEmpty() && operatorStack.top().getType() == TokenHandler.LEFT_PARENTHESIS) {
                    operatorStack.pop();
                } else {
                    System.out.println("Invalid Expression For Parenthesis");
                    error = true;
                }
            }

        }
        // Make Operator Stack empty at the end of the input
        while (!operatorStack.isEmpty() && operatorStack.top().getType() == TokenHandler.OPERATOR) {
            TokenHandler toProcess = operatorStack.top();
            operatorStack.pop();
            checkOperator(toProcess);
        }
        // Print the result
        if(error == false) {
            TokenHandler result = valueStack.top();
            valueStack.pop();
            if (!operatorStack.isEmpty() || !valueStack.isEmpty()) {
                System.out.println("Invalid Expression");
            } else {
                System.out.println("Final Math Expression Result : " + result.getValue());
            }
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        // User Input
        System.out.print("Enter Math Expression : ");
        String userInput = input.nextLine();
        MathExpressCalc calc = new MathExpressCalc();
        if(userInput!=null && userInput.trim().length()>0 ){       
        	calc.evaluateInput(userInput);
          }else{
            System.out.println("Kindly enter a valid mathematical expression to evaluate");
          }
          
    }
}

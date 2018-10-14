package ganeshsghule.mathexpress;

import java.util.Stack;
import java.util.ArrayList;
/**
* author : Ganesh Shivajirao Ghule
**/
public class StackTokenUtility
{
  private ArrayList<TokenHandler> tokens;

  public StackTokenUtility() {
    tokens = new ArrayList<TokenHandler>();
  }
  public boolean isEmpty() {
    return tokens.size() == 0;
  }
  public TokenHandler top() {
    return tokens.get(tokens.size()-1);
  }
  public void push(TokenHandler t) {
    tokens.add(t);
  }
  public void pop() {
    tokens.remove(tokens.size()-1);
  }
}    
      

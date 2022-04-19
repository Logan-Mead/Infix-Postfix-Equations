import java.util.Scanner;
import java.lang.String;
import java.util.Stack;
import java.util.LinkedList;

class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in).useDelimiter("\n");                               //scanner that makes it so we can enter strings with spaces
    int userInput = -1;                                                                        //setting variables
    String equation = new String();
    String postOutString = new String();
    String infixOutString = new String();
    LinkedList<String> infixToPostfix = new LinkedList<String>();                             //making linked lists
    LinkedList<String> postfixToInfix = new LinkedList<String>();

    while (userInput != 4) {                                                                   // run menu until user enters 4
      do {                                                                                     // menu with options
        System.out.print("\n");

        System.out.println("1) Infix to Postfix");
        System.out.println("2) Postfix to Infix");
        System.out.println("3) Print Equations");
        System.out.println("4) Exit");
        System.out.print("Enter a Selection: ");
        userInput = scanner.nextInt();
        System.out.print("\n");
      } while (userInput < 1 || userInput > 4);                                                  // do certain type of conversion based on userInput

      if (userInput == 1) {
        Stack<Character> operatorStack = new Stack<Character>();                                //create/reset operatorStack
        System.out.print("Enter a Equation: ");                                                 //asks user for equation and saves it
        equation = scanner.next();
        equation = equation.substring(equation.indexOf('=') + 1);                               //removes things in string up to and =
        postOutString = new String();                                                           //create/reset postOutString

        for (int i = 0; i < equation.length(); i++) {                                           //loop that runs through equation
          if (Character.isLetterOrDigit(equation.charAt(i))) {                                  //if loop that goes through if is a letter or digit
            postOutString = postOutString + equation.charAt(i);                                 //adds to postOutString with current letter/digit
          } else if (operatorStack.isEmpty() || operatorStack.peek() == '(') {                  //goes through if stack is empty or the top of the stack is (
            operatorStack.push(equation.charAt(i));                                             //push current character to stack
          } else if (equation.charAt(i) == '(') {                                               //goes through if current character if (
            operatorStack.push(equation.charAt(i));                                             //push current character onto stack
          } else if (equation.charAt(i) == ')') {                                               //goes through if current character is )
            for (int j = operatorStack.size() - 1; j >= 0; j--) {                               //for loop that runs through the stack
              if (operatorStack.peek() != '(') {                                                //if statement that goes through if top of stack is not a (
                postOutString = postOutString + operatorStack.peek();                           //adds to postOutString to top of stack
                operatorStack.pop();                                                            //pops stack
              } else {
                operatorStack.pop();                                                            //else  pop stack
              }
            }
          } else if (isPrecedence(equation.charAt(i)) > isPrecedence(operatorStack.peek())) {  //runs if isPrecedence is less than top
            operatorStack.push(equation.charAt(i));                                            //push current operator to stack
          } else if (isPrecedence(equation.charAt(i)) == isPrecedence(operatorStack.peek())) { //runs if isPrecedence is equal
            postOutString = postOutString + operatorStack.peek();                              //adds to postOutString to top of Stack
            operatorStack.pop();                                                               //pops stack
            operatorStack.push(equation.charAt(i));                                            //push current operator to stack
          } else if (isPrecedence(equation.charAt(i)) < isPrecedence(operatorStack.peek())) {  //runs if isPrecedence is less than
            postOutString = postOutString + operatorStack.peek();                              //adds to postOutString to top of stack
            operatorStack.pop();                                                               //pops stack
            if (operatorStack.isEmpty() || operatorStack.peek() == '(') {                      //re runs through everything above to check order to next operator
              operatorStack.push(equation.charAt(i));
            } else if (equation.charAt(i) == '(') {
              operatorStack.push(equation.charAt(i));
            } else if (equation.charAt(i) == ')') {
              for (int j = operatorStack.size() - 1; j >= 0; j--) {
                if (operatorStack.peek() != '(') {
                  postOutString = postOutString + operatorStack.peek();
                  operatorStack.pop();
                } else {
                  operatorStack.pop();
                }
              }
            } else if (isPrecedence(equation.charAt(i)) > isPrecedence(operatorStack.peek())) {
              operatorStack.push(equation.charAt(i));
            } else if (isPrecedence(equation.charAt(i)) == isPrecedence(operatorStack.peek())) {
              postOutString = postOutString + operatorStack.peek();
              operatorStack.pop();
              operatorStack.push(equation.charAt(i));
            }
            else{
              operatorStack.push(equation.charAt(i));
            }
          }
        }
        for(int k = operatorStack.size() - 1; k >= 0; k-- ){                                     //for loop that runs through operatorStack
          postOutString = postOutString + operatorStack.peek();                                  //adds to postOutString to top of stack
          operatorStack.pop();                                                                   //pops stack
        }
        postOutString = postOutString.replace("(", "");                                          //deletes ( in the string
        postOutString = postOutString.replace(")", "");                                          //deletes ) in the string
        infixToPostfix.add(postOutString);                                                       //adds to linked list
        System.out.println("Infix to Postfix: " + postOutString);                                //prints current converted equation
      }

      if (userInput == 2) {                                                                       //method to convert infixToPostfix
        Stack<String> operatorStack = new Stack<String>();                                        //creates/resets operatorStack
        System.out.print("Enter a Equation: ");                                                   //asks user to enter equation and saves it
        equation = scanner.next(); 
        equation = equation.substring(equation.indexOf('=') + 1);                                 //removes chars up to = in equation
        infixOutString = new String();                                                            //creates/resets variables
        String temp1 = new String(); 
        String temp2 = new String();
         
        for(int i = 0; i < equation.length(); i++){                                               //runs through the string equation
         if(Character.isLetterOrDigit(equation.charAt(i))){                                       //checks to see if the character is a letter or digit
            operatorStack.push("" + equation.charAt(i));                                          //if so push to the stack
          } 
          else if(equation.charAt(i) == '\0'){                                                   //else if at end of equation
            temp1 = operatorStack.peek();                                                        //set temp1 to the top of stack
            operatorStack.pop();                                                                 //pop operatorStack
            temp2 = operatorStack.peek();                                                        //set temp2 to the top of stack
            operatorStack.pop();                                                                 //pop operatorStack
            operatorStack.push('(' + temp1 + equation.charAt(i) + temp2 + ')');                  //push '(' temp1 currect operator temp2 ')' onto stack
          } 
          else if(equation.charAt(i) != '\0'){                                                   //else if not at end of equation
            temp1 = operatorStack.peek();                                                        //set temp1 to top of stack
            operatorStack.pop();                                                                 //pop operatorStack
            temp2 = operatorStack.peek();                                                        //set temp2 to top of stack
            operatorStack.pop();                                                                 //pop operatorStack
            operatorStack.push('(' + temp2 + equation.charAt(i) + temp1 + ')');                  //push '(' temp2 current operator temp1 ')' onto stack
          } 
        } 
        infixOutString = operatorStack.peek();                                                   //output string set to top of stack
        postfixToInfix.add(infixOutString);                                                      //add string to linked list
        System.out.println("Postfix to Infix: " + infixOutString);                               //print current converted equation
         
      }

      if (userInput == 3) {                                                                   //method to print all the conversions that occured
        System.out.println("Infix to Postfix: ");
        for(int i = 0; i < infixToPostfix.size(); i++){                                       //prints all infixToPostfix conversions
          System.out.println(infixToPostfix.get(i));
        }
        System.out.println("Postfix to Infix: ");
        for(int i = 0; i < postfixToInfix.size(); i++){                                      //prints all postfixToInfix conersions
          System.out.println(postfixToInfix.get(i));
        }
      }

    }

    if (userInput == 4) {                                                                     //tells user thank you if user says to exit
      System.out.println("Goodbye! Thank You For Using My Service!");
      System.out.println("Infix to Postfix: ");                                               //re-prints all conversions
        for(int i = 0; i < infixToPostfix.size(); i++){
          System.out.println(infixToPostfix.get(i));
        }
        System.out.println("Postfix to Infix: ");
        for(int i = 0; i < postfixToInfix.size(); i++){
          System.out.println(postfixToInfix.get(i));
        }
    }

  }

  private static int isPrecedence(char a) {                                                   //method to find the order of operations for comparision 
    switch (a) {
    case '+':                                                                                 //if + or - return 1
    case '-':
      return 1;
    case '*':                                                                                 //if * or / return 2
    case '/':
      return 2;
    case '^':                                                                                 //if ^ return 3
      return 3;
    case '(':                                                                                 //if ( or ) return 4
    case ')':
      return 4;
    }
    return -1;                                                                                //else return -1
  }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bodmas;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Adescode
 */
public class Bodmas {

    ArrayList<String> container;
    String temp;

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        String yourExpress;
        do{
        Scanner input = new Scanner(System.in);
        System.out.println("Enter expression:");
        yourExpress = input.nextLine();
        Bodmas bodmasIs = new Bodmas();
        String result = bodmasIs.checkBracket(yourExpress);
        System.out.println("Answer : " + result
        +"\t(Enter q to quit)\n");
        }while(!yourExpress.equals("q"));
                {
        if(yourExpress.equals("q")){
            System.out.println("Done!!!");  
        }
    }
    }
    /*Method that reads brackets*/

    public String checkBracket(String input) {
        while (input.contains(Character.toString('(')) || input.contains(Character.toString(')'))) { //condition: input with bracket
            for (int i = 0; i < input.length(); i++) {
                try {
                    if ((input.charAt(i) == ')' || Character.isDigit(input.charAt(i))) //multiplication oprator to occur 
                            && input.charAt(i + 1) == '(') {                           //after the digit 
                        input = input.substring(0, i + 1) + "*" + input.substring(i + 1);      //before open bracket '('
                    }
                } catch (Exception e) {}                                 //ignore out of bounds exception
                if (input.charAt(i) == ')') {
                    for (int j = i; j >= 0; j--) {
                        if (input.charAt(j) == '(') {
                            String in = input.substring(j + 1, i);               //reading method
                            in = read(in);                                //should occur
                            input = input.substring(0, j) + in + input.substring(i + 1);  //inside the bracket
                            j = i = 0;
                        }
                    }
                }
            }
            if (input.contains(Character.toString('(')) || input.contains(Character.toString(')'))) {
                return "Error: incorrect brackets placement";
            }
        }
        input = read(input);
        return input;
    }
    /*Method that reads numbers and operators */

    public String read(String input) {
        container = new ArrayList<>();    //keeps input String
        temp = "";                            //temporary String
        
        for (int i = input.length() - 1; i >= 0; i--) {   //precendence order of operation reads from RIGHT to LEFT
            if (Character.isDigit(input.charAt(i))) {
                temp = input.charAt(i) + temp;
                if (i == 0) {
                    check();
                }
            } else if (input.charAt(i) == '.') {                       //reads decimal
                temp = input.charAt(i) + temp;
            } else if (input.charAt(i) == '-' && (i == 0
                    || (!Character.isDigit(input.charAt(i - 1))))) //read negative numbers
            {
                temp = input.charAt(i) + temp;
                check();
            }else
                if ((input.charAt(i) == '+'||input.charAt(i) == '*'||
                        input.charAt(i) == '/'||input.charAt(i) == '^')
                        && (i == 0 || (!Character.isDigit(input.charAt(i - 1))))){
                return "Error in input";
                }
                
                else{
                check();
                temp += input.charAt(i);
                check();
            if(input.charAt(i)=='|'){
                temp+=" ";
                check();
            }
                }
        }
        container = calculate(container, "^"); //power
        container = calculate(container, "|"); //root
        container = calculate(container, "/"); //Divide
        container = calculate(container, "*"); //Multiple
        container = calculate(container, "+"); //Add
        container = calculate(container, "-"); //Sub
        return container.get(0);             // return result
    }
    
    public void check() {
        if (!temp.equals("")) {
            container.add(0, temp);
            temp = "";
        }
    }
    
    public ArrayList<String> calculate(ArrayList<String> input, String data) {
        int scale = 10;                         //to limit decimal places
        BigDecimal ex = new BigDecimal(0);    //Used instead of any data type
        for (int c = 0; c < input.size(); c++) {
            if (input.get(c).equals(data)) {
                switch (input.get(c)) {
                    case "^":
                        ex = new BigDecimal(input.get(c - 1)).pow(Integer.parseInt(input.get(c + 1)));
                        break;
                    case "|":
                        ex = new BigDecimal(Math.sqrt(Double.parseDouble(input.get(c + 1))));
                        break;
                    case "*":
                        ex = new BigDecimal(input.get(c - 1)).multiply(new BigDecimal(input.get(c + 1)));
                        break;
                    case "/":
                        try {
                            ex = new BigDecimal(input.get(c - 1)).divide(new BigDecimal(input.get(c + 1)));
                            if (input.get(c + 1).equals("0")) {
                            }
                        } catch (Exception e) {
                            System.err.println("Error : " + e.getMessage());
                        }
                        break;
                    case "+":
                        ex = new BigDecimal(input.get(c - 1)).add(new BigDecimal(input.get(c + 1)));
                        break;
                    case "-":
                        ex = new BigDecimal(input.get(c - 1)).subtract(new BigDecimal(input.get(c + 1)));
                        break;
                }
                input.set(c, ex.setScale(scale, RoundingMode.UP).stripTrailingZeros().toPlainString());
                input.remove(c + 1);       //replace operators with results                                                         
                input.remove(c - 1);        // remove used numbers numbers
            } else {
                continue;
            }
            c = 0;
        }
        return input;
    }
}

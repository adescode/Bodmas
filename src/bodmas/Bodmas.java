/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bodmas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Adescode
 */
public class Bodmas {

    private ArrayList<String> container;
    private String tempString;
    private String title;
    private String formula;
    private File file;

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        String expression;
        do {
            Scanner input = new Scanner(System.in);
            System.out.println("Enter expression:");
            expression = input.nextLine();
            Bodmas bodmas = new Bodmas();
            String result = bodmas.readExpression(expression);
            System.out.println("Answer : " + result
                    + "\t(Enter @ to quit)\n");
        } while (!expression.equals("@"));
    }
    
    String readExpression(String input){
        input = inputWithEqualsSign(input);
        return input;
    }
    
    public String inputWithEqualsSign(String input) {
        if (input.contains("=")) {                                      //condition: input with Equals sign
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) == '=') {
                    formula = input.substring(i + 1, input.length());
                    title = input.substring(0, i);
                    file = new File(title);
                    try {
                        if ((!formula.isEmpty()) && file.createNewFile()) {
                            input = saveTitleAndFormula();
                        } else if (file.exists() && !formula.isEmpty()) {
                            return titleAlreadyExisted();
                        } else {
                            input = loadTitleFormula(input);
                        }
                    } catch (IOException ex) {
                    }
                }
            }
        }
        input = alphabetInput(input);
        return input;
    }
    
    String saveTitleAndFormula() {
        try {
            try (
                    FileOutputStream fileOS = new FileOutputStream(file);
                    ObjectOutputStream objectOS = new ObjectOutputStream(fileOS)) {
                objectOS.writeUTF(formula);
                System.out.println("formula saved");
                objectOS.close();
                fileOS.close();
            }
        } catch (Exception ex) {
        }
        return "";
    }

    String titleAlreadyExisted() {
        System.out.println("Title-Formula already existed ");
        return "";
    }

    String loadTitleFormula(String input) {
        try {
            try (
                    FileInputStream fileIS = new FileInputStream(file);
                    ObjectInputStream objectIS = new ObjectInputStream(fileIS)) {
                String loadFormula = objectIS.readUTF();
                input = loadFormula;
                objectIS.close();
                fileIS.close();
            } catch (FileNotFoundException ex) {
            }
        } catch (IOException ex) {
        }
        return input;
    }

    /*Method that reads Alhabet as formula*/
    public String alphabetInput(String input) {
        for (int i = 0; i < input.length(); i++) {
            while (Character.isAlphabetic(input.charAt(i))) {               //condition: input with Alphabet
                Scanner scanner = new Scanner(System.in);
                System.out.print(input.charAt(i) + " = ");
                String isAlphabet = scanner.next();
                String nowDigit = input.replaceAll(Character.toString(input.charAt(i)), isAlphabet);//repalce all alphabet to digit
                input = nowDigit;
            }
        }
        input = inputWithBracket(input);
        return input;
    }

    /*Method that reads brackets*/
    public String inputWithBracket(String input) {
        while (input.contains(Character.toString('('))
                || input.contains(Character.toString(')'))) {                          //condition: input with bracket
            for (int i = 0; i < input.length(); i++) {
                try {
                    if ((input.charAt(i) == ')' || Character.isDigit(input.charAt(i))) //multiplication oprator to occur 
                            && input.charAt(i + 1) == '(') {                           //after the digit 
                        input = input.substring(0, i + 1) + "*" + input.substring(i + 1);      //before open bracket '('
                    }
                } catch (Exception e) {                                                 //ignore out of bounds exception
                }
                if (input.charAt(i) == ')') {
                    for (int j = i; j >= 0; j--) {
                        if (input.charAt(j) == '(') {
                            String in = input.substring(j + 1, i);                        //readInput method
                            in = readInput(in);                                           //should occur
                            input = input.substring(0, j) + in + input.substring(i + 1);  //inside the bracket
                            j = i = 0;
                        }
                    }
                }
            }
            if (input.contains(Character.toString('(')) || input.contains(Character.toString(')'))) {
                System.out.println("Error: incorrect brackets placement");
                return "";
            }
        }
        input = readInput(input);
        return input;
    }

    /*Method that reads numbers and operators */
    public String readInput(String input) {
        container = new ArrayList<>();                      //keeps input String
        tempString = "";                                         //temporary String
        for (int i = input.length() - 1; i >= 0; i--) {   //precendence order of operation reads from RIGHT to LEFT
            if (Character.isDigit(input.charAt(i))) {
                tempString = input.charAt(i) + tempString;
                if (i == 0) {
                    emptyTempString();
                }
            } else if (input.charAt(i) == '.') {                    //reads decimal
                if (input.charAt(i) == '.' && (i == 0)) {
                    return "Error in input";
                } else {
                    tempString = input.charAt(i) + tempString;
                }
            } else if (input.charAt(i) == '-' && (i == 0
                    || (!Character.isDigit(input.charAt(i - 1))))) //read negative numbers
            {
                tempString = input.charAt(i) + tempString;
                emptyTempString();
            } else if ((input.charAt(i) == '+' || input.charAt(i) == '*'
                    || input.charAt(i) == '/' || input.charAt(i) == '^')
                    && (i == 0 || (!Character.isDigit(input.charAt(i - 1))))) {
                return "Error in input";
            } else {
                emptyTempString();
                tempString += input.charAt(i);
                emptyTempString();
                if (input.charAt(i) == '|') {           //for root, empty space should replace
                    tempString += " ";                        //the Character before operator        
                    emptyTempString();                            //to be removed instead of result. 
                }
            }
        }
        container = process(container, "^"); //power
        container = process(container, "|"); //root*
        container = process(container, "/"); //Divide
        container = process(container, "*"); //Multiple
        container = process(container, "+"); //Add
        container = process(container, "-"); //Sub
        return container.get(0);             // return result
    }

    public void emptyTempString() {
        if (!tempString.isEmpty()) {
            container.add(0, tempString);
            tempString= "";
        }
    }

    public ArrayList<String> process(ArrayList<String> input, String operator) {
        int scale = 10;                         //to limit decimal places
        BigDecimal bigDecimal = new BigDecimal(0);    //Used instead of any data type
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i).equals(operator)) {
                switch (input.get(i)) {
                    case "^":
                        bigDecimal = new BigDecimal(input.get(i - 1)).pow(Integer.parseInt(input.get(i + 1)));
                        break;
                    case "|":
                        bigDecimal = new BigDecimal(Math.sqrt(Double.parseDouble(input.get(i + 1))));
                        break;
                    case "*":
                        bigDecimal = new BigDecimal(input.get(i - 1)).multiply(new BigDecimal(input.get(i + 1)));
                        break;
                    case "/":
                        try {
                            bigDecimal = new BigDecimal(input.get(i - 1)).divide(new BigDecimal(input.get(i + 1)));
                            if (input.get(i + 1).equals("0")) {
                            }
                        } catch (Exception e) {
                            System.out.println("Error : Division by zero");
                        }
                        break;
                    case "+":
                        bigDecimal = new BigDecimal(input.get(i - 1)).add(new BigDecimal(input.get(i + 1)));
                        break;
                    case "-":
                        bigDecimal = new BigDecimal(input.get(i - 1)).subtract(new BigDecimal(input.get(i + 1)));
                        break;
                }
                input.set(i, bigDecimal.setScale(scale, RoundingMode.UP).stripTrailingZeros().toPlainString());
                input.remove(i + 1);       //replace operators with results                                                         
                input.remove(i - 1);        // remove used numbers numbers
            } else {
                continue;
            }
            i = 0;
        }
        return input;
    }
}

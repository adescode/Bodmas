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

    ArrayList<String> container;
    String temp;

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        String yourExpress;
        do {
            Scanner input = new Scanner(System.in);
            System.out.println("Enter expression:");
            yourExpress = input.nextLine();
            Bodmas bodmasCheck = new Bodmas();
            String result = bodmasCheck.fileStore(yourExpress);
            System.out.println("Answer : " + result
                    + "\t(Enter @ to quit)\n");
        } while (!yourExpress.equals("@"));
        {
            if (yourExpress.equals("@")) {
                System.out.println("Done!!!");
            }
        }
    }

    public String fileStore(String input) {
        if (input.contains("=")) {                                          //condition: input with Equals sign
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) == '=') {
                    String formula = input.substring(i + 1, input.length());
                    String title = input.substring(0, i);
                    File file = new File(title);
                    try {
                        if ((!formula.isEmpty()) && file.createNewFile()) {
                            try (
                                    FileOutputStream fileOS = new FileOutputStream(file);
                                    ObjectOutputStream objectOS = new ObjectOutputStream(fileOS)) {
                                objectOS.writeUTF(formula);
                                objectOS.close();
                                fileOS.close();
                            }
                            return "formula saved";
                        } else {
                            try (
                                    FileInputStream fileIS = new FileInputStream(file);
                                    ObjectInputStream objectIS = new ObjectInputStream(fileIS)) {
                                String loadFormula = objectIS.readUTF();
                                input = loadFormula;
                                objectIS.close();
                                fileIS.close();
                            } catch (FileNotFoundException ex) {
                                return "File doesn't exist";
                            }
                        }
                    } catch (IOException ex) {
                    }
                }
            }
        }
        input = isFormula(input);
        return input;
    }

    public String isFormula(String input) {
        for (int i = 0; i < input.length(); i++) {
            while (Character.isAlphabetic(input.charAt(i))) {               //condition: input with Alphabet
                Scanner in = new Scanner(System.in);
                System.out.print(input.charAt(i) + "=");
                String isAlphabet = in.next();
                String nowDigit = input.replaceAll(Character.toString(input.charAt(i)), isAlphabet);
                input = nowDigit;
            }
        }
        input = readBracket(input);
        return input;
    }
    /*Method that reads brackets*/

    public String readBracket(String input) {
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
                return "Error: incorrect brackets placement";
            }
        }
        input = readInput(input);
        return input;
    }
    /*Method that reads numbers and operators */

    public String readInput(String input) {
        container = new ArrayList<>();                      //keeps input String
        temp = "";                                         //temporary String
        for (int i = input.length() - 1; i >= 0; i--) {   //precendence order of operation reads from RIGHT to LEFT
            if (Character.isDigit(input.charAt(i))) {
                temp = input.charAt(i) + temp;
                if (i == 0) {
                    check();
                }
            } else if (input.charAt(i) == '.') {                    //reads decimal
                if (input.charAt(i) == '.' && (i == 0)) {
                    return "Error in input";
                } else {
                    temp = input.charAt(i) + temp;
                }
            } else if (input.charAt(i) == '-' && (i == 0
                    || (!Character.isDigit(input.charAt(i - 1))))) //read negative numbers
            {
                temp = input.charAt(i) + temp;
                check();
            } else if ((input.charAt(i) == '+' || input.charAt(i) == '*'
                    || input.charAt(i) == '/' || input.charAt(i) == '^')
                    && (i == 0 || (!Character.isDigit(input.charAt(i - 1))))) {
                return "Error in input";
            } else {
                check();
                temp += input.charAt(i);
                check();
                if (input.charAt(i) == '|') {           //for root, empty space should replace
                    temp += " ";                        //the Character before operator        
                    check();                            //to be removed instead of result. 
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

    public void check() {
        if (!temp.equals("")) {
            container.add(0, temp);
            temp = "";
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
                            System.out.println("Error : Division by zero" );
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

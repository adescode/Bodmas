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
    private String loadFormula;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        Bodmas bodmas = new Bodmas();
        bodmas.run();
    }

    public void run() {
        String expression;
        do {
            System.out.println("Enter expression:");
            expression = scanner.nextLine();
            String result = readExpression(expression);
            System.out.println("Answer : " + result
                    + "\t(Enter @ to quit)\n");
        } while (!expression.equals("@"));
    }

    String readExpression(String input) {
        if (input.isEmpty()) {
            return emptyInput();
        } else if (input.contains("=")) {
            input = readEqualsSign(input.replaceAll("\\s", ""));
        } else if ((input.contains("(") || input.contains(")")) && !input.contains("=")) {
            input = specifyBracketType(input.replaceAll("\\s", ""));
        } else if (input.matches("[a-zA-Z]+")) {
            title = input;
            file = new File(title);
            if (file.exists()) {
                input = loadSavedFormula();
            } else {
                input = fileNotFound();
            }
        } else {
            input = convertFormulaValues(input.replaceAll("\\s", ""));
        }
        return input;
    }

    String fileNotFound() {
        System.out.println("Error: File doesn't exist");
        return "";
    }

    String emptyInput() {
        System.out.println("Error: Empty expression");
        return "";
    }

    public boolean inputContainsBracket(String input) {
        if (input.contains(Character.toString('('))
                || input.contains(Character.toString(')'))) {
        }
        return true;
    }

    public boolean formulaContainsCompleteBracket() {
        if (formula.contains(Character.toString('('))
                && formula.contains(Character.toString(')'))) {
            System.out.println("complete formula bracket");
        }
        return true;
    }

    String inputContainsCompleteBracket(String input) {
        if (input.contains(Character.toString('('))
                && input.contains(Character.toString(')'))) {
            System.out.println("complete input bracket");
        }
        return "";
    }

    public String getExpressionInBracket() {
        for (int i = 0; i < formula.length(); i++) {
            if (formula.charAt(i) == '(') {
                for (int j = i; j <= formula.length(); j++) {
                    if (formula.charAt(j) == ')') {
                        String tempFormula = formula.substring(i + 1, j);
                        formula = tempFormula;
                    }
                }
            }
        }
        return formula;
    }

    String replaceLoadedFormula() {
        String expressionInBracket = getExpressionInBracket();
        String[] splitExpressionInBracket = expressionInBracket.split(",");
        String savedFormula = loadSavedFormula();
        String[] splitSavedFormula = savedFormula.split("[-+*/^|()]");
        String currentExpression;
        int nextLoop = 0;
        for (String currentSavedFormula : splitSavedFormula) {
            if (currentSavedFormula.matches("[a-zA-Z]+")) {
                nextLoop++;
            }
        }
        if (splitExpressionInBracket.length < nextLoop || splitExpressionInBracket.length > nextLoop) {
            savedFormula = incorrect();
            return savedFormula;
        } else {
            for (int i = 0; i <= 0; i++) {
                for (String currentSavedFormula : splitSavedFormula) {
                    if (currentSavedFormula.matches("[a-zA-Z]+")) {
                        title = splitExpressionInBracket[i];
                        file = new File(title);
                        if (file.exists()) {
                            String loadsavedformula = loadSavedFormula();
                            splitExpressionInBracket[i] = splitExpressionInBracket[i].replace(splitExpressionInBracket[i], loadsavedformula);
                            currentExpression = splitExpressionInBracket[i];
                        } else {
                            if (splitExpressionInBracket[i].matches("[a-zA-Z]+") && !file.exists()) {
                                savedFormula = "formula not found";
                                return savedFormula;
                            }
                            currentExpression = splitExpressionInBracket[i];
                        }
                        String newSavedFormula = savedFormula.replace(currentSavedFormula, currentExpression);
                        savedFormula = newSavedFormula;
                        i++;
                    }
                }
            }
        }
        savedFormula = convertFormulaValues(savedFormula);
        return savedFormula;
    }

    String incorrect() {
        System.out.println("Error: incorrect parameters");
        return "";
    }

    String loadSavedFormula() {
        try {
            try (
                    FileInputStream fileIS = new FileInputStream(file);
                    ObjectInputStream objectIS = new ObjectInputStream(fileIS)) {
                loadFormula = objectIS.readUTF();
                objectIS.close();
                fileIS.close();
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return loadFormula;
    }

    String specifyBracketType(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(') {
                title = input.substring(0, i);
                formula = input.substring(i, input.length());
                file = new File(title);
                if (file.exists()) {
                    if (formula.contains(Character.toString('('))
                            && formula.contains(Character.toString(')'))) {
                        formula = replaceLoadedFormula();
                        input = formula;
                    } else {
                        input = emptyInput();
                    }
                } else {
                    input = convertFormulaValues(input);
                }
            }
        }
        return input;
    }

    public String readEqualsSign(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '=') {
                formula = input.substring(i + 1);
                title = input.substring(0, i);
                file = new File(title);
                if ((!input.isEmpty()) && !file.exists()) {
                    return saveTitleAndFormula();

                } else if (file.exists() && !formula.isEmpty()) {
                    return titleAlreadyExisted();
                } else {
                    input = loadSavedFormula();
                    input = convertFormulaValues(input);
                }
            }
        }
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
            System.out.println(ex.getMessage());
        }
        return "";

    }

    String titleAlreadyExisted() {
        System.out.println("Title-Formula already existed ");
        return "";
    }

    /*Method that convert letters to digit*/
    public String convertFormulaValues(String input) {
        String[] splitInput = input.split("[-+*/^|()]");
        for (String newInput : splitInput) {
            if (newInput.matches("[a-zA-Z]+")) {
                System.out.print(newInput + " = ");
                String isLetter = scanner.nextLine();
                if (isLetter.isEmpty()) {
                    return emptyInput();
                }
                String nowDigit = input.replace(newInput, isLetter.trim());
                input = nowDigit;
            }
        }
        input = readInputWithBracket(input);
        return input;
    }

    /*Method that reads brackets*/
    public String readInputWithBracket(String input) {
        while (input.contains(Character.toString('('))
                || input.contains(Character.toString(')'))) {                             //condition: input with bracket
            for (int i = 0; i < input.length(); i++) {
                try {
                    if ((input.charAt(i) == ')' || Character.isDigit(input.charAt(i))) //multiplication oprator to occur 
                            && input.charAt(i + 1) == '(') {                              //after the digit 
                        input = input.substring(0, i + 1) + "*" + input.substring(i + 1); //before open bracket '('
                    }
                } catch (Exception e) {                                                   //ignore out of bounds exception
                }
                if (input.charAt(i) == ')') {
                    for (int j = i; j >= 0; j--) {
                        if (input.charAt(j) == '(') {
                            String in = input.substring(j + 1, i);                        //readInput method
                            in = readInputDigitWithOperator(in);                          //should occur
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
        input = readInputDigitWithOperator(input);
        return input;
    }

    /*Method that reads numbers and operators */
    public String readInputDigitWithOperator(String input) {
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
                    && ((i == 0 || (!Character.isDigit(input.charAt(i - 1))))
                    || (i == input.length() - 1))) {
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
        container = process(container, "|"); //power
        container = process(container, "^"); //root*
        container = process(container, "/"); //Divide
        container = process(container, "*"); //Multiple
        container = process(container, "+"); //Add
        container = process(container, "-"); //Sub
        return container.get(0);             // return result
    }

    public void emptyTempString() {
        if (!tempString.isEmpty()) {
            container.add(0, tempString);
            tempString = "";
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
                            bigDecimal = new BigDecimal(input.get(i - 1)).divide(new BigDecimal(input.get(i + 1)), scale, BigDecimal.ROUND_DOWN);
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

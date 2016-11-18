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
 * @author user
 */
public class Bodmas {
ArrayList<String> container;
 String temp;
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter expression:");
        String yourExpress=input.nextLine();
        Bodmas bodmasIs = new Bodmas();
        String result = bodmasIs.bracket(yourExpress);
        System.out.println("Answer : " + result);
    }
    public String bracket(String box){
        while(box.contains(Character.toString('(')) || box.contains(Character.toString(')'))){
            for (int i=0;i<box.length();i++){
                try{
                if((box.charAt(i)==')' || Character.isDigit(box.charAt(i)))&& box.charAt(i+1)=='('){
                    box=box.substring(0, i+1)+"*"+box.substring(i+1);
                }
                }catch (Exception ignored){}
                if(box.charAt(i)==')'){
                    for(int j=i;j>=0;j--){
                        if(box.charAt(j)=='('){
                            String in= box.substring(j+1, i);
                            in= reading(in);
                            box=box.substring(0, j)+in+box.substring(i+1);
                            j=i=0;
                        }
                    }
                }
            }
            if(box.contains(Character.toString('('))||box.contains(Character.toString(')'))||
                    box.contains(Character.toString('('))||box.contains(Character.toString(')'))){
                System.out.println("Error: incorrect brackets placement");
                return "Error: incorrect brackets placement";
            }
        }
        box=reading(box);
    return box;
    }
    /*Method that reads number and operator */
    public String reading(String read){
            container = new ArrayList<>();    //keeps input String
            temp = "";                            //temporary String
            for(int i=read.length()-1;i>=0;i--){   //precendence order of operation reads from RIGHT to LEFT
              if(Character.isDigit(read.charAt(i))){
                  temp = read.charAt(i)+temp;
                  if(i==0){
                      if(!temp.equals("")){
                container.add(0,temp);
                temp="";
            }
                  }
              }else if(read.charAt(i)=='.'){
                   temp =read.charAt(i)+temp;
                  }
                  else if(read.charAt(i)=='-' && (i==0 || (!Character.isDigit(read.charAt(i-1)))))
                  {
                  temp =read.charAt(i)+temp;
                  if(!temp.equals("")){
                container.add(0,temp);
                temp="";
            }
                  }else
                  {if(!temp.equals("")){
                container.add(0,temp);
                temp="";
            }
                  temp +=read.charAt(i);
                  if(!temp.equals("")){
                container.add(0,temp);
                temp="";
            }
                  }
            }
           container = opp(container,"^"); //power
           container = opp(container,"|"); //root
           container = opp(container,"/"); //Divide
           container = opp(container,"*"); //Multiple
           container = opp(container,"+"); //Add
           container = opp(container,"-"); //Sub
           return container.get(0);
            }
    public ArrayList<String>opp(ArrayList<String> oop, String q){
                int scale=10;
                 BigDecimal ex = new BigDecimal(0);
                for(int c = 0; c<oop.size();c++){
                if(oop.get(c).equals(q)){
                 switch (oop.get(c)){
                     case "^":
                     ex = new BigDecimal(oop.get(c-1)).pow(Integer.parseInt(oop.get(c+1)));
                     break;
                     case "|":
                     ex = new BigDecimal(Math.sqrt(Double.parseDouble(oop.get(c+1))));
                     break;
                     case "*":
                     ex = new BigDecimal(oop.get(c-1)).multiply(new BigDecimal(oop.get(c+1)));
                     break;
                     case "/":
                     ex = new BigDecimal(oop.get(c-1)).divideToIntegralValue(new BigDecimal(oop.get(c+1)));
                     break;
                     case "+":
                     ex = new BigDecimal(oop.get(c-1)).add(new BigDecimal(oop.get(c+1)));
                     break;
                     case "-":
                     ex = new BigDecimal(oop.get(c-1)).subtract(new BigDecimal(oop.get(c+1)));
                     break;
                 }oop.set(c, ex.setScale(scale, RoundingMode.UP).stripTrailingZeros().toString());
                oop.remove(c+1);
                oop.remove(c-1);
                }else{
                    continue;
                }c=0;
                }return oop;
            }
}

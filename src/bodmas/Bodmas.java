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
ArrayList<String> contents;
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
                if(box.charAt(i)==')' && box.charAt(i+1)=='('){
                    box=box.substring(0, i+1)+"*"+box.substring(i+1);
                }
                }catch (Exception ignored){}
                if(box.charAt(i)==')'){
                    for(int j=0;j>=0;j--){
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
    public String reading(String read){
            contents = new ArrayList<>();    
            temp = "";                            //temporary String
            for(int i=read.length()-1;i>=0;i--){
              if(Character.isDigit(read.charAt(i))){
                  temp = read.charAt(i)+temp;
                  if(i<=0){
                  contents.add(0, temp);
                  temp="";
                  }
              }else if(read.charAt(i)=='.'){
                   temp =read.charAt(i)+temp;
                  }
                  else if(read.charAt(i)=='-' && (i<=0))
                  {
                  temp =read.charAt(i)+temp;
                  contents.add(0, temp);
                  temp="";
                  }else
                  {
                  contents.add(0,temp);
                  temp="";
                  temp +=read.charAt(i);
                  contents.add(0,temp);
                  temp="";
                  }
              
            }
           contents = opp(contents,"^"); //power
           contents = opp(contents,"|"); //root
           contents = opp(contents,"/"); //Divide
           contents = opp(contents,"*"); //Multiple
           contents = opp(contents,"+"); //Add
           contents = opp(contents,"-"); //Sub
           return contents.get(0);
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

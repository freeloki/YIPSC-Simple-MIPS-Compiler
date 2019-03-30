/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yavuz.yipsc.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author codegenius
 */
public class Tokenizer {
    
    private String codeToken;

    public Tokenizer(String code) {

        if (code != null && !code.isEmpty()) {

            codeToken = code;
        }
    }
    
    public String reduceBlanksToOne(){
        codeToken = codeToken.trim().replaceAll(" +", " ");
        System.out.print("ParseCode:\n" + codeToken);
        return codeToken;
    }
    
    public String removeIncludesAndComments() {
        
        String lines[] = codeToken.split("\\r?\\n");
        String newString = "";
        
        for(int i=0; i<lines.length; i++) {
            if(lines[i].contains(String.valueOf(Indicators.SHARP)) || lines[i].contains(Indicators.COMMENT)) {
             continue;
            }
            newString += lines[i]+"\n";
        }
        codeToken = newString;
        return codeToken;
    }
    
    
    public void parseIntegerVariables() {
        
        List<IntegerVariable> intVariables = new ArrayList<>();
        
         String lines[] = codeToken.split("\\r?\\n");
         
         for(int i=0; i<lines.length; i++) {
             String words[] = lines[i].split(" ");
             for(int j=0; j<words.length; j++) {
                 
                 if(Keywords.INT.equals(words[j])) {
                     IntegerVariable intVar;
                     String candidate = words[j+1];
                     System.out.println("Integer variable candidate detected!!");
                     candidate = candidate.replaceAll(";","");
                     // check it is not a function
                     if(!candidate.contains(String.valueOf(Indicators.PARENTHESES_START))) {
                         if(lines[i].contains(String.valueOf(Indicators.EQUALITY)) && words[j+2].equals(String.valueOf(Indicators.EQUALITY))) {
                             // decleration and assignment detected:
                             String sValue = words[j+3].replaceAll(";", "");
                             int value = Integer.valueOf(sValue);
                             intVar = new IntegerVariable(candidate, value);
                             intVariables.add(intVar);
                             
                         } else {
                             // only decleration detected
                              intVariables.add(new IntegerVariable(candidate,0));
                         }
                        
                     }
                 }
                 System.out.println("Words:\n" + words[j]);
             }
         }
         
         
         
         System.out.println("Integer Variables Size:" + intVariables.size());
         for(IntegerVariable var : intVariables) {
             System.out.println("Var Name: " + var.variableName + " Var Value: " + var.value);
         }
    }
}

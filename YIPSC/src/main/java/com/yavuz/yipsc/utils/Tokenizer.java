/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yavuz.yipsc.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;
import jdk.nashorn.internal.runtime.regexp.RegExp;

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

    public String reduceBlanksToOne() {
        codeToken = codeToken.trim().replaceAll(" +", " ");
        codeToken = codeToken.trim().replaceAll("\\t", "");
        System.out.print("ParseCode:\n" + codeToken);
        return codeToken;
    }

    public String removeIncludesAndComments() {

        String lines[] = codeToken.split("\\r?\\n");
        String newString = "";

        for (int i = 0; i < lines.length; i++) {
            if (lines[i].contains(String.valueOf(Indicators.SHARP)) || lines[i].contains(Indicators.COMMENT)) {
                continue;
            }
            newString += lines[i] + "\n";
        }
        codeToken = newString;
        return codeToken;
    }

    public void parseIntegerVariables() {

        List<IntegerVariable> intVariables = new ArrayList<>();

        String lines[] = codeToken.split("\\r?\\n");

        for (int i = 0; i < lines.length; i++) {
            String words[] = lines[i].split(" ");
            for (int j = 0; j < words.length; j++) {

                if (Keywords.INT.equals(words[j])) {
                    IntegerVariable intVar;
                    String candidate = words[j + 1];
                    System.out.println("Integer variable candidate detected!!");
                    candidate = candidate.replaceAll(";", "");
                    // check it is not a function
                    if (!candidate.contains(String.valueOf(Indicators.PARENTHESES_START))) {
                        if (lines[i].contains(String.valueOf(Indicators.EQUALITY)) && words[j + 2].equals(String.valueOf(Indicators.EQUALITY))) {
                            // decleration and assignment detected:
                            String sValue = words[j + 3].replaceAll(";", "");
                            int value = Integer.valueOf(sValue);
                            intVar = new IntegerVariable(candidate, value);
                            intVariables.add(intVar);

                        } else {
                            // only decleration detected
                            intVariables.add(new IntegerVariable(candidate, 0));
                        }

                    }
                }
                System.out.println("Words:\n" + words[j]);
            }
        }

        System.out.println("Integer Variables Size:" + intVariables.size());
        for (IntegerVariable var : intVariables) {
            System.out.println("Var Name: " + var.variableName + " Var Value: " + var.value);
        }
    }

    public String parseMainFunction() {

        String lines[] = codeToken.split("\\r?\\n");

        String newString = "";
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].contains("void main()") || lines[i].contains("int main()")) {
                continue;
            }
            newString+=lines[i];
        }

        codeToken = newString;
        return codeToken;
    }

    public void parseOperations() {
        String lines[] = codeToken.split("\\r?\\n");
        for (int i = 0; i < lines.length; i++) {

            // operation needs at least one equality and one operand
            if (lines[i].contains(String.valueOf(Indicators.EQUALITY))
                    && (lines[i].contains(Operations.ADDITION)
                    || lines[i].contains(Operations.SUBSTRACTION)
                    || lines[i].contains(Operations.MULTIPLY)
                    || lines[i].contains(Operations.DIVIDE)
                    || lines[i].contains(Operations.AND)
                    || lines[i].contains(Operations.OR)
                    || lines[i].contains(Operations.XOR)
                    || lines[i].contains(Operations.LOGICAL_AND)
                    || lines[i].contains(Operations.LOGICAL_OR))) {

                System.out.println("Operation Line Detected!!!!");
                System.out.println("Converting to postfix...");
                String removeSemicolon = lines[i].replaceAll(";", "");
                System.out.println(removeSemicolon);
                System.out.println("WordSize: " + removeSemicolon.split(" ").length);

                for (String s : removeSemicolon.split(" ")) {
                    System.out.println("Word:  " + s);
                }

                String result = infixToPostfix(removeSemicolon.split(" "));

                System.out.println("RESULT:\n" + result);
            }
        }
    }

    private int Prec(String ch) {
        switch (ch) {
            case "+":
            case "-":
                return 1;

            case "*":
            case "/":
                return 2;

            case "^":
                return 3;
        }
        return -1;
    }

    // The main method that converts given infix expression 
    // to postfix expression.  
    private String infixToPostfix(String[] exp) {
        // initializing empty String for result 
        String result = "";

        // initializing empty stack 
        Stack<String> stack = new Stack<>();

        String regex = "^[a-zA-Z0-9]+$";

        Pattern pattern = Pattern.compile(regex);

        for (int i = 0; i < exp.length; ++i) {

            if (!" ".equals(exp[i])) {
                String c = exp[i];

                System.out.println("C: " + c);

                // If the scanned character is an operand, add it to output. 
                if (pattern.matcher(c).matches()) {
                    result += " " + c;
                } // If the scanned character is an '(', push it to the stack. 
                else if (c.equals(String.valueOf(Indicators.PARENTHESES_START))) {
                    stack.push(c);
                } //  If the scanned character is an ')', pop and output from the stack  
                // until an '(' is encountered. 
                else if (c.equals(String.valueOf(Indicators.PARENTHESES_END))) {
                    while (!stack.isEmpty() && !stack.peek().equals(String.valueOf(Indicators.PARENTHESES_START))) {
                        result += stack.pop();
                    }

                    if (!stack.isEmpty() && !stack.peek().equals(String.valueOf(Indicators.PARENTHESES_START))) {
                        return "Invalid Expression"; // invalid expression                 
                    } else {
                        stack.pop();
                    }
                } else // an operator is encountered 
                {
                    while (!stack.isEmpty() && Prec(c) <= Prec(stack.peek())) {
                        result += " " + stack.pop();
                    }
                    stack.push(c);
                }
            }
        }

        // pop all the operators from the stack 
        while (!stack.isEmpty()) {
            result += " " + stack.pop();
        }

        return result;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yavuz.yipsc.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author codegenius
 */
public class CCompiler {
    
    public CCompiler(){
    }
    
    public boolean compileCFile(String fileName)
    {
        System.out.println("Filename" + fileName);
        String compileFileCommand = "gcc " + fileName;
        System.out.println("Command:\n" + compileFileCommand);
        String resultString = "";
        try {
            System.out.println("Compiling C File");

            Process processCompile = Runtime.getRuntime().exec(compileFileCommand);
            
            System.out.println(processCompile.getOutputStream().toString());

            BufferedReader brCompileError = new BufferedReader(new InputStreamReader(processCompile.getErrorStream()));
            String errorCompile = brCompileError.readLine();
            System.out.println("Error Compiler1 = " + errorCompile);
            if (errorCompile != null) {
                System.out.println("Error Compiler2 = " + errorCompile);

            resultString += errorCompile +"\n";
            return false;
            }

            BufferedReader brCompileRun = new BufferedReader(new InputStreamReader(processCompile.getInputStream()));
            String outputCompile = brCompileRun.readLine();
             System.out.println("Output Compiler 1 = " + outputCompile);
            if (outputCompile != null) {
                System.out.println("Output Compiler 2 = " + outputCompile);
            

            resultString += outputCompile +"\n";
               
            return false;
            }

        } catch (Exception e){
            // TODO: handle exception
            System.out.println("Exception ");
            System.out.println(e.getMessage());
        }
         System.out.println("Returning:\n" + resultString);
         
        return true;
    }
}

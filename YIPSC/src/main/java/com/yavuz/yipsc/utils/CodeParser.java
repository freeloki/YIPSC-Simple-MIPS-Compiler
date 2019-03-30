/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yavuz.yipsc.utils;

/**
 *
 * @author codegenius
 */
public class CodeParser {
    
    
    public CodeParser(String code){
        
        String parseCode = "#include <stdio.h>\n" +
                "#include <stdiasdsado.h>\n" +
" \n" +
"int main()\n" +
"{\n" +
"  printf(\"Hello world\\n\");\n" +
"  return 0;\n" +
"}";
        
        
        for(int i=0; i<parseCode.length();i++) {
            
            // if there is an # we can skip that LINE.
            System.out.println("Checking...\n" +parseCode.charAt(i) );
            if(Indicators.SHARP.equals(parseCode.charAt(i))) {
                String lines[] = parseCode.split("\\r?\\n");
                
                parseCode = parseCode.replace(lines[0],"");
                System.out.println(parseCode);
            }
        }
        
    }
}

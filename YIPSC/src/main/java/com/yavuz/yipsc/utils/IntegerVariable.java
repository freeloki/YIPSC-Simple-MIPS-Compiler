/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yavuz.yipsc.utils;

/**
 *
 * @author codemania
 */
public class IntegerVariable extends Variable {
    
    int value;
    
    
    public IntegerVariable(String variableName, int value) {
        this.variableName = variableName;
        this.value = value;
    }
    
}

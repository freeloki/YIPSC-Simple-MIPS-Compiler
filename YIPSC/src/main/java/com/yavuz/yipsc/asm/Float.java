package com.yavuz.yipsc.asm;
import com.yavuz.yipsc.lexer.*;
import com.yavuz.yipsc.symbols.*;

public class Float extends Register {
    
    private int address;
    
    public Float(Token token, Type type, int address) {
        super(token, type);
        this.address = address;
    }
    
    @Override
    public String getAddress() {
        return "" + "$t" + address;
    }
    
    @Override
    public int getRegisterAddress() {
        return address;
    }
    
    @Override
    public String toString() {
        return token.toString();
    }
}

package com.yavuz.yipsc.inter;
import com.yavuz.yipsc.lexer.*; 
import com.yavuz.yipsc.symbols.*;

public class Temp extends Expr {

   static int count = 0;
   int number = 0;

   public Temp(Type p) { super(Word.temp, p); number = ++count; }

   @Override
   public String toString() { return "t" + number; }
}

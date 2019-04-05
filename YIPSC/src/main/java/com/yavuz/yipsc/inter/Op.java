package com.yavuz.yipsc.inter;
import com.yavuz.yipsc.lexer.*; 
import com.yavuz.yipsc.symbols.*;

public class Op extends Expr {

   public Op(Token tok, Type p)  { super(tok, p); }

   @Override
   public Expr reduce() {
      Expr x = gen();
      Temp t = new Temp(type);
      emit( t.toString() + " = " + x.toString() );
      return t;
   }
}

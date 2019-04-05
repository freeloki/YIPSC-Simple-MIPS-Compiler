/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yavuz.yipsc.inter;

import com.yavuz.yipsc.symbols.Type;

/**
 *
 * @author codegenius
 */
public class Return extends Stmt {

   Expr expr;

   public Return(Expr x) {
      expr = x;
      if( expr.type != Type.Int ) expr.error("int required in return");
   }

   @Override
   public void gen(int b, int a) {
      int label = newlabel(); // label for the code for stmt
      //expr.jumping(0, a);     // fall through on true, goto a on false
      //emitlabel(label);
      //ToDo: implement $ra here.
   }
}

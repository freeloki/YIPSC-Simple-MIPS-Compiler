package com.yavuz.yipsc.inter;

public class Break extends Stmt {

   Stmt stmt;

   public Break() {
      if( Stmt.Enclosing == Stmt.Null ) error("unenclosed break");
      stmt = Stmt.Enclosing;
   }

    /**
     *
     * @param b Before
     * @param a After
     */
    @Override
   public void gen(int b, int a) {
      emit( "goto L" + stmt.after);
   }
}

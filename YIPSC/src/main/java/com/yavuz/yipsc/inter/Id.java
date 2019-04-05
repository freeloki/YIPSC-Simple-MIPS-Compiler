package com.yavuz.yipsc.inter;
import com.yavuz.yipsc.lexer.*; 
import com.yavuz.yipsc.symbols.*;

public class Id extends Expr {

	public int offset;     // relative address

	public Id(Word id, Type p, int b) { super(id, p); offset = b; }

//	public String toString() {return "" + op.toString() + offset;}
}

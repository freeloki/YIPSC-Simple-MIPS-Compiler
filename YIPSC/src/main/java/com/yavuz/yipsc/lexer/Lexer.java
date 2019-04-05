package com.yavuz.yipsc.lexer;
import java.io.*;
import java.util.*;
import com.yavuz.yipsc.symbols.*;
public class Lexer {
    
   public static int line = 1;//counts newlines. Useful for error handling.
   
   
   private static int index = 0;
   private char peek = ' '; //container for next character.
   private final String parseFile; 
   
   private HashMap words = new HashMap();
   
  // private ReadFile r = new ReadFile();           //reads the "test.txt" file.
   
   /**
    * Reserves Reserved words into the HashMap.
    * @param w 
    */
   private void reserve(Word w) { words.put(w.lexeme, w); }

   /**
    * Constructor.
    * @param file : C codeToken to parse. 
    */
   public Lexer(String file) {
      parseFile = file;
      reserve( new Word("if",       Tag.IF)       );
      reserve( new Word("else",     Tag.ELSE)     );
      reserve( new Word("while",    Tag.WHILE)    );
      reserve( new Word("do",       Tag.DO)       );
      reserve( new Word("break",    Tag.BREAK)    );
      reserve( new Word("continue", Tag.CONTINUE) );
      reserve( new Word("return",   Tag.RETURN)   );
      reserve( new Word("main",     Tag.FUNC)     );

      reserve( Word.True );  reserve( Word.False );

      reserve( Type.Int  );  reserve( Type.Char   );
      reserve( Type.Bool );  reserve( Type.Float  );
      reserve( Type.Byte );  reserve( Type.Short  );
      reserve( Type.Long );  reserve( Type.Double );
   }

   /**
    * Reads the next character.
    * @throws IOException 
    */
   private void readch() throws IOException { peek = nextCh(); }
   
   /**
    * Checks if the next character is equal to the character
    * in the parameter.
    * @param c The character to be checked.
    * @return {@code true} if char is equal. Otherwise {@code false}.
    * @throws IOException 
    */
   private boolean readch(char c) throws IOException {
      readch();
      if( peek != c ||  peek == '#' ) return false;
      peek = ' ';
      return true;
   }
   
   /**
    * Scans the string of words; then, it returns the token
    * of each word scanned.
    * @return Token type.
    * @throws IOException 
    */
   public Token scan() throws IOException {
      for( ; ; readch() ) {
         if( peek == ' ' || peek == '\t' ) continue;
         else if( peek == '\n' ) line = line + 1;
         else if( peek == '/' ) {
             char temp = peek;
             if( readch('/') ) {
                 while( peek != '\n' )
                     readch();
             }
             else return new Token(temp);
         }
         else break;
      }
      switch( peek ) {
      case '&':
         if( readch('&') ) return Word.and;  else return new Token('&');
      case '|':
         if( readch('|') ) return Word.or;   else return new Token('|');
      case '=':
         if( readch('=') ) return Word.eq;   else return new Token('=');
      case '!':
         if( readch('=') ) return Word.ne;   else return new Token('!');
      case '<':
         if( readch('=') ) return Word.le;   else return new Token('<');
      case '>':
         if( readch('=') ) return Word.ge;   else return new Token('>');
      }
      if( Character.isDigit(peek) ) {
         int v = 0;
         do {
            v = 10*v + Character.digit(peek, 10); readch();
         } while( Character.isDigit(peek) );
         if( peek != '.' ) return new Num(v);
      }
      if( Character.isLetter(peek) ) {
         StringBuilder b = new StringBuilder();
         do {
            b.append(peek); readch();
         } while( Character.isLetterOrDigit(peek) );
         String s = b.toString();
         Word w = (Word)words.get(s);
         if( w != null ) return w;
         w = new Word(s, Tag.ID);
         words.put(s, w);
         return w;
      } 
      Token tok = new Token(peek); peek = ' ';
      return tok;
   }
   
   private Character nextCh() throws IOException{
       Character current;
       if( index < parseFile.length() ) {
       current = parseFile.charAt(index);
       System.out.println("INDEX: " + index +  "CHAR: " + current);
       
       index++;
       } else {
           return '#';
       }
       return current;
   }
}

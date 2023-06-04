package lexer;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import javafx.application.Application;
import symbols.*;
public class Lexer {
   public static int line = 1;
   char peek = ' ';
   String fileContent="";
   int fileContentLength=0;
   int fileContentIndex=0;
   Hashtable words = new Hashtable();
   void reserve(Word w) { words.put(w.lexeme, w); }

   public Lexer(String fileName) throws IOException {
      ReadFile(fileName);

      reserve( new Word("if",    Tag.IF)    );
      reserve( new Word("else",  Tag.ELSE)  );
      reserve( new Word("while", Tag.WHILE) );
      reserve( new Word("do",    Tag.DO)    );
      reserve( new Word("break", Tag.BREAK) );
      reserve( new Word("for", Tag.FOR) );

      reserve( Word.True );
      reserve( Word.False );

      reserve( Type.Int  );
      reserve( Type.Char  );
      reserve( Type.Bool );
      reserve( Type.Float );
      reserve( Type.Double );
   }

   int ReadFile(String fileName) throws IOException {
      //String fileName = "D:\\data\\test\\newFile3.txt";
      fileContent="";
      fileContentIndex=0;
      // System.out.println(fileName);
      //如果是JDK11用上面的方法，如果不是用这个方法也很容易
      byte[] bytes = Files.readAllBytes(Paths.get(fileName));
      fileContentLength=bytes.length;
      fileContent = new String(bytes, StandardCharsets.UTF_8);
      // System.out.println("run here!");
      return 0;
   }

   void readch() throws IOException {
      //peek = (char)System.in.read();
      if(fileContentIndex<fileContentLength){
         peek=fileContent.charAt(fileContentIndex);
         // System.out.print(peek);
         fileContentIndex++;
      }else{
         peek='\0';
      }
   }

   boolean readch(char c) throws IOException {
      readch();
      if( peek != c ) return false;
      peek = ' ';
      return true;
   }
   public Token scan() throws IOException {
      for( ; ; readch() ) {
         if( peek == ' ' || peek == '\t' ) continue;
         else if( peek == '\n' ) line = line + 1;
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
         float x = v; float d = 10;
         for(;;) {
            readch();
            if( ! Character.isDigit(peek) ) break;
            x = x + Character.digit(peek, 10) / d; d = d*10;
         }
         return new Real(x);
      }
      if( Character.isLetter(peek) ) {
         StringBuffer b = new StringBuffer();
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
}

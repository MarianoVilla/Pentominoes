package DLXPentominoesSolverPack;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;

public class Parser
{
	private StreamTokenizer st;
	
	public Parser(Reader r){
		st = new StreamTokenizer(r);
		st.resetSyntax();	// want my own settings
		st.eolIsSignificant(false);
		st.lowerCaseMode(false);
		st.commentChar('#');
		st.slashSlashComments(false);
		st.slashStarComments(false);
		// Want to allow names with digits in them. Also want to parse
		// moves such as r3, r', r-1, r2^4 etc. 
		st.wordChars('a','z');	// allow letters in words
		st.wordChars('A','Z');	// allow letters in words
		st.wordChars('0','9');	// allow numbers in words
		st.wordChars('^','^');	// allow exponent in words
		st.wordChars('-','-');	// allow minus in words
		st.wordChars('_','_');	// allow underline in words
		st.wordChars('\'','\'');	// allow apostrophe in words
		st.wordChars('.','.');	// allow dot in words incl filenames
		st.wordChars('\\','\\');	// allow backslash in words incl filename
		st.wordChars('/','/');	// allow backslash in words incl filename
		st.whitespaceChars('\u0000','\u0020'); //ignore whitespace
		st.whitespaceChars(',',','); //ignore commas
	}
	public String toString(){ return st.toString(); }
	public int getLine(){ return st.lineno(); }
	public void skipOpenBracket() throws IOException {skipChar('(');}
	public void skipChar(char c) throws IOException {
		int nt=st.nextToken();
		if( nt==StreamTokenizer.TT_WORD )
			throw(new IOException("Expected "+c+" but "+st.sval+" was found."));
		else if( nt!=c)
			throw(new IOException("Missing "+c+"."));	
	}

	public int readNumber() throws IOException {
		return readNumber(0, 0, false, false);
	}
	public int readNumber(int min) throws IOException {
		return readNumber(min,0, true, false);
	}
	public int readNumber(int min,int max) throws IOException {
	   return readNumber(min, max, true, true);
	}
	private int readNumber(int min,int max, boolean useMin, boolean useMax) throws IOException {
		int nt=st.nextToken();
		switch( nt ){
			case StreamTokenizer.TT_WORD:
				int d=0;
				try{
					d = Integer.parseInt(st.sval);
				}catch( NumberFormatException e){
					throw new IOException("Number expected but '"+st.sval+"' found.");
				}
            if(useMin && d<min) throw new IOException("Number "+d+" is below the minimum value "+min+".");
            if(useMax && d>max) throw new IOException("Number "+d+" is above the maximum value "+max+".");
				return d;
			case StreamTokenizer.TT_EOF:
				throw new IOException("Number expected.");
			default:
				throw new IOException("Number expected but '"+(char)nt+"' found.");
		}
	}
   public double readDouble() throws IOException {
      int nt=st.nextToken();
      switch( nt ){
         case StreamTokenizer.TT_WORD:
            double d=0;
            try{
               d = Double.parseDouble(st.sval);
            }catch( NumberFormatException e){
               throw new IOException("Number expected but '"+st.sval+"' found.");
            }
            return d;
         case StreamTokenizer.TT_EOF:
            throw new IOException("Number expected.");
         default:
            throw new IOException("Number expected but '"+(char)nt+"' found.");
      }
   }

	public String readString() throws IOException {
		return readString(false);
	}
	public String readString(boolean allowEnd) throws IOException {
		int nt=st.nextToken();
		switch( nt ){
			case StreamTokenizer.TT_WORD:
				return st.sval;
			case StreamTokenizer.TT_EOF:
			case StreamTokenizer.TT_EOL:
				if(allowEnd) return null;
				throw new IOException("Unexpected end of file.");
		}
		return ""+(char)nt;
	}
	
	public void pushback(){ st.pushBack(); }

   static public String encode(String s){
      if( s.isEmpty() ) return "-";
      StringBuilder sb = new StringBuilder();
      for(int i=0; i<s.length(); i++){
         char c = s.charAt(i);
         sb.append((char)('a'+(c>>4)));
         sb.append((char)('a'+(c&15)));
      }
      return sb.toString();
   }
   static public String decode(String s){
      if( s.equals("-") ) return "";
      StringBuilder sb = new StringBuilder();
      for(int i=0; i<s.length(); i+=2){
         int c1 = s.charAt(i)-'a';
         int c2 = s.charAt(i+1)-'a';
         sb.append((char)((c1<<4)+c2));
      }
      return sb.toString();
   }
}


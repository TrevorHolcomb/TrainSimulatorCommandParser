/* Tester
*/

import java.util.*;
import w16cs350.controller.cli.parser.*;

public class test
{
	public static void main(String[] args)
	{
		Controller con = new Controller();
		   CommandLineInterface cl = new CommandLineInterface(con);
		   ActionProcessor ap = new ActionProcessor(cl);
		   MyParserHelper ph = new MyParserHelper(ap);
		   CommandParser myParser = new CommandParser(ph, "UNCOUPLE STOCK id1 AND id2");
	      myParser.parse();
	}//end of main
	
}//end of test
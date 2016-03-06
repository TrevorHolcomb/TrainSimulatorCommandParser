/* Tester
*/

import java.util.*;

import w16cs350.controller.ActionProcessor;
import w16cs350.controller.Controller;
import w16cs350.controller.cli.CommandLineInterface;
import w16cs350.controller.cli.parser.*;

public class test
{
	public static void main(String[] args)
	{
		Controller con = new Controller();
		   CommandLineInterface cl = new CommandLineInterface(con);
		   ActionProcessor ap = new ActionProcessor(cl);
		   MyParserHelper ph = new MyParserHelper(ap);
		   //CommandParser myParser = new CommandParser(ph, "UNCOUPLE STOCK id1 AND id2");
		   CommandParser myParser1 = new CommandParser(ph, "USE world AS REFERENCE 8*24'34\" / 5 * 13' 50 \"");
		   myParser1.parse();
		   CommandParser myParser = new CommandParser(ph, "CREATE TRACK BRIDGE id1 REFERENCE $world DELTA START 23 : 45 END 23 : 23");
	      myParser.parse();
	}//end of main
	
}//end of test

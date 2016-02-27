/* Tester
*/

import java.util.*;
import w16cs350.controller.cli.parser.*;

public class test
{
	public static void main(String[] args)
	{
	   CommandParser myParser = new CommandParser("UNCOUPLE STOCK id1 AND id2");
      myParser.parse();
	}//end of main
	
}//end of test
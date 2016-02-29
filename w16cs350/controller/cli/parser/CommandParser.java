/* Team 1
   CommandParser class
*/

package w16cs350.controller.cli.parser;
import w16cs350.controller.command.*;
import w16cs350.controller.command.creational.*;
import w16cs350.controller.command.meta.*;
import w16cs350.controller.command.behavioral.*;
import w16cs350.controller.command.structural.*;
import w16cs350.datatype.Angle;

import java.util.*;

public class CommandParser
{
   //private A_ParserHelper parserHelper;
   private String commandText;

   public CommandParser(String commandText)
   {
      // I took out the parserHelper for the time being so
      // that we can actually test our strings by only passing through a string
      //this.parserHelper = parserHelper;
      this.commandText = commandText;
   }
   
   public void parse()
   {
      String[] commandArray = this.commandText.split("\\s+");
      String subCommand;
      switch (commandArray[0]){
         case "DO":
            subCommand = createSubCommand(commandArray);
            // this is where you would do this.parserHelper.getActionProcessor().schedule(createBehavioralCommand(subCommand));
            A_Command commandBehavioral = createBehavioralCommand(subCommand);
            break;
         case "CREATE":
            subCommand = createSubCommand(commandArray);
            // this is where you would do this.parserHelper.getActionProcessor().schedule(createCreationalCommand(subCommand));
            A_Command commandCreational = createCreationalCommand(subCommand);
            break;
         case "@EXIT":
            // this is where you would do this.parserHelper.getActionProcessor().schedule(createExitCommand());
            A_Command commandExit = createExitCommand();
            break;
         case "@RUN":
            subCommand = createSubCommand(commandArray);
            // this is where you would do this.parserHelper.getActionProcessor().schedule(createMetaRunCommand(subCommand));
            A_Command commandMetaRun = createMetaRunCommand(subCommand);
            break;
         case "@SCHEDULE":
            subCommand = createSubCommand(commandArray);
            // this is where you would do this.parserHelper.getActionProcessor().schedule(createMetaSchedule(subCommand));
            A_Command commandMetaSchedule = createMetaSchedule(subCommand);
            break;
         case "OPEN":
            subCommand = createSubCommand(commandArray);
            // this is where you would do this.parserHelper.getActionProcessor().schedule(createMetaView(subCommand));
            A_Command commandMetaView = createMetaView(subCommand);
            break;
         case "CLOSE":
            subCommand = createSubCommand(commandArray);
            // this is where you would do this.parserHelper.getActionProcessor().schedule(createMetaViewDestroy(subCommand));
            A_Command commandMetaViewDestroy = createMetaViewDestroy(subCommand);
            break;
         case "COMMIT":
            // this is where you would do this.parserHelper.getActionProcessor().schedule(createStructuralCommit());
            A_Command commandStructuralCommit = createStructuralCommit();
            break;
         case "COUPLE":
            subCommand = createSubCommand(commandArray);
            // this is where you would do this.parserHelper.getActionProcessor().schedule(createStructuralCouple(subCommand));
            A_Command commandStructuralCouple = createStructuralCouple(subCommand);
            break;
         case "LOCATE":
            subCommand = createSubCommand(commandArray);
            // this is where you would do this.parserHelper.getActionProcessor().schedule(createStructuralLocate(subCommand));
            A_Command commandStructuralLocate = createStructuralLocate(subCommand);
            break;
         case "UNCOUPLE":
            subCommand = createSubCommand(commandArray);
            // this is where you would do this.parserHelper.getActionProcessor().schedule(createStructuralUncouple(subCommand));
            A_Command commandStructuralUncouple = createStructuralUncouple(subCommand);
            break;
         case "USE":
            // need to have the parserHelper in order to add a reference
            // this will be completed later
            break;
         default:
            throw new IllegalArgumentException("Invalid Command");
      }
   }
   
   // helper method that strips the first element of the array and returns
   // a string with the rest of the elements
   private String createSubCommand(String[] commandArray)
   {
      StringBuilder subCommand = new StringBuilder();
      for(int i = 1; i < commandArray.length; i++)
      {
         subCommand.append(commandArray[i]);
         if(i < commandArray.length-1)
            subCommand.append(" ");
      }
      return subCommand.toString();
   }  
   
   /*public A_ParserHelper getParserHelper()
   {
      return this.parserHelper;
   }*/
   
   public String getCommandText()
   {
      return this.commandText;
   }
   
   /*
    * @Author Marco Karier
    * @param subCoomand with out the intial word
    *
    *This Method is called after the first word is DO
    */
   private A_Command createBehavioralCommand(String subCommand)
   {
      String[] commandArray = subCommand.split("\\s+");
      subCommand = createSubCommand(commandArray);
      A_Command command = null;
      switch(commandArray[0])
      {
      	case "BRAKE":
      		command = createBehavioralCommandBrake(subCommand);
      		break;
      	case "SELECT":
      		command = createBehavioralCommandSelect(subCommand);
      		break;
      	case "SET":
      		command = createBehavioralCommandSet(subCommand);
      		break;
      	default:
      		return null;
      }//end of switch
      return command;
   }//end createBehavioralCommand
   
   /*
    * @Author Marco Karier
    * @Param a substring of commands
    * 
    * This Method is creating the command behavioral brake
    */
   private A_Command createBehavioralCommandBrake(String subCommand)
   {
	   String[] commandArray = subCommand.split("\\s+");
	   subCommand = createSubCommand(commandArray);
	   A_Command command = new CommandBehavioralBrake(commandArray[0]);
	   return command;
   }//end of createBehavioralCommandBrake
   
   /*
    * @Author Marco Karier
    * @Param a substring of commands when the second word was select
    * 
    * this method create a commandBehavioralSelect and checks to find which kind of select
    */
   private A_Command createBehavioralCommandSelect(String subCommand)
   {
	   String[] commandArray = subCommand.split("\\s+");
	   subCommand = createSubCommand(commandArray);
	   A_Command command = null;
	   switch(commandArray[0])
	   {
	   	case"DRAWBRIDGE":
		   command = createBehavioralCommandSelectDrawbridge(subCommand);
		   break;
	   	case"ROUNDHOUSE":
	   		command = createBehavioralCommandSelectRoundhouse(subCommand);
	   		break;
	   	case"SWITCH":
	   		command = createBehavioralCommandSelectSwitch(subCommand);
	   }//end of switch
	   return command;
   }//end of createBehavioralCommandSelect()
   
   /*
    * @Author Marco Karier
    * @Param command subString
    * 
    * This method takes a substring and creates  drawbridge command
    */
   private A_Command createBehavioralCommandSelectDrawbridge(String subCommand)
   {
	   String[] commandArray = subCommand.split("\\s+");
	   A_Command command = new CommandBehavioralSelectBridge(commandArray[0], commandArray[2].equals("UP"));
	   return command;
   }//end of createBehavioralCommandSelectDrawbridge()
   
   /*
    *@Author Marco Karier
    *@param commandSubstring
    *This method takes a subCommandstring and outputs a CommandBehavioralSelectRoundhouse; 
    */
   private A_Command createBehavioralCommandSelectRoundhouse(String subCommand)
   {
	   String[] commandArray = subCommand.split("\\s+");
	   double angle = Double.parseDouble(commandArray[2]);
	   A_Command command = new CommandBehavioralSelectRoundhouse(commandArray[0], new Angle(angle), commandArray[3].equals("CLOCKWISE"));
	   return command;
   }//end of creawteBehavioralCommandSelectRoundhouse
   
   /*
    * @Author Marco Karier
    * @Param command Substring
    * 
    * This method takes a substring and outputs a CommandBehavioralSelectSwitch
    */
   private A_Command createBehavioralCommandSelectSwitch(String subCommand)
   {
	   String[] commandArray = subCommand.split("\\s+");
	   A_Command command = new CommandBehavioralSelectSwitch(commandArray[0], commandArray[2].equals("PRIMARY"));
	   return command;
   }//end of createBehavioralCommandSelectSwitch
   
   /*
    * @Author Marco Karier
    * @Param command subString
    * 
    * this takes a string and finds what kind of set command the user wants
    */
   private A_Command createBehavioralCommandSet(String subString)
   {
	   String[] commandArray = subString.split("\\s+");
	   subString = createSubCommand(commandArray);
	   A_Command command = null;
	   if(commandArray[0].equals("REFERENCE"))
	   {
		   command = new CommandBehavioralSetReference(commandArray[1]);
	   }//end of if
	   else
	   {
		   switch(commandArray[1])
		   {
		   	case"DIRECTION":
		   		command = createBehavioralCommandSetDirection(commandArray);
		   		break;
		   	case"SPEED":
		   		command = createBehavioralCommandSetSpeed(commandArray);
		   }//end of switch
	   }//end of else
	   return command;
   }//end of createBehavioralCommandSet()
   
   /*
    * @Author Marco Karier
    * @Param a string array to create the variables needed for the constructor of CommandBehavioralSetDirection()
    * 
    * This method is going to create a BehabioralCommandSetDirection
    */
   private A_Command createBehavioralCommandSetDirection(String[] commandArray)
   {
	   return new CommandBehavioralSetDirection(commandArray[0], commandArray[2].equals("FORWARD"));
   }//end of createBehavioralCommandSetDirection(String[] commandArray)
   
   /*
    * @Author Marco Karier
    * @Param a string Array used to create a CommandBehavioralSetSpeed
    * 
    * This returns a CommandBehavioralSetSpeed object
    */
   private A_Command createBehavioralCommandSetSpeed(String[] commandArray)
   {
	   return new CommandBehavioralSetSpeed(commandArray[0], Double.parseDouble(commandArray[2]));
   }//end of createBehavioralCommandSetSpeed
   
   
   // splits the creationals commands further
   private A_Command createCreationalCommand(String subCommand)
   {
      String[] commandArray = subCommand.split("\\s+");
      return null;
   }
   
   // creates and returns CommandMetaDoMetaExit()
   private A_Command createExitCommand()
   {
      return new CommandMetaDoExit();
   }
   
   // returns a CommandMetaDoMetaRun command
   private A_Command createMetaRunCommand(String subCommand)
   {
      String[] commandArray = subCommand.split("\\s+");
      return null;
   }
   
   // returns a CommandMetaDoMetaSchedule command
   private A_Command createMetaSchedule(String subCommand)
   {
      String[] commandArray = subCommand.split("\\s+");
      return null;
   }
   
   // returns a CommandMetaViewGenerate command
   private A_Command createMetaView(String subCommand)
   {
      String[] commandArray = subCommand.split("\\s+");
      return null;
   }
   
   // returns a CommandMetaViewDestroy command
   private A_Command createMetaViewDestroy(String subCommand)
   {
      String[] commandArray = subCommand.split("\\s+");
      return null;
   }
   
   // creates and returns CommandStructuralCommit
   private A_Command createStructuralCommit()
   {
      return new CommandStructuralCommit();
   }
   
   // returns a CommandStructuralCouple command
   private A_Command createStructuralCouple(String subCommand)
   {
      String[] commandArray = subCommand.split("\\s+");
      return null;
   }
   
   // returns a CommandStructuralLocate command
   private A_Command createStructuralLocate(String subCommand)
   {
      String[] commandArray = subCommand.split("\\s+");
      return null;
   }
   
   // returns a CommandStructuralUncouple command
   private A_Command createStructuralUncouple(String subCommand)
   {
      String[] commandArray = subCommand.split("\\s+");
      return null;
   }
}
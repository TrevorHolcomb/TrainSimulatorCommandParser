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
import w16cs350.controller.cli.*;
import w16cs350.datatype.*;

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
      switch (commandArray[0].toUpperCase()){
         case "DO":
            subCommand = createSubCommand(commandArray);
            // this is where you would do this.parserHelper.getActionProcessor().schedule(createBehavioralCommand(subCommand));
            A_Command commandBehavioral = createBehavioralCommand(subCommand);
            break;
         case "CREATE":
            subCommand = createSubCommand(commandArray);
            //this.parserHelper.getActionProcessor().schedule(createCreationalCommand(subCommand));
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
      A_Command command = null;
      switch (commandArray[0].toUpperCase()){
         case "POWER":
            subCommand = createSubCommand(commandArray);
            
            command = createPowerCommand(subCommand);
            break;
         case "STOCK":
            subCommand = createSubCommand(commandArray);
            
            command = createStockCommand(subCommand);
            break;
              
         case "TRACK":
            subCommand = createSubCommand(commandArray);
            command = createTrackCommand(subCommand);
            break;
      }
      return command;
   }
   
   // creates a creational power command and returns it
   private A_Command createPowerCommand(String subCommand)
   {
      String[] commandArray = subCommand.split("[()]");
      StringBuilder strBuild = new StringBuilder();
      for(int i = 0; i < commandArray.length; i++)
      {
         strBuild.append(commandArray[i]);
         if(i < commandArray.length-1)
             strBuild.append(" ");
      }
      subCommand = strBuild.toString();
      commandArray = subCommand.split("\\s+");
      A_Command command = null;
      switch (commandArray[0].toUpperCase()){
        case "CATENARY":
           List<String> poles = new ArrayList<String>();
           for(int i = 4; i < commandArray.length; i++)
           {
               poles.add(commandArray[i]);
           }
           command = new CommandCreatePowerCatenary(commandArray[1], poles);
           break;
        case "POLE":
           boolean isFromStart = true;
           switch (commandArray[8].toUpperCase()){
               case "START":
                 isFromStart = true;
                 break;
               case "END":
                 isFromStart = false;
           }
           TrackLocator trLoc = new TrackLocator(commandArray[4], Double.parseDouble(commandArray[6]), isFromStart);
           command = new CommandCreatePowerPole(commandArray[2], trLoc);
           break;
        case "STATION":
           command = createCommandStation(subCommand);
           break;
        case "SUBSTATION":
           command = createCommandSubStation(subCommand);
           break;
      }
      return command;
   }
   
   // creates a station and returns it
   private A_Command createCommandStation(String subCommand)
   {
      String[] commandArray = subCommand.split("\\s+");
      A_Command command = null;
      Latitude lat = new Latitude(Integer.parseInt(commandArray[3]), Integer.parseInt(commandArray[5]), Double.parseDouble(commandArray[7]));
      Longitude lon = new Longitude(Integer.parseInt(commandArray[10]), Integer.parseInt(commandArray[12]), Double.parseDouble(commandArray[14]));
      CoordinatesWorld ref = new CoordinatesWorld(lat, lon);
      CoordinatesDelta del = new CoordinatesDelta(Integer.parseInt(commandArray[17]), Integer.parseInt(commandArray[19]));
      List<String> poles = new ArrayList<String>();
      for(int i = 22; i < commandArray.length; i++)
      {
          poles.add(commandArray[i]);
      }
      command = new CommandCreatePowerStation(commandArray[1], ref, del, poles);
      return command;
   }
   
   // creates a substation and returns it
   private A_Command createCommandSubStation(String subCommand)
   {
      String[] commandArray = subCommand.split("\\s+");
      A_Command command = null;
      Latitude lat = new Latitude(Integer.parseInt(commandArray[3]), Integer.parseInt(commandArray[5]), Double.parseDouble(commandArray[7]));
      Longitude lon = new Longitude(Integer.parseInt(commandArray[10]), Integer.parseInt(commandArray[12]), Double.parseDouble(commandArray[14]));
      CoordinatesWorld ref = new CoordinatesWorld(lat, lon);
      CoordinatesDelta del = new CoordinatesDelta(Integer.parseInt(commandArray[17]), Integer.parseInt(commandArray[19]));
      List<String> catenaries = new ArrayList<String>();
      for(int i = 22; i < commandArray.length; i++)
      {
          catenaries.add(commandArray[i]);
      }
      command = new CommandCreatePowerSubstation(commandArray[1], ref, del, catenaries);
      return command;
   }


   // creates creational stock command and returns it
   private A_Command createStockCommand(String subCommand)
   {
      String[] commandArray = subCommand.split("\\s+");
      A_Command command = null;
      if(commandArray[0].toUpperCase().equals("ENGINE"))
      {
            commandArray = subCommand.split("[()]");
            StringBuilder strBuild = new StringBuilder();
            for(int i = 0; i < commandArray.length; i++)
            {
                strBuild.append(commandArray[i]);
                if(i < commandArray.length-1)
                     strBuild.append(" ");
            }
            subCommand = strBuild.toString();
            commandArray = subCommand.split("\\s+");
            boolean isFromStart = true, isFacing = true;
            switch (commandArray[10].toUpperCase()){
                case "START":
                  isFromStart = true;
                  break;
                case "END":
                  isFromStart = false;
            }
            switch (commandArray[12].toUpperCase()){
                case "START":
                  isFacing = true;
                  break;
                case "END":
                  isFacing = false;
            }
            TrackLocator trLoc = new TrackLocator(commandArray[6], Double.parseDouble(commandArray[8]), isFromStart);
            command = new CommandCreateStockEngineDiesel(commandArray[1], trLoc, isFacing);
      }
      else
      {
         subCommand = createSubCommand(commandArray);
         commandArray = subCommand.split("\\s+");
         
         switch (commandArray[commandArray.length-1].toUpperCase()){
            case "BOX":
               command = new CommandCreateStockCarBox(commandArray[0]);            
               break;
            case "CABOOSE":
               command = new CommandCreateStockCarCaboose(commandArray[0]);
               break;
            case "FLATBED":
               command = new CommandCreateStockCarFlatbed(commandArray[0]);
               break;
            case "PASSENGER":
               command = new CommandCreateStockCarPassenger(commandArray[0]);
               break;
            case "TANK":
               command = new CommandCreateStockCarTank(commandArray[0]);
               break;
            case "TENDER":
               command = new CommandCreateStockCarTender(commandArray[0]);
               break;   
         }
      }
      return command;
   }
    
   /*
    * further brakes down the create track and returns specified command, 
    * if it is a BRIDGE or SWITCH it must be broken down further
    */

   private A_Command createTrackCommand(String command) {
	String[] commandArray = command.split("\\s+");
	String subCommand;
	A_Command commandType = null;
	String first = commandArray[0].toUpperCase();
	
	if(first.equals("BRIDGE") || first.equals("SWITCH") || first.equals("LAYOUT") || first.equals("ROUNDHOUSE")){
		subCommand = createSubCommand(commandArray);
		commandType = createTracksubCommand(subCommand);
		return commandType;
	}
	for(int i = 0; i < commandArray.length; i++){
		System.out.println(i + ". " + commandArray[i]);
	}
	
	String id = commandArray[1];
	Latitude latitude = new Latitude(Double.parseDouble(commandArray[3]));
	Longitude longitude = new Longitude(Double.parseDouble(commandArray[5]));
	CoordinatesWorld worldCoor = new CoordinatesWorld(latitude, longitude);
	double startX = Double.parseDouble(commandArray[8]);
	double startY = Double.parseDouble(commandArray[10]);
	CoordinatesDelta deltaStart = new CoordinatesDelta(startX, startY);
	double endX = Double.parseDouble(commandArray[12]);
	double endY = Double.parseDouble(commandArray[14]);
	CoordinatesDelta deltaEnd = new CoordinatesDelta(endX, endY);
	PointLocator locator = new PointLocator(worldCoor, deltaStart, deltaEnd);
	
	switch(commandArray[0]){
		
		case "CROSSING":
			commandType = new CommandCreateTrackCrossing(id, locator);
			break;
		
		case "CROSSOVER":
			double start2X = Double.parseDouble(commandArray[16]);
			double start2Y = Double.parseDouble(commandArray[18]);
			CoordinatesDelta delta2Start = new CoordinatesDelta(start2X, start2Y);
			double end2X = Double.parseDouble(commandArray[20]);
			double end2Y = Double.parseDouble(commandArray[22]);
			CoordinatesDelta delta2End = new CoordinatesDelta(end2X, end2Y);
			commandType = new CommandCreateTrackCrossover(id, worldCoor, deltaStart, deltaEnd, delta2Start, delta2End);
			break;
		
		case "CURVE":
			String origin = commandArray[15].toUpperCase();
			if(origin.equals("ORIGIN")){
				double originX = Double.parseDouble(commandArray[16]);
				double originY = Double.parseDouble(commandArray[18]);
				CoordinatesDelta deltaOrigin = new CoordinatesDelta(originX, originY);
				commandType = new CommandCreateTrackCurve(id, worldCoor, deltaStart, deltaEnd, deltaOrigin);
			}
			else if(origin.equals("DISTANCE")){
				double distance = Double.parseDouble(commandArray[17]);
				commandType = new CommandCreateTrackCurve(id, worldCoor, deltaStart, deltaEnd, distance);
			}
			break;
		
		case "END":
			commandType = new CommandCreateTrackEnd( id, locator); 
			break;
				
		case "STRAIGHT":
			commandType = new CommandCreateTrackStraight(id, locator);
			break;
				
	}
	return commandType;
   }
   
   private A_Command createTracksubCommand(String subCommand) {
	// TODO Auto-generated method stub
	return null;
}

private A_Command createBridgeCommand(String command) {
	    String[] commandArray = command.split("\\s+");
	    A_Command commandType = null;
	    String id;
	    
	    if(commandArray[0].toUpperCase().equals("DRAW")){
	    	id = commandArray[1];
	    	if(commandArray[3].charAt(0) == '$'){
				
			}
	    	else{
	    		Latitude latitude = new Latitude(Double.parseDouble(commandArray[3]));
				Longitude longitude = new Longitude(Double.parseDouble(commandArray[5]));
				CoordinatesWorld worldCoor = new CoordinatesWorld(latitude, longitude);
				double startX = Double.parseDouble(commandArray[8]);
				double startY = Double.parseDouble(commandArray[10]);
				CoordinatesDelta deltaStart = new CoordinatesDelta(startX, startY);
				double endX = Double.parseDouble(commandArray[12]);
				double endY = Double.parseDouble(commandArray[14]);
				CoordinatesDelta deltaEnd = new CoordinatesDelta(endX, endY);
				PointLocator pi = new PointLocator(worldCoor, deltaStart, deltaEnd);
				Angle angle = new Angle(Double.parseDouble(commandArray[16]));
				commandType = new CommandCreateTrackBridgeDraw(id, pi, angle);
	    	}
	    	
	    }
		return commandType;
   }
   
   private A_Command createSwitchCommand(String command) {
	   String[] commandArray = command.split("\\s+");
	   String subCommand;
	   A_Command commandType = null;
	   
	   String id = commandArray[1];
	   Latitude latitude = new Latitude(Double.parseDouble(commandArray[3]));
	   Longitude longitude = new Longitude(Double.parseDouble(commandArray[5]));
	   CoordinatesWorld worldCoor = new CoordinatesWorld(latitude, longitude);
	   double startX = Double.parseDouble(commandArray[8]);
	   double startY = Double.parseDouble(commandArray[10]);
	   CoordinatesDelta deltaStart = new CoordinatesDelta(startX, startY);
	   double endX = Double.parseDouble(commandArray[12]);
	   double endY = Double.parseDouble(commandArray[14]);
	   CoordinatesDelta deltaEnd = new CoordinatesDelta(endX, endY);
		
	   switch(commandArray[0]){
		   case "WYE":
			   subCommand = createSubCommand(commandArray);
			   commandType = createWyeCommand(subCommand);
			   break;
			   
		   case "TURNOUT":
			   double curveStartX = Double.parseDouble(commandArray[18]);
			   double curveStartY = Double.parseDouble(commandArray[20]);
			   CoordinatesDelta curveStart = new CoordinatesDelta(curveStartX, curveStartY);
			   double curveEndX = Double.parseDouble(commandArray[22]);
			   double curveEndY = Double.parseDouble(commandArray[24]);
			   CoordinatesDelta curveEnd = new CoordinatesDelta(curveEndX, curveEndY);
			   double distance = Double.parseDouble(commandArray[27]);
			   /*ShapeArc curve = new ShapeArc(worldCoor, curveStart, curveEnd, distance);
			   CoordinatesDelta origin = curve.getDeltaOrigin();
			   commandType = new CommandCreateTrackSwitchTurnout(id, worldCoor, deltaStart, deltaEnd,  curveStart, 
															   curveEnd, origin);*/
			   break;
	   }
	return commandType;
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
   
   /* @Author Marco Karier
    * creates and returns CommandStructuralCommit
    */
   private A_Command createStructuralCommit()
   {
      return new CommandStructuralCommit();
   }//end of createStructuralCommit
   
   /* @Author Marco Karier
    * @Param takes in a substring that holds the ids from couple.
    *  returns a CommandStructuralCouple command
    */
   private A_Command createStructuralCouple(String subCommand)
   {
      String[] commandArray = subCommand.split("\\s+");
      return new CommandStructuralCouple(commandArray[1], commandArray[3]);
   }//end of craeteStructuralCouple
   
   /* @Author Marco Karier
    * @Param takes in a substring that holds the id on track and distance from either start or end.
    *  returns a CommandStructuralLocate command
    */
   private A_Command createStructuralLocate(String subCommand)
   {
      String[] commandArray = subCommand.split("\\s+");
      String id1 = commandArray[1];
      String id2 = commandArray[4];
      double distance = Double.parseDouble(commandArray[6]);
      String from = commandArray[8];
      return new CommandStructuralLocate(id1, new TrackLocator(id2, distance, from.equals("START")));
   }
   
   /* @Author Marco Karier
    * @Param A substring command that contain a uncouple command from id1 and id2
    *  returns a CommandStructuralUncouple command
    */
   private A_Command createStructuralUncouple(String subCommand)
   {
      String[] commandArray = subCommand.split("\\s+");
      return new CommandStructuralUncouple(commandArray[1], commandArray[3]);
   }//end of createStructuralUncouple
}
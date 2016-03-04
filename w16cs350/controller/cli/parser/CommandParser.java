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
   private MyParserHelper parserHelper;
   private String commandText;
   private double time;

   public CommandParser(MyParserHelper parserHelper, String commandText)
   {
      // I took out the parserHelper for the time being so
      // that we can actually test our strings by only passing through a string
      this.parserHelper = parserHelper;
      this.commandText = commandText;
   }
   
   public void parse()
   {
      String [] multCom = this.commandText.split(";");
      String singleCommand; 
      for(int i = 0; i < multCom.length; i++)
      {
         singleCommand = multCom[i];
         String[] commandArray = this.commandText.split("\\s+");
         String subCommand;
         boolean scheduleFlag = false;
         
         /* 
          * If it is a schedule command then set trigger time and flag to true
          * updates the commandArray to include only the command to input
          */
         
         if(commandArray[0].toUpperCase().equals("@SCHEDULE")){
       	  scheduleFlag = true;
       	  time = Double.parseDouble(commandArray[2]);
       	  commandArray = createScheduleCommand(commandArray);
         }
         
         
         switch (commandArray[0].toUpperCase()){
            case "DO":
           	 subCommand = createSubCommand(commandArray);
           	 if(scheduleFlag){
           		 A_Command commandBehavioral = createBehavioralCommand(subCommand);
           	 }
               this.parserHelper.getActionProcessor().schedule(createBehavioralCommand(subCommand));
               //A_Command commandBehavioral = createBehavioralCommand(subCommand);
               break;
            case "CREATE":
               subCommand = createSubCommand(commandArray);
               this.parserHelper.getActionProcessor().schedule(createCreationalCommand(subCommand));
               //A_Command commandCreational = createCreationalCommand(subCommand);
               break;
            case "@EXIT":
               this.parserHelper.getActionProcessor().schedule(createExitCommand());
               //A_Command commandExit = createExitCommand();
               break;
            case "@RUN":
               subCommand = createSubCommand(commandArray);
               this.parserHelper.getActionProcessor().schedule(createMetaRunCommand(subCommand));
               //A_Command commandMetaRun = createMetaRunCommand(subCommand);
               break;
            /*case "@SCHEDULE":
               subCommand = createSubCommand(commandArray);
               // this is where you would do this.parserHelper.getActionProcessor().schedule(createMetaSchedule(subCommand));
               A_Command commandMetaSchedule = createMetaSchedule(subCommand);
               break;*/
            case "OPEN":
               subCommand = createSubCommand(commandArray);
               /*
                * if the schedule flag is on then create an command and pass the the scheduler
                */
               if(scheduleFlag){
          		 	A_Command commandMetaView = createMetaView(subCommand);
          		 	//this.parserHelper.getActionProcessor().schedule(createMetaSchedule(subCommand))
          		 	A_Command commandMetaSchedule = createMetaSchedule(commandMetaView);
          	 	}
               /*
                * else run the standard command
                */
               else{
   	            this.parserHelper.getActionProcessor().schedule(createMetaView(subCommand));
   	            //A_Command commandMetaView = createMetaView(subCommand);
               }    
               break;
            case "CLOSE":
               subCommand = createSubCommand(commandArray);
               this.parserHelper.getActionProcessor().schedule(createMetaViewDestroy(subCommand));
               //A_Command commandMetaViewDestroy = createMetaViewDestroy(subCommand);
               break;
            case "COMMIT":
               this.parserHelper.getActionProcessor().schedule(createStructuralCommit());
               //A_Command commandStructuralCommit = createStructuralCommit();
               break;
            case "COUPLE":
               subCommand = createSubCommand(commandArray);
               this.parserHelper.getActionProcessor().schedule(createStructuralCouple(subCommand));
               //A_Command commandStructuralCouple = createStructuralCouple(subCommand);
               break;
            case "LOCATE":
               subCommand = createSubCommand(commandArray);
               this.parserHelper.getActionProcessor().schedule(createStructuralLocate(subCommand));
               //A_Command commandStructuralLocate = createStructuralLocate(subCommand);
               break;
            case "UNCOUPLE":
               subCommand = createSubCommand(commandArray);
               this.parserHelper.getActionProcessor().schedule(createStructuralUncouple(subCommand));
               //A_Command commandStructuralUncouple = createStructuralUncouple(subCommand);
               break;
            case "USE":
           	 subCommand = createSubCommand(commandArray);
               // need to have the parserHelper in order to add a reference
               // this will be completed later
           	 setCoordinatesWorldReference(subCommand);
               break;
            default:
               throw new IllegalArgumentException("Invalid Command");
         }
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
   
   private String[] createScheduleCommand(String[] commandArray)
   {
      String[] temp = new String[commandArray.length - 3];
      int i, k;
      for( i = 0, k = 3; i < temp.length; i++, k++)
      {
         temp[i] = commandArray[k];
      }
      return temp;
   } 
   
   public A_ParserHelper getParserHelper()
   {
      return this.parserHelper;
   }
   
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
	      String id = commandArray[1];
	      A_Command command = null;
      int hasRef = subCommand.indexOf('$');
      CoordinatesWorld ref = null;
      if(hasRef > 0)
      {
         String reference = subCommand.substring(hasRef+1, subCommand.length());
        
            
         String [] myRef = reference.split("\\s+");
         String refId;
         if(myRef[0].equals(""))
            refId = myRef[1];
         else
            refId = myRef[0];
         ref = this.parserHelper.getReference(refId);
      }
      else
      {
	         ref = getCoordWorld(subCommand);
      }
	      CoordinatesDelta del = getCoordDel(subCommand);
	      List<String> poles = new ArrayList<String>();
	      subCommand = subCommand.toLowerCase();
	      int statIndex = subCommand.indexOf("with");
	      String stationCheck = subCommand.substring(statIndex, subCommand.length());
	      statIndex = stationCheck.indexOf("substations") + 12;
	      if(statIndex < 12)
	      {
	         statIndex = stationCheck.indexOf("substation") + 11;
	      }
	      subCommand = stationCheck.substring(statIndex, stationCheck.length());
	      while(subCommand.substring(0,1).equals(" "))
	      {
	          subCommand = subCommand.substring(1, subCommand.length());
	      }
	      commandArray = subCommand.split("\\s+");
	      for(int i = 0; i < commandArray.length; i++)
	      {
	          poles.add(commandArray[i]);
	      }
	      command = new CommandCreatePowerStation(id, ref, del, poles);
	      return command;

   }
   
   // creates a substation and returns it
   private A_Command createCommandSubStation(String subCommand)
   {
	   String[] commandArray = subCommand.split("\\s+");
	      A_Command command = null;
	      String id = commandArray[1];
      int hasRef = subCommand.indexOf('$');
      CoordinatesWorld ref = null;
      if(hasRef > 0)
      {
         String reference = subCommand.substring(hasRef+1, subCommand.length());
        
            
         String [] myRef = reference.split("\\s+");
         String refId;
         if(myRef[0].equals(""))
            refId = myRef[1];
         else
            refId = myRef[0];
         ref = this.parserHelper.getReference(refId);
      }
      else
      {
	         ref = getCoordWorld(subCommand);
      }
	      CoordinatesDelta del = getCoordDel(subCommand);
	      List<String> catenaries = new ArrayList<String>();
	      subCommand = subCommand.toLowerCase();
	      int cantIndex = subCommand.indexOf("catenaries")+ 11;
	      subCommand = subCommand.substring(cantIndex, subCommand.length()-1);
	      commandArray = subCommand.split("\\s+");
	      for(int i = 0; i < commandArray.length; i++)
	      {
	          catenaries.add(commandArray[i]);
	      }
	      command = new CommandCreatePowerSubstation(id, ref, del, catenaries);
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
   
   private CoordinatesDelta processDelta(String[] command, int index){
	   double x = Double.parseDouble(command[index]);
	   double y = Double.parseDouble(command[index + 2]);
	   CoordinatesDelta coord= new CoordinatesDelta(x, y);
	   return coord;
   }
   
   private CoordinatesDelta calcOrigin(CoordinatesDelta start, CoordinatesDelta end, double height){
	   double startX = start.getX();
	   double startY = start.getY();
	   double endX = end.getX();
	   double endY = end.getY();
	   double midpointX = (startX + endX) / 2;
	   double midpointY = (startY + endY) / 2;
	   double distance = start.calculateDistance(end);
	   double originX = midpointX + (height * ((startY - endY) / distance));
	   double originY = midpointY + (height * ((endX - startX) / distance));
	   CoordinatesDelta origin = new CoordinatesDelta(originX, originY);
	   return origin;
   }
    
	/*
	 * further brakes down the create track and returns specified command, if it
	 * is a BRIDGE or SWITCH it must be broken down further
	 */

	private A_Command createTrackCommand(String command) {
		String[] commandArray = command.split("[*'\"\\s]+");
		A_Command commandType = null;
		String first = commandArray[0].toUpperCase();

		if (first.equals("BRIDGE") || first.equals("SWITCH") || first.equals("LAYOUT") || first.equals("ROUNDHOUSE")) {
			commandType = createTracksubCommand(command);
			return commandType;
		}

		String id = commandArray[1];
		int i = 0;
		CoordinatesWorld worldCoor = null;
		
		if(commandArray[3].charAt(0) != '$'){
			Latitude latitude = new Latitude(Integer.parseInt(commandArray[3]), Integer.parseInt(commandArray[4]), Double.parseDouble(commandArray[5]));
			Longitude longitude = new Longitude(Integer.parseInt(commandArray[7]), Integer.parseInt(commandArray[8]), Double.parseDouble(commandArray[9]));
			worldCoor = new CoordinatesWorld(latitude, longitude);
			i = 6;
		}	
		
		else{
			//worldCoor = this.parserHelper.getReference(commandArray[3]);
		}
		CoordinatesDelta deltaStart = processDelta(commandArray, 6 + i);
		CoordinatesDelta deltaEnd = processDelta(commandArray, 10 + i);
		PointLocator locator = new PointLocator(worldCoor, deltaStart, deltaEnd);

		switch (commandArray[0]) {

		case "CROSSING":
			commandType = new CommandCreateTrackCrossing(id, locator);
			break;

		case "CROSSOVER":
			CoordinatesDelta delta2Start = processDelta(commandArray, 14 + i);
			CoordinatesDelta delta2End = processDelta(commandArray, 18 + i);
			commandType = new CommandCreateTrackCrossover(id, worldCoor, deltaStart, deltaEnd, delta2Start, delta2End);
			break;

		case "CURVE":
			String origin = commandArray[13 + i].toUpperCase();
			if (origin.equals("ORIGIN")) {
				CoordinatesDelta deltaOrigin = processDelta(commandArray, 14 + i);
				commandType = new CommandCreateTrackCurve(id, worldCoor, deltaStart, deltaEnd, deltaOrigin);
			} else if (origin.equals("DISTANCE")) {
				double distance = Double.parseDouble(commandArray[15 + i]);
				commandType = new CommandCreateTrackCurve(id, worldCoor, deltaStart, deltaEnd, distance);
			}
			break;

		case "END":
			commandType = new CommandCreateTrackEnd(id, locator);
			break;

		case "STRAIGHT":
			commandType = new CommandCreateTrackStraight(id, locator);
			break;

		}
		return commandType;
	}

	private A_Command createTracksubCommand(String command) {
		String[] commandArray = command.split("\\s+");
		String subCommand;
		A_Command commandType = null;
		switch (commandArray[0]) {

		case "BRIDGE":
			subCommand = createSubCommand(commandArray);
			commandType = createBridgeCommand(subCommand);
			break;

		case "SWITCH":
			subCommand = createSubCommand(commandArray);
			commandType = createSwitchCommand(subCommand);
			break;

		case "LAYOUT":
			String id = commandArray[1];
			List<String> idList = new ArrayList<String>();
			for (int i = 4; i < commandArray.length; i++) {
				idList.add(commandArray[i]);
			}

			commandType = new CommandCreateTrackLayout(id, idList);
			break;

		case "ROUNDHOUSE":
			subCommand = createSubCommand(commandArray);
			commandType = createRoundhouseCommand(subCommand);
			break;

		}
		return commandType;
	}

	private A_Command createBridgeCommand(String command) {
		String[] commandArray = command.split("[*'\"\\s]+");
		A_Command commandType = null;
		String id;
		int index = 0;
		boolean isDraw = commandArray[0].toUpperCase().equals("DRAW");
		if (!isDraw) {
			index--;
		}
		
		id = commandArray[1 + index];
		CoordinatesWorld worldCoor = null;
		
		if(commandArray[3].charAt(0) != '$'){
			Latitude latitude = new Latitude(Integer.parseInt(commandArray[3]), Integer.parseInt(commandArray[4]), Double.parseDouble(commandArray[5]));
			Longitude longitude = new Longitude(Integer.parseInt(commandArray[7]), Integer.parseInt(commandArray[8]), Double.parseDouble(commandArray[9]));
			worldCoor = new CoordinatesWorld(latitude, longitude);
			index += 6;
		}
		
		else{
			//worldCoor = this.parserHelper.getReference(commandArray[3]);
		}
		
		CoordinatesDelta deltaStart = processDelta(commandArray, 6 + index);
		CoordinatesDelta deltaEnd = processDelta(commandArray, 10 + index);
		PointLocator pi = new PointLocator(worldCoor, deltaStart, deltaEnd);
		
		if(isDraw){
			Angle angle = new Angle(Double.parseDouble(commandArray[14 + index]));
			commandType = new CommandCreateTrackBridgeDraw(id, pi, angle);
		}
		
		else{
			commandType = new CommandCreateTrackBridgeFixed(id, pi);
		}
		return commandType;
	}

	private A_Command createSwitchCommand(String command) {
		String[] commandArray = command.split("[*'\"\\s]+");
		
		int index = 0;
		A_Command commandType = null;
		
		String id = commandArray[1];
		
		CoordinatesWorld  worldCoor = null;
		if(commandArray[3].charAt(0) != '$'){
			Latitude latitude = new Latitude(Integer.parseInt(commandArray[3]), Integer.parseInt(commandArray[4]), Double.parseDouble(commandArray[5]));
			Longitude longitude = new Longitude(Integer.parseInt(commandArray[7]), Integer.parseInt(commandArray[8]), Double.parseDouble(commandArray[9]));
			worldCoor = new CoordinatesWorld(latitude, longitude);
			index = 6;
		}	
		
		else{
			//worldCoor = this.parserHelper.getReference(commandArray[3]);
		}
		switch (commandArray[0]) {

		case "WYE":
			CoordinatesDelta wyeDeltaStart = processDelta(commandArray, 6 + index);
			CoordinatesDelta wyeDeltaEnd = processDelta(commandArray, 10 + index);
			double distance1 = Double.parseDouble(commandArray[15 + index]);
			CoordinatesDelta originWye = calcOrigin(wyeDeltaStart, wyeDeltaEnd, distance1);
			CoordinatesDelta wyeDeltaStart2 = processDelta(commandArray, 18 + index);
			CoordinatesDelta wyeDeltaEnd2 = processDelta(commandArray, 22 + index);
			double distance2 = Double.parseDouble(commandArray[15 + index]);
			CoordinatesDelta originWye2 = calcOrigin(wyeDeltaStart2, wyeDeltaEnd2, distance2);
			commandType = new CommandCreateTrackSwitchWye(id, worldCoor, wyeDeltaStart, wyeDeltaEnd, originWye, wyeDeltaStart2, wyeDeltaEnd2, originWye2);

		case "TURNOUT":
			CoordinatesDelta deltaStart = processDelta(commandArray, 7 + index);
			CoordinatesDelta deltaEnd = processDelta(commandArray, 11 + index);
			CoordinatesDelta curveStart = processDelta(commandArray, 17 + index);
			CoordinatesDelta curveEnd =  processDelta(commandArray, 21 + index);
			double distance = Double.parseDouble(commandArray[26 + index]);
			CoordinatesDelta originTurnout = calcOrigin(curveStart, curveEnd, distance);
			commandType = new CommandCreateTrackSwitchTurnout(id, worldCoor, deltaStart, deltaEnd, 
					  											curveStart, curveEnd, originTurnout);
			 
			break;
		}
		return commandType;
	}
	
	private A_Command createRoundhouseCommand(String command) {
		String[] commandArray = command.split("[*'\"\\s]+");
		A_Command commandType = null;
		int i = 0;
		CoordinatesWorld worldCoor = null;
		String id = commandArray[0];
		
		if(commandArray[2].charAt(0) != '$'){
			Latitude latitude = new Latitude(Integer.parseInt(commandArray[3]), Integer.parseInt(commandArray[4]), Double.parseDouble(commandArray[5]));
			Longitude longitude = new Longitude(Integer.parseInt(commandArray[7]), Integer.parseInt(commandArray[8]), Double.parseDouble(commandArray[9]));
			worldCoor = new CoordinatesWorld(latitude, longitude);
			i = 6;
		}
		
		else{
			//worldCoor = this.parserHelper.getReference(commandArray[3]);
		}
		
		CoordinatesDelta deltaOrigin = processDelta(commandArray, 5 + i);
		
		double aEntry = Double.parseDouble(commandArray[10 + i]);
		Angle angleEntry = new Angle(aEntry);
		double aStart = Double.parseDouble(commandArray[12 + i]);
		Angle angleStart = new Angle(aStart);
		double aEnd = Double.parseDouble(commandArray[14 + i]);
		Angle angleEnd = new Angle(aEnd);
		
		int spurCount = Integer.parseInt(commandArray[16 + i]);
		double spurLength = Double.parseDouble(commandArray[19 + i]);
		double turntableLength = Double.parseDouble(commandArray[22 + i]);
		
		commandType = new CommandCreateTrackRoundhouse(id, worldCoor, deltaOrigin, angleEntry, angleStart, 
													   angleEnd, spurCount,  spurLength,  turntableLength);
		
		return commandType;
	}

	// creates and returns CommandMetaDoMetaExit()
	private A_Command createExitCommand() {
		return new CommandMetaDoExit();
	}

	// returns a CommandMetaDoMetaRun command
	private A_Command createMetaRunCommand(String subCommand) {
		A_Command command = new CommandMetaDoRun(subCommand);
		return command;
	}

	// returns a CommandMetaDoMetaSchedule command
	private A_Command createMetaSchedule(A_Command command) {
		A_Command schedule = new CommandMetaDoSchedule(this.time, command);
		return schedule;
	}

	// returns a CommandMetaViewGenerate command
	private A_Command createMetaView(String subCommand) {
		String[] commandArray = subCommand.split("[*'\"\\s]+");
		String id = commandArray[1];
		int i = 0;
		int ix = 0;
		for(String s : commandArray){
			System.out.println(ix + ". " + s);
			ix++;
		}
		CoordinatesWorld origin = null;
		A_Command command = null;
		
		if (commandArray[3].charAt(0) != '$') {
			Latitude latitude = new Latitude(Integer.parseInt(commandArray[3]), Integer.parseInt(commandArray[4]), Double.parseDouble(commandArray[5]));
			Longitude longitude = new Longitude(Integer.parseInt(commandArray[7]), Integer.parseInt(commandArray[8]), Double.parseDouble(commandArray[9]));
			origin = new CoordinatesWorld(latitude, longitude);
			i = 6;
		}
		else{
			//origin = this.parserHelper.getReference(commandArray[3]);
		}
		
		int worldWidth = Integer.parseInt(commandArray[6 + i]);
		int screenHeight = Integer.parseInt(commandArray[9 + i]);
		int screenWidth = Integer.parseInt(commandArray[11 + i]);
		CoordinatesScreen screenSize = new CoordinatesScreen(screenHeight, screenWidth);
		command = new CommandMetaViewGenerate(id, origin, worldWidth, screenSize);

		return command;
	}

	// returns a CommandMetaViewDestroy command
	private A_Command createMetaViewDestroy(String subCommand) {
		String[] commandArray = subCommand.split("\\s+");
		A_Command commandType = new CommandMetaViewDestroy(commandArray[1]);
		return commandType;
	}

	
	private void setCoordinatesWorldReference(String subCommand) {
		String[] commandArray = subCommand.split("\\s+");
		String id = commandArray[0];
		Latitude latitude = new Latitude(Double.parseDouble(commandArray[3]));
		Longitude longitude = new Longitude(Double.parseDouble(commandArray[5]));
		CoordinatesWorld coordinates = new CoordinatesWorld(latitude, longitude);
		//this.parserHelper.addReference(id, coordinates);
		
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
   
// splits the numbers into a coordinateDelta and returns it
   private CoordinatesDelta getCoordDel(String subCommand)
   {
      String[] commandArray = subCommand.split("\\s+");
      subCommand = subCommand.toLowerCase();
      int deltaIndex = subCommand.indexOf("delta")+6;
      subCommand = subCommand.substring(deltaIndex, subCommand.length()-1);
      int colonIndex = subCommand.indexOf(':');
      String [] tempCommand = subCommand.substring(0, colonIndex).split("\\s+");
      int x = Integer.parseInt(tempCommand[0]);
      int withIndex = subCommand.indexOf("with")-1;
      tempCommand = subCommand.substring(colonIndex+1, withIndex).split("\\s+");
      int y;
      if(tempCommand[0].equals(""))
      {
         y = Integer.parseInt(tempCommand[1]);
      }
      else
      {
         y = Integer.parseInt(tempCommand[0]);
      }
      return new CoordinatesDelta(x, y); 
   }
   
   // splits on the diferent ways lon and lat can come in and returns a coordinatesWorld
   private CoordinatesWorld getCoordWorld(String subCommand)
   {
      String[] commandArray = subCommand.split("\\s+");
      for(int x = 0; x < 3; x++)
      {
         subCommand = createSubCommand(commandArray);
         commandArray = subCommand.split("\\s+");
      }
      int degIn = subCommand.indexOf('*');
      int minIn = subCommand.indexOf('\'');
      int secIn = subCommand.indexOf('\"');
      String [] tempCommand = subCommand.substring(0, degIn).split("\\s+");
      int degrees = Integer.parseInt(tempCommand[0]);
      tempCommand = subCommand.substring(degIn+1, minIn).split("\\s+");
      int minutes;
      if(tempCommand[0].equals(""))
      {
         minutes = Integer.parseInt(tempCommand[1]);
      }
      else
      {
         minutes = Integer.parseInt(tempCommand[0]);
      }
      tempCommand = subCommand.substring(minIn+1, secIn).split("\\s+");
      double seconds;
      if(tempCommand[0].equals(""))
      {
         seconds = Double.parseDouble(tempCommand[1]);
      }
      else
      {
         seconds = Double.parseDouble(tempCommand[0]);
      }
      Latitude lat = new Latitude(degrees, minutes, seconds);
      // create the longitude
      int lonIndex = subCommand.indexOf('/');
      String lonCoor = subCommand.substring(lonIndex+1, subCommand.length()-1);
      if(lonCoor.substring(0, 0).equals(""))
      {
         lonCoor = lonCoor.substring(1, lonCoor.length()-1);
      }
      degIn = lonCoor.indexOf('*');
      minIn = lonCoor.indexOf('\'');
      secIn = lonCoor.indexOf('\"');
      tempCommand = lonCoor.substring(0, degIn).split("\\s+");
      degrees = Integer.parseInt(tempCommand[0]);
      tempCommand = lonCoor.substring(degIn+1, minIn).split("\\s+");
      if(tempCommand[0].equals(""))
      {
         minutes = Integer.parseInt(tempCommand[1]);
      }
      else
      {
         minutes = Integer.parseInt(tempCommand[0]);
      }
      tempCommand = lonCoor.substring(minIn+1, secIn).split("\\s+");
      if(tempCommand[0].equals(""))
      {
         seconds = Double.parseDouble(tempCommand[1]);
      }
      else
      {
         seconds = Double.parseDouble(tempCommand[0]);
      }
      Longitude lon = new Longitude(degrees, minutes, seconds);
      return new CoordinatesWorld(lat, lon);
   
   }
}
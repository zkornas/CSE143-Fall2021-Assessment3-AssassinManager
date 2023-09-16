// Zacharia Kornas
// TA: Kent Zeng
// The AssassinManager class represents and manages a game of "Assassin"
// and can be played by the client.
import java.util.*;

public class AssassinManager {
   private AssassinNode killRingFront;
   private AssassinNode graveyardFront;
   
   // Constructs a new kill ring of the list of names provided
   // The kill ring will be in same order as the list provided
   // If the list provided is empty, AssassinManager will throw
   // an IllegalArgumentException
   // Parameters:
   //   names - list of names used to construct a kill ring
   public AssassinManager(List<String> names){
      if(names.size() == 0){
         throw new IllegalArgumentException();
      }
      AssassinNode player = new AssassinNode(names.get(names.size() - 1));
      for(int i = (names.size() - 2); i >= 0; i--){
         AssassinNode newPlayer = new AssassinNode (names.get(i), player);
         player = newPlayer;
      }
      killRingFront = player;
   }
   
   // Prints the names of everyone in the current Kill Ring
   // as well as who they are stalking.
   // If the game is over, it prints the winner's name is
   // stalking themself.
   public void printKillRing(){
      AssassinNode current = killRingFront;
      while(current.next != null){
         System.out.println("    " + current.name + " is stalking " + current.next.name);
         current = current.next;
      }
      System.out.println("    " + current.name + " is stalking " + killRingFront.name);
   }
   
   // Prints the names of everyone who was killed and is
   // currently in teh graveyard, as well as who they
   // were killed by. If the graveyard is empty and no one
   // has been killed, nothing will print.
   public void printGraveyard(){
      AssassinNode current = graveyardFront;
      while(current != null){
         System.out.println("    " + current.name + " was killed by " + current.killer);
         current = current.next;
      }
   }
   
   // Will print out true or false depending on if the name
   // provided is in the kill ring and currently still in the game
   // regardless of capitalization.
   // Parameters: 
   //    name - the name of the player the client wishes to search for
   public boolean killRingContains(String name){
      return contains(name, killRingFront);
   }
   
   // Will print out true or false depending on if the name
   // provided is in the graveyard and has been killed regardless
   // of capitalization
   // Parameters:
   //    name - the name of the player the client wishes to search for
   public boolean graveyardContains(String name){
      return contains(name, graveyardFront);
   }
   
   // Helper method to go through the graveyard or the kill ring and
   // see if the name provided is listed in them. Returns true or false
   // depending on if the name is found.
   // Parameters:
   //    name - the name of the player the client wishes to search for
   //    front - the first node in either the graveyard or kill ring
   private boolean contains(String name, AssassinNode front){
      AssassinNode current = front;
      while(current != null){
         if(name.equalsIgnoreCase(current.name)){
            return true;
         }
         current = current.next;
      }
      return false;
   }
   
   // Returns true if game is over and there is one person left in
   // the kill ring, will return false if there are multiple people
   // in the kill ring.
   public boolean gameOver(){
      return(killRingFront.next == null);
   }
   
   // Returns the winner of the game if the game is over or returns
   // null if the game is not over yet.
   public String winner(){
      if(killRingFront.next == null){
         return killRingFront.name;
      }
      return null;
   }
   
   // Records the assassination of the person passed in as String name
   // regardless of capitalization. Will remove the person from the 
   // Kill Ring and place them into the Graveyard. If the game is over,
   // will throw an IllegalStateException, if the name passed in is not
   // the name of someone in the Kill Ring, will throw an IllegalArgumentException.
   // Parameters:
   //    name - the name of the person who is being assassinated
   public void kill(String name){
      if(gameOver()){
         throw new IllegalStateException();
      } else if(!killRingContains(name)){
         throw new IllegalArgumentException();
      }
      AssassinNode current = killRingFront;
      while(!current.name.equalsIgnoreCase(name)){
         current = current.next;
      }
      AssassinNode killer = killRingFront;
      if(current == killRingFront){
         while(killer.next != null){
            killer = killer.next;
         }
         killRingFront = current.next;
      } else {
         while(killer.next != current){
            killer = killer.next;
         }
         killer.next = current.next;
      }
      current.killer = killer.name;
      current.next = graveyardFront;
      graveyardFront = current;
   }
}

// player1
// player2
package gui;

public class SettingsHvAI {
	static int time = 0;
  static String player1 = "";

  public static void changeTime(int newTime){
    time = newTime;
  }

  public static void changeType1(String type){
    player1 = type;
  }


  public static Boolean continuex(){
    if(time != 0 && player1 != ""){
      return true;
    } else {
      return false;
    }
  }
}

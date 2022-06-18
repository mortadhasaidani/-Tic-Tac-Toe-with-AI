package tictactoe;

import java.util.Scanner;
import java.util.ArrayList;

public class Main{
   public static char[][] arr = new char[3][3];

  public static void main(String[] args) {
      // write your code here
      String s;
      Scanner sc = new Scanner(System.in);
      intialize();
      menu();
  }
  
  public static int miniMax(int actualPlayer, int player, boolean isMax) {
      int x = check_win(player);
      if (x == 1 && actualPlayer == player) {
          return 10;
      }
      
       if (x == 1 && actualPlayer != player) {
           return -10;
       }
      
      if (check_empty() != 0) {
          return 0;
      }
      
      if (isMax) {
          int best = -1000;
          for (int i = 0; i < 3; i++) {
              for (int j = 0; j < 3; j++) {
                 if (arr[i][j] == ' ') {
                     if (player == 1) {
                         arr[i][j] = 'X';
                         best = Math.max(best, miniMax(actualPlayer, 2, false));
                     } else {
                         arr[i][j] = 'O';
                         best = Math.max(best, miniMax(actualPlayer, 1, false));
                     }
                     arr[i][j] = ' ';
                 } 
              }
          }
          return best;
      } else {
          int best = 1000;
          for (int i = 0; i < 3; i++) {
              for (int j = 0; j < 3; j++) {
                  if (arr[i][j] == ' ') {
                      if (player == 2) {
                          arr[i][j] = 'O';
                          best = Math.min(best, miniMax(actualPlayer, 1, true));
                      } else {
                          arr[i][j] = 'X';
                          best = Math.min(best, miniMax(actualPlayer, 2, true));
                      }
                      
                      arr[i][j] = ' ';
                  }
              }
          }
          return best;
      }
  }
  
  public static void findBestMove(int player) {
      int move = 0;
      int x = -1000;
      int row = -1;
      int column = -1;
      for (int i = 0; i < 3; i++) {
          for (int j = 0; j < 3; j++) {
              if (arr[i][j] == ' ') {
                  if (player == 1) {
                      arr[i][j] = 'X';
                      move = miniMax(player, 2, false);
                  } else {
                      arr[i][j] = 'O';
                      move = miniMax(player, 1, false);
                  }
                  arr[i][j] = ' ';
                  if (move > x) {
                      x = move;
                      row = i;
                      column = j;
                  }
              }
          }
      }
      if (player == 1) {
          arr[row][column] = 'X';
      } else {
          arr[row][column] = 'O';
      }
  }
  
  public static int check_player(String s) {
      int nb = 0;
      int nb1 = 0;
      for (int i = 0; i < s.length(); i++) {
          if (s.charAt(i) == 'X') {
              nb++;
          }
          if (s.charAt(i) == 'O') {
              nb1++;
          }
      }
      if(nb == nb1) {
          return 1;
      }
      return 2;
  }

  public static void intialize() {
      int row = 0;
      int col = 0;
      for (int i = 0; i < 3; i++) {
          for(int j = 0; j < 3; j++) {
              arr[i][j] = ' ';
          }
      }
  }

  public static ArrayList setRowColumn(int player) {
      ArrayList<Integer> arr1 = new  ArrayList<>();
      arr1 = checkColumn(player);
      if ((int) arr1.get(0) == 2) {
      	return arr1;
      }
      arr1 = chekcRow(player);
      if ((int) arr1.get(0) == 2) {
      	return arr1;
      }
      arr1 = checkDiag1(player);
      if ((int) arr1.get(0) == 2) {
          return arr1;
      }
      arr1 = checkDiag2(player);
      if ((int) arr1.get(0) == 2) {
          return arr1;
      }
      return null;
  }

  public static int playAI(int player) {
      ArrayList<Integer> arr1 = new ArrayList<>();
      if (player == 2) {
          arr1 = setRowColumn(2);
          if (arr1 != null) {
              arr[(int) arr1.get(1)][(int) arr1.get(2)] = 'O';
              return 1;
          } else {
              arr1 = setRowColumn(1);
              if (arr1 != null) {
                  arr[(int) arr1.get(1)][(int) arr1.get(2)] = 'O';
                  return 1;
              } else {
                  return 0;
              }
          }
      } else {
          arr1 = setRowColumn(1);
          if (arr1 != null) {
              arr[(int) arr1.get(1)][(int) arr1.get(2)] = 'X';
              return 1;
          } else {
              arr1 = setRowColumn(2);
              if (arr1 != null) {
                  arr[(int) arr1.get(1)][(int) arr1.get(2)] = 'X';
                  return 1;
              } else {
                  return 0;
              }
          }  
      }
      
  }

  public static void player_vs_AI(int player, String name) {
      int row = 1;
      int col = 1;
      int ok = 0;
      Scanner sc = new Scanner(System.in);
      if(!name.equals("user")) {
          System.out.print("Making move level \"");
          System.out.println(name +"\"");
      }
      if (name.equals("medium")) {
          ok = playAI(player);
      }
      if (name.equals("hard")) {
          findBestMove(player);
          return;
      }
      if (!name.equals("user") && ok == 0) {
          while(true) {
              row = (int) ((Math.random() * 3) + 1);
              col = (int) ((Math.random() * 3) + 1);
              if(arr[row - 1][col - 1] == ' ') {
                  if (player == 2) {
                      arr[row - 1][col - 1] = 'O';
                  } else {
                      arr[row - 1][col - 1] = 'X';
                  }
                  break;
              }
          }
          return;
      }
      while(ok == 0) {
          System.out.print("Enter the coordinates: ");
          String[] s = sc.nextLine().split(" ");
          if (verif_number(s) == 0) {
              System.out.println("You should enter numbers!");
              continue;
          }
          row = Integer.parseInt(s[0]);
          col = Integer.parseInt(s[1]);
          if (row < 1 || row > 3 || col < 1 || col > 3) {
              System.out.println("Coordinates should be from 1 to 3!");
          } else if (arr[row - 1][col - 1] != ' ') {
              System.out.println("This cell is occupied! Choose another one!");
          } else {
              if (player == 1) {
                  arr[row - 1][col - 1] = 'X';
              } else {
                  arr[row - 1][col - 1] = 'O';
              }
              ok = 1;
          }
      }
  }
  public static int verif_number(String[] s) {
      for (String s1 : s) {
          for(int i = 0; i < s1.length(); i++) {
              if (s1.charAt(i) == ' ') {
                  continue;
              }
              if (s1.charAt(i) < '0' || s1.charAt(i) > '9') {
                  return 0;
              }
          }
      }
      return 1;
  }

  public static void display() {
      System.out.println("---------");
      for (int i = 0; i < 3; i++) {
          System.out.print("| ");
          for (int j = 0; j < 3; j++) {
              System.out.print(arr[i][j] + " ");
          }
          System.out.println("|");
      }
      System.out.println("---------");
  }

  public static ArrayList chekcRow(int player) {
      int nbX = 0;
      int indexColumn = -1;
      ArrayList<Integer> arr1 = new ArrayList<>();
      for (int i = 0; i < 3; i++) {
          indexColumn = -1;
          for (int j = 0; j < 3; j++) {
              if (player == 1 && arr[i][j] == 'X') {
                  nbX++;
              } else if (player == 2 && arr[i][j] == 'O') {
                  nbX++;
              }
              if (arr[i][j] == ' ') {
                  indexColumn = j;
              }
              if ((nbX == 2 && indexColumn != -1) || (indexColumn == -1 && nbX == 3)) {
                  arr1.clear();
                  arr1.add(nbX);
                  arr1.add(i);
                  arr1.add(indexColumn);
              }
          }
          nbX = 0;
      }
      if(!arr1.isEmpty()) {
          return arr1;
      }
      arr1.add(0);
      arr1.add(-1);
      arr1.add(-1);
      return arr1;
  }

  public static ArrayList checkColumn(int player) {
      int nbX = 0;
      int index = -1;
      int index1= -1;
      ArrayList arr1 = new ArrayList<Integer>();
      for (int i = 0; i < 3; i++) {
          index = -1;
          index1 = -1;
          for (int j = 0; j < 3; j++) {
              if (player == 1) {
                  if (arr[j][i] == 'X') {
                      nbX++;
                  }
              }
              
              if (player == 2) {
                  if (arr[j][i] == 'O') {
                      nbX++;
                  }
              }
              if (arr[j][i] == ' ') {
                  index = i;
                  index1 = j;
              }
              if ((nbX == 2 && index != -1) || (index == -1 && nbX == 3)) {
                  arr1.clear();
                  arr1.add(nbX);
                  arr1.add(index1);
                  arr1.add(index);
              }
          }
          nbX = 0;
      }
      if (!arr1.isEmpty()) {
          return arr1;
      }
      arr1.add(0);
      arr1.add(-1);
      arr1.add(-1);
      return arr1;
  }

  public static ArrayList checkDiag1(int player) {
      int nbX = 0;
      int index = -1;
      ArrayList<Integer>arr1 = new ArrayList<>();
      for (int i = 0; i < 3; i++) {
          if (player == 1) {
              if (arr[i][i] == 'X') {
                  nbX++;
              }
          }
          if (player == 2) {
              if (arr[i][i] == 'O') {
                  nbX++;
              }
          }
          
          if (arr[i][i] == ' ') {
              index = i;
          }
          
          if ((nbX == 2 && index != -1) || (index == -1 && nbX == 3)) {
              arr1.clear();
              arr1.add(nbX);
              arr1.add(index);
              arr1.add(index);
          }
      }
      if (!arr1.isEmpty()) {
          return arr1;
      }
      arr1.add(0);
      arr1.add(-1);
      arr1.add(-1);
      return arr1;
  }

  public static ArrayList checkDiag2(int player) {
      int l = 3;
      int nbX = 0;
      int index = -1;
      int index1 = -1;
      ArrayList<Integer>arr1 = new ArrayList<>();
      for (int i = 0; i < 3; i++) {
          if (player == 1) {
              if (arr[i][l - 1 - i] == 'X') {
                  nbX++;
              }
          }
          if (player == 2) {
              if (arr[i][l - 1 - i] == 'O') {
                  nbX++;
              }
          }
          if (arr[i][l - 1 - i] == ' ') {
              index = l - 1 - i;
              index1 = i;
          }
          if ((nbX == 2 && index != -1) || (index == -1 && nbX == 3)) {
              arr1.clear();
              arr1.add(nbX);
              arr1.add(index1);
              arr1.add(index);
          }
      }
      if (!arr1.isEmpty()) {
          return arr1;
      }
      arr1.add(0);
      arr1.add(-1);
      arr1.add(-1);
      return arr1;
  }

  public static int check_empty() {
      for (int i = 0; i < 3; i++) {
          for (int j = 0; j < 3; j++) {
              if (arr[i][j] == ' ') {
                  return 0;
              }
          }
      }
      return 4;
  }

  public static int check_win(int player) {
      boolean x = (int) checkColumn(player).get(0) == 3 || (int) chekcRow(player).get(0) == 3 || (int) checkDiag1(player).get(0) == 3 || (int) checkDiag2(player).get(0) == 3;
      int d = check_empty();
      if (x) {
          return 1;
      }
      return d;
  }
  
  public static void play_v2(String player1, String player2) {
      int player = 1;
      display();
      while(true) {
          if (player == 1) {
              player_vs_AI(1, player1);
          } else {
              player_vs_AI(2, player2);
          }
          display();
          int win = check_win(player);
          if (win == 1) {
              if (player == 1) {
                  System.out.println("X wins");
              }
              if (player == 2) {
                  System.out.println("O wins");
              }
              break;
          } else if (win != 0) {
              System.out.println("Draw");
              break;
          }
          
          if (player == 1) {
              player = 2;
          } else {
              player = 1;
          }
      }
  }
  
  public static int check_parameter(String arg) {
      if (!arg.equals("medium") && !arg.equals("easy") && !arg.equals("user") && !arg.equals("hard")) {
          return 0;
      }
      return 1;
  }
  
  public static void menu() {
      /*
          mode = 
              user vs user => 1
              user vs easy => 2
              easy vs easy => 3
              easy vs medium => 4
              medium vs user => 5
              
          
      */
       Scanner sc = new Scanner(System.in);
       String[] arr;
       while(true) {
           intialize();
           System.out.print("Input command: ");
           arr = sc.nextLine().split(" ");
           if (arr.length == 1 && arr[0].equals("exit")) {
               break;
           }
           if (arr.length == 3 && arr[0].equals("start")) {
               if (check_parameter(arr[1]) == 0 || check_parameter(arr[2]) == 0) {
                   System.out.println("Bad parameters!");
               } else {
                   play_v2(arr[1], arr[2]);
               }
           } else {
               System.out.println("Bad parameters!");
           }
       }
  }
}

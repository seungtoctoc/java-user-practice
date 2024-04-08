package day04;

import java.util.HashMap;
import java.util.Scanner;

public class EX01 {


  public static void main(String[] args) {
    View View = new View();
    DAO DAO = new DAO();

    int menu = 0;
    boolean isLoggedIn = false;
    String currentId = "";

    while (true) {
      System.out.println();

      if (isLoggedIn) {
        View.printCurrentUser(currentId);
      }

      View.printMenu();
      menu = View.getMenuInput();

      if (menu == 0) {
        System.out.println("\n* 종료");
        break;
      }

      if (menu == 1) {
        if (isLoggedIn) {
          System.out.println("이미 로그인 되어있음");
          continue;
        }

        System.out.println("\n* 회원가입");
        String[] IdAndPassword = View.getIdAndPassword();
        DAO.createUser(IdAndPassword[0], IdAndPassword[1]);

        System.out.println("\n* 회원가입 완료.");
        continue;
      }

      if (menu == 2) {
        if (isLoggedIn) {
          System.out.println("이미 로그인 되어있음");
          continue;
        }

        System.out.println("\n* 로그인");
        String inputId = View.getId();

        if (!DAO.isUser(inputId)) {
          System.out.println("아이디 틀");
          continue;
        }

        String inputPassword = View.getPassword();
        if (DAO.isCorrectPassword(inputId, inputPassword)) {
          System.out.println("로그인 완");
          isLoggedIn = true;
          currentId = inputId;
          continue;
        }

        System.out.println("비번 틀");
        continue;
      }

      if (menu == 3) {

        if (!isLoggedIn) {
          System.out.println("로그인부터 하세요");
          continue;
        }

        System.out.println("\n* 회원정보 수정");
        String newPassword = View.getNewPassword();
        DAO.modifyUser(currentId, newPassword);

        System.out.println("수정 완~");
        continue;
      }

      if (menu == 4) {
        if (!isLoggedIn) {
          System.out.println("로그인부터 하세요");
          continue;
        }

        System.out.println("\n* 회원 탈퇴");

        String password = View.getPassword();
        if (!DAO.isCorrectPassword(currentId, password)) {
          System.out.println("비번 틀");
          continue;
        }

        DAO.deleteUser(currentId);
        currentId = "";
        isLoggedIn = false;

        System.out.println("삭제 완~");
        continue;
      }

      if (menu == 5) {
        if (!isLoggedIn) {
          System.out.println("로그인부터 하세요");
          continue;
        }

        System.out.println("\n* 로그아웃");

        currentId = "";
        isLoggedIn = false;

        continue;
      }

      System.out.println("다시 입력");
    }
  }
}

class View {

  static Scanner input = new Scanner(System.in);

  void printMenu() {
    System.out.println("--------------------------MENU--------------------------");
    System.out.println("1-회원가입, 2-로그인, 3-회원정보 수정, 4-회원탈퇴, 5-로그아웃, 0-종료");
    System.out.println("--------------------------------------------------------");
  }

  void printCurrentUser(String currentId) {
    System.out.printf("현재 로그인중인 아이디: %s\n", currentId);
  }

  int getMenuInput() {
    System.out.println("메뉴를 입력해주세요.");

    String menuInput = input.nextLine();

    return Integer.parseInt(menuInput);
  }

  String[] getIdAndPassword() {
    System.out.println("아이디를 입력해주세요.");
    String id = input.nextLine();

    System.out.println("비밀번호를 입력해주세요.");
    String password = input.nextLine();

    return new String[]{id, password};
  }

  String getPassword() {
    System.out.println("비밀번호를 입력해주세요.");
    return input.nextLine();
  }

  String getNewPassword() {
    System.out.println("새로운 비밀번호를 입력해주세요.");
    return input.nextLine();
  }

  String getId() {
    System.out.println("아이디를 입력해주세요.");
    return input.nextLine();
  }
}


class DAO {

  HashMap<String, String> user = new HashMap<String, String>();

  void createUser(String id, String password) {
    user.put(id, password);
  }

  boolean isUser(String id) {
    return user.containsKey(id);
  }

  boolean isCorrectPassword(String id, String password) {
    return user.get(id).equals(password);
  }

  void modifyUser(String id, String password) {
    user.replace(id, password);
  }

  void deleteUser(String id) {
    user.remove(id);
  }

}


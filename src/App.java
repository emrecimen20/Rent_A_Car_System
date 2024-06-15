import business.UserManager;
import core.Helper;
import view.AdminView;
import view.LoginView;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        Helper.setTheme();
        UserManager userManager =new UserManager();
        LoginView loginView =new LoginView();
        //AdminView adminView = new AdminView(userManager.finfByLogin("admin", "1234"));


    }
}
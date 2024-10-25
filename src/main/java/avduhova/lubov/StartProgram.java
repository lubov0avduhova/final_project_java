package avduhova.lubov;

import avduhova.lubov.logic.ClickLink;
import avduhova.lubov.logic.Generator;
import avduhova.lubov.model.Url;
import avduhova.lubov.model.User;
import avduhova.lubov.model.Users;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import static avduhova.lubov.logic.Generator.user;

public class StartProgram {
    Scanner scanner = new Scanner(System.in);
    private final Users users = new Users();
    Generator generator = new Generator();
    ClickLink clickLink = new ClickLink();

    public void startProgram() {
        while (true) {

            boolean exit = true;
            while (exit) {

                //проверка на истечения времени жизни ссылки
                for (User user : users.getUsers()) {
                    Iterator<Map.Entry<String, Url>> iterator = user.getUrl().entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, Url> entry = iterator.next();
                        if ((entry.getValue().getExpirationDate().getTime() - new Date().getTime()) <= 0) {
                            System.out.printf("Ссылка истекла: %s\n", entry.getKey());
                            iterator.remove();
                            System.out.println("Ссылка удалена");
                        }

                        if (entry.getValue().getReferralLimit() <= 0) {
                            entry.getValue().setBlock(true);
                            System.out.printf("Лимит максимального количества переходов по ссылке: %s исчерпан\n", entry.getKey());

                        }
                    }
                }


                System.out.println("Введи цифру из меню: ");

                System.out.println("""
                        1 - хочу ввести ссылку;
                        2 - перейти по ссылке;
                        3 - удалить все ссылки;
                        4 - редактировать ссылку;
                        5 - удалить ссылку;
                        6 - выйти.
                        """);


                int input = scanner.nextInt();
                switch (input) {
                    case 1 -> {
                        System.out.println("Ты в разделе ввода ссылки.\n Введи ссылку: ");
                        scanner = new Scanner(System.in);
                        String longUrl = scanner.nextLine();
                        generator.generateShortUrl(users, longUrl);
                    }

                    case 2 -> {

                        clickLink.clickLink(user);
                    }
                    case 6 -> exit = false;
                }

            }
            System.out.println("Ты вышел из системы!");
        }
    }
}

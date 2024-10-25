package avduhova.lubov.logic;

import avduhova.lubov.model.Url;
import avduhova.lubov.model.User;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;


public class ClickLink {

    public void clickLink(User user) {
        try {
            String longUrl = showAllUsersLinks(user);
            Desktop.getDesktop().browse(new URI(longUrl));
        } catch (RuntimeException | IOException | URISyntaxException e) {
            System.out.println(e.getMessage());
        }
    }


    private String showAllUsersLinks(User user) {
        AtomicInteger i = new AtomicInteger();
        user.getUrl().forEach((key, value) -> System.out.printf("%s: Ключ %s и значение %s \n", i.getAndIncrement(), key, value.getLongUrl()));

        int inputInt = chooseLink();

        List<Url> urls = user.getUrl().values().stream()
                .toList();

        Url url = urls.get(inputInt);

        if (url.isBlock()) {
            throw new RuntimeException("Ссылка недоступна");
        }
        url.setReferralLimit(url.getReferralLimit() - 1);

        return url.getLongUrl();

    }

    private int chooseLink() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите число: ");
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.println("Это не число!");
        }
        return scanner.nextInt();
    }
}

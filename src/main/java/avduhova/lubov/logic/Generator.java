package avduhova.lubov.logic;

import avduhova.lubov.model.Url;
import avduhova.lubov.model.User;
import avduhova.lubov.model.Users;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class Generator {

    //private final char[] allSymbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    private final char[] allSymbols = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2',
            '3', '4', '5', '6', '7', '8', '9'
    };

    //private final Random random = new Random();
    Random random = new Random();

    //убрать
    public static User user;

    /**
     * Метод принимает длинный URL и пользователя и кладет в его мапу
     *
     * @param longUrl
     */
    public String generateShortUrl(Users users, String longUrl) {
        String userUUID = "";
        if (longUrl.isEmpty()) {
            System.out.println("Нет ссылки для обработки");
        } else {
            if (user == null || user.getUuid() == null) {
                userUUID = generateUUIDForUser(users);
                user.setUuid(userUUID);
            } else {
                userUUID = user.getUuid();
            }

            String shortUrl = String.valueOf(generateRandomUrl());

            for (User userInList : users.getUsers()) {

                if (userInList.getUuid().equals(userUUID)) {
                    if (userInList.getUrl().containsKey(shortUrl)) {
                        System.out.println("Такая ссылка уже существует");
                    } else {
                        createUrl(userInList, longUrl, shortUrl);
                    }
                }
            }
        }
        return userUUID;
    }

    private void createUrl(User user, String longUrl, String newUrl) {
        String shortUrl = "clck.ru/" + newUrl;

        System.out.println("""
                Хотите установить максимальное количество переходов по ссылке?
                Если да - введи да""");

        Scanner scanner = new Scanner(System.in);

        if (scanner.next().equalsIgnoreCase("да")) {
            System.out.println("Введите число переходов: ");

            while (!scanner.hasNextInt()) {
                scanner.next();
                System.out.println("Это не число!");
            }

            int referralLimit = scanner.nextInt();
            user.getUrl().put(shortUrl, new Url(longUrl, referralLimit));

        } else {
            user.getUrl().put(shortUrl, new Url(longUrl));
        }

    }

    /**
     * Каждый пользователь идентифицируется без авторизации с помощью UUID,
     * который генерируется при первом запросе на создание короткой ссылки.
     *
     * @return
     */
    private String generateUUIDForUser(Users users) {
        String uuid = UUID.randomUUID().toString();
        boolean uuidExists = false;
        for (User user : users.getUsers()) {
            if (user.getUuid().equals(uuid)) {
                uuidExists = true;
                break;
            }
        }

        if (!uuidExists) {
            user = new User(uuid, new HashMap<>());
            users.getUsers().add(user);
        }

        return uuid;
    }

    /**
     * Метод генерирует рандомную ссылку
     */
    private char[] generateRandomUrl() {
        int sizeUrl = 6;
        char[] newUrl = new char[sizeUrl];

        for (int i = 0; i < sizeUrl; i++) {
            int randomIndex = random.nextInt(allSymbols.length);

            newUrl[i] = allSymbols[randomIndex];

        }
        return newUrl;
    }

}

package controllers;


import models.User;

/** Класс, наследующий класс Security из модуля Secure:
 * @see Security
 * Нужен для работы с авторизацией пользователя
 */
public class Security extends Secure.Security {

    /** Метод определяет, как будет авторизован пользователь
     * @param username - username пользователя
     * @param password - пароль пользователя
     * @return - true, пользователь считается авторизованным,
     * если в таблице:
     * @see User есть такой пользователь, иначе false - пользователь
     * считается не авторизованным
     */
    static boolean authenticate(String username, String password) {
        return User.connect(username, password) != null;
    }

    /** Метод определяет, на какую страницу буде осуществлён
     * переход после выхода пользователя
     */
    static void onDisconnected() {
        flash.success("Вы успешно вышли");
        Application.index();
    }

    /** Метод определяет, разрешено ли пользователю просмтривать
     * определённую страницу в view.
     * @param profile
     * @return - true, если пользователь соответствует admin'у, иначе false
     */
    static boolean check(String profile) {
        if ("admin".equals(profile)) {
            return User.find("byUsername", connected()).<User>first().isAdmin;
        }
        return false;
    }
}

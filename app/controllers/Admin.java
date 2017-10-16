package controllers;


import models.Book;
import models.User;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

import java.util.List;

/** Класс для событий с авторизованным пользователем */
@With(Secure.class)
public class Admin extends Controller {

    /** Метод проверяет наличие username пользователя в сессии и
     * сохраняет его в HashMap, для работы с ним во время фазы рендеринга
     */
    @Before
    static void setConnectedUser() {
        if (Secure.Security.isConnected()) {
            User user = User.find("byUsername", Secure.Security.connected()).first();
            renderArgs.put("username", user.username);
        }
    }

    /** Метод для отображения страницы авторизованного пользователя */
    public static void index() {
        List<Book> bookses = Book.find("order by name").fetch();
        render(bookses);
    }
}

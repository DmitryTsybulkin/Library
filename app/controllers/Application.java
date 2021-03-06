package controllers;

import models.Book;
import models.Status;
import models.User;
import play.data.validation.*;
import play.mvc.Controller;

import java.util.List;

/** <h1>Главный класс с контроллерами</h1> */
public class Application extends Controller {

    /** <h1>Метод для отображения страницы с таблицей книг, для не авторизованного пользователя</h1> */
    public static void index() {
        List<Book> bookses = Book.find("order by name").fetch();
        render(bookses);
    }

    /** <h1>Метод для отображения страницы регистрации</h1> */
    public static void registration() {
        render();
    }

    /**
     * </h1>Метод для регистрации нового пользователя и добавления его в БД
     * с использованием валидации введёных им данных <h1>
     * После проверки введённых пользователем данных, пользователь либо
     * остаётся на странице регистрации для исправления ошибок, либо
     * переходит на страницу авторизованного пользователя:
     * @see Admin
     * Получаемые параметры:
     * @param username - юзернейм или логин пользователя
     * @param password - пароль
     * @param password2 - проверка пароля введённого раннее
     * @param fullname - реальное имя пользователя
     */
    public static void saveUser(
            @Required(message = "Обязательное поле")
            @MinSize(value = 4, message = "Слишком короткий логин")
                String username,
            @Required(message = "Обязательное поле")
            @MinSize(value = 5, message = "Минимум 5 символов")
            @MaxSize(value = 20, message = "Пароль должен быть не более 20 знаков")
            @Match(value="^\\w*$", message="Неправильный пароль")
                String password,
            @Required(message = "Обязательное поле")
            @Equals(value = "password", message = "Пароли не совпадают")
                String password2,
            @Required(message = "Обязательное поле")
            @MinSize(value = 4, message = "Слишком короткое имя пользователя")
                String fullname
    ) {
        if (Validation.hasErrors()) {
            flash.keep("url");
            Validation.keep();
            params.flash();
            flash.error("Исправте ошибки");
            registration();
        }
        User user = new User(username, password, fullname);
        session.put("username", user.username);
        flash.success("Добро пожаловать, " + user.username + " вы успешно зарегистрировались!");
        Admin.index();
    }

    /**
     * <h1>Метод для начала чтения книги пользователем</h1>
     * В БД, для таблицы книг и статуса чтения делаются запросы:
     * для книги - это то, есть ли в списке читатателей авторизованный
     * пользователь, и для статуса - если пользователь не читал эту книгу,
     * то создаётся новый кортеж в таблице:
     * @see Status
     * А также, в список читателей определённой книги в таблице Book добавляется
     * fullname или имя пользователя, начавшего читать
     * Получаемый параметр:
     * @param name - название книги, которую пользователь будет читать
     */
    public static void StartRead(String name) {
        String user = session.get("username");
        User us = User.find("byUsername", user).first();
        Book book = Book.find("byName", name).first();
        Status stat = Status.find("byNickAndTitle", us.username, book.name).first();
        if (book.readers.contains(us)) {
            flash.success("вы уже читали эту книгу");
        } else {
            book.readers.add(us);
            book.save();
        }
        if (stat == null) {
            Status status = new Status(user, name);
        } else {
            flash.success("вы уже читаете эту книгу!");
        }
        Admin.index();
    }

    /**
     * <h1>Метод для завершения чтения книги</h1>
     * После того, как авторизованный пользователь нажимает на странице
     * кнопку "Закончить", в БД, в таблицу Status делается запрос поиска
     * кортежа по  username'у пользователя и названию книги, если он есть
     * то удаляется, иначе:
     * @exception NullPointerException - если такого статуса/кортежа нет.
     * Получаемый параметр:
     * @param name - название книги
     */
    public static void StopRead(String name) {
        String user = session.get("username");
        Status st = Status.find("byNickAndTitle", user, name).first();
        try {
            st.delete();
            flash.success("Вы успешно сдали книгу!");
        } catch (NullPointerException e) {
            flash.error("Вы не можете закончить читать кинигу, не начав!");
        }
        Admin.index();
    }

}
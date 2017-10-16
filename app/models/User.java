package models;

import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.Table;

/** Created by Dmitry on 03.09.2017. */

/** Класс определяет таблицу пользователей в БД */
@Entity
@Table(name = "Users")
public class User extends Model {

    /** username пользователя, должен быть не менее 4 символов */
    @Required(message = "Обязательное поле*")
    @MinSize(value = 4, message = "Слишком короткий логин")
    public String username;

    /** пароль пользователя */
    @Required(message = "Обязательное поле*")
    @MaxSize(value = 20, message = "Пароль должен быть не более 20 знаков")
    @MinSize(value = 5, message = "Слишком короткий пароль")
    //@Match(value="^\\w*$", message="Неправильный пароль")
    public String password;

    /** Реальное имя пользователя */
    @Required(message = "Обязательное поле*")
    @MinSize(value = 4, message = "Слишком короткое имя пользователя")
    public String fullname;

    /** Является пользователь администратором или нет */
    public boolean isAdmin;

    /** Инициализируем столбцы и сохраняем */
    public User(String username, String password, String fullname) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        save();
    }

    /** Метод производит поиск по таблице пользовтелей и
     * возвращает результат в метод authenticate:
     * @see controllers.Security
     * @param username - username пользователя
     * @param password - пароль
     * @return возвращает объект, содержащий данные о пользователе
     */
    public static User connect(String username, String password) {
        return find("byUsernameAndPassword", username, password).first();
    }

    /** @return строчное значение столбца username
     */
    public String toString() {
        return username;
    }
}

package models;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

/** Created by Dmitry on 03.09.2017. */

/**
 * <h1>Класс представляет из себя таблицу книг</h1>
 * <p>С помощью ORM: JPA этот класс можно связать с БД - H2.</p>
 */
@Entity
public class Book extends Model{

    /** Название книги */
    @Required(message = "Обязательное поле*")
    public String name;

    /** Имя автора */
    @Required(message = "Обязательное поле*")
    public String author;

    /** Жанр книги */
    @Required(message = "Обязательное поле*")
    public String genre;

    /** Список читателей определённой книги */
    @ManyToMany
    public List<User> readers;

    /** Инициализируем столбцы и сохраняем */
    public Book(String name, String author, String genre, List<User> readers) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.readers = readers;
        save();
    }

    public Book(){}

    /** Метод возвращает строчное название столбца */
    public String toString() {
        return name;
    }
}

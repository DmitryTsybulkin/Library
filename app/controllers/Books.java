package controllers;


import play.mvc.With;


/** Класс наследует CRUD и с помощью аннотаций
 *  Check и With(используя класс Secure) проверяет:
 *  является ли пользователь администратором и даёт доступ
 *  к таблицам базы данных
 */
@Check("admin")
@With(Secure.class)
public class Books extends CRUD {
}

import models.User;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

/** Created by Dmitry on 03.09.2017. */


/** <h1>Класс для выполнения задач перед запуском приложения</h1> */

@OnApplicationStart
public class Bootstrap extends Job {

    /** Метод проверяет, если таблица с пользователями пуста, то загружает
     * тестовую информацию в БД из файла initial-data.yml */

    public void doJob() {
        if (User.count() == 0) {
            Fixtures.loadModels("initial-data.yml");
        }
    }
}

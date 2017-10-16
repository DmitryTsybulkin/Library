import models.Book;
import models.Status;
import models.User;
import org.junit.Before;
import org.junit.Test;
import play.test.Fixtures;
import play.test.UnitTest;

/** Базовые Unit тесты */
public class BasicTestForUsers extends UnitTest {

    @Test
    public void aVeryImportantThingToTest() {
        assertEquals(2, 1 + 1);
    }

    /** Предварительное удаление БД */
    @Before
    public void setup() {
        Fixtures.deleteDatabase();
    }

    /** Проверка загрузки тестовой информации в БД */
    @Test
    public void BeforeJobs() {
        assertTrue(User.count() == 0);
        assertTrue(Book.count() == 0);
        assertTrue(Status.count() == 0);
        Fixtures.loadModels("data.yml");
        assertTrue(User.count() != 0);
        assertTrue(Book.count() != 0);
    }

    /** Создание нового пользователя */
    @Test
    public void newUser() {
        new User("demo", "demo", "demo").save();
        User demo = User.find("byUsername", "demo").first();
        assertNotNull(demo);
        assertEquals("demo", demo.fullname);
        assertEquals("demo", demo.username);
        assertEquals("demo", demo.password);
    }

    /** Попытка зайти, как зарегестрированный пользователь */
    @Test
    public void tryConnectAsUser() {
        new User("demo", "demo", "demo").save();
        assertNotNull(User.connect("demo", "demo"));
        assertNull(User.connect("demo", "badpassword"));
        assertNull(User.connect("dementor", "demo"));
    }

    /** Проверка и вход как администратор */
    @Test
    public void IsAdministration() {
        new User("admin", "admin", "admin").save();
        User user = User.find("byUsername", "admin").first();
        assertNotNull(User.connect("admin", "admin"));
        assertNull(User.connect("admin", "demo"));
        assertNull(User.connect("demo", "admin"));
        assertTrue("admin", true);
        assertFalse("demo", user.isAdmin);

    }

    /** Создание новой книги */
    @Test
    public void newBook() {
        new User("user", "user", "user").save();
        User user = User.find("byUsername", "user").first();
        assertNotNull(user);
        assertEquals("user", user.username);

        Book books = new Book();
        new Book("Best Book", "SuperAuthor", "Interesting", books.readers);
        Book book = Book.find("byName", "Best Book").first();
        assertNotNull(book);
        assertEquals(book.name, "Best Book");
        assertEquals(book.author, "SuperAuthor");
        assertEquals(book.genre, "Interesting");
        assertNull(book.readers);
    }

}

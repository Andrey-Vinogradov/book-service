import java.util.*;

public class BookService {
    private final Map<Integer, Book> booksById = new HashMap<>();//ключ
    private final Map<String, List<Book>> booksByName = new HashMap<>();
    private final Map<String, List<Book>> booksByAuthor = new HashMap<>();
    private final Map<String, List<Book>> booksByGenre = new HashMap<>();
    private final Queue<Integer> availableIds = new LinkedList<>(); // Свободные ID
    private int nextId = 1; // Счётчик новых ID

    // Логирование
    private void log(String message) {
        System.out.println("[LOG] " + message);
    }
    // Автогенерация ID с заполнением пропусков
    private int generateId() {
        // Ищем первый доступный ID, начиная с nextId
        while (booksById.containsKey(nextId)) {
            nextId++;
        }
        return nextId++;
    }


    // Добавление книги (если ID указано ноль —, то ID подставится автоматически)
    public void addBook(Book book) {
        if (book == null) throw new IllegalArgumentException("Book cannot be null");

        // Определяем ID для книги
        int id = (book.getId() == 0) ? generateId() : book.getId();
        Book bookToAdd = (book.getId() == 0)
                ? new Book(id, book.getName(), book.getAuthor(), book.getGenre())
                : book;

        if (booksById.containsKey(id)) {
            throw new IllegalStateException("Book with ID " + id + " already exists");
        }

        // Добавляем книгу во все коллекции
        booksById.put(id, bookToAdd);
        booksByName.computeIfAbsent(bookToAdd.getName(), k -> new ArrayList<>()).add(bookToAdd);
        booksByAuthor.computeIfAbsent(bookToAdd.getAuthor(), k -> new ArrayList<>()).add(bookToAdd);
        booksByGenre.computeIfAbsent(bookToAdd.getGenre(), k -> new ArrayList<>()).add(bookToAdd);

        log("Added book: " + bookToAdd);
    }



    // Удаление книги (ID освобождается для повторного использования)
    public void deleteBook(int id) {
        Book book = booksById.remove(id);
        if (book == null) {
            throw new NoSuchElementException("Book with ID " + id + " not found");
        }
        booksByName.get(book.getName()).remove(book);
        availableIds.add(id);  // Возвращаем ID в пул свободных
        log("Deleted book: " + book);
    }


    //Поиск книги по ID (O(1))
    public Book findById(int id) {
        Book book = booksById.get(id);
        if (book == null) {
            log("Book with ID " + id + " not found");
        } else {
            log("Found book by ID: " + id);
        }
        return book;
    }

    //Поиск книги по названию (O(1))
    public List <Book> findByName(String name) {
        List<Book> books = booksByName.getOrDefault(name, Collections.emptyList());
        if (books == null) {
            log("Book with name '" + name + "' not found");
        } else {
            log("Found book by name: " + name);
        }
        return books;
    }

    //Поиск всех книг по автору
    public List<Book> findByAuthor(String author) {
        List<Book> books = booksByAuthor.getOrDefault(author, Collections.emptyList());
        log("Found " + books.size() + " books by author: " + author);
        return books;
    }

    //Поиск всех книг по жанру
    public List<Book> findByGenre(String genre) {
        List<Book> books = booksByGenre.getOrDefault(genre, Collections.emptyList());
        log("Found " + books.size() + " books by genre: " + genre);
        return books;
    }

    //Получение всех книг
    public List<Book> getAllBooks() {
        List<Book> allBooks = new ArrayList<>(booksById.values());
        log("Search all books in the library. Total number of books= " + allBooks.size());
        return allBooks;
    }
}

public class Main {
    public static void main(String[] args) {
        BookService service = new BookService();

        service.addBook(new Book(1, "Clean Code", "Robert Martin", "textbooks"));
        service.addBook(new Book(2, "Effective Java", "Joshua Bloch", "textbooks"));
        service.addBook(new Book(3, "Not Effective Java", "Joshua Bloch", "fantasy"));
        service.addBook(new Book(0, "The best Java", "Joshua Bloch", "fantasy"));
        service.addBook(new Book(5, "The best of the Java", "Joshua Mainer", "fantasy"));


        System.out.println(service.findById(1));
        System.out.println("*****");
        System.out.println(service.findByName("Effective Java"));
        System.out.println("*****");
        System.out.println(service.findByAuthor("Joshua Bloch"));
        System.out.println("*****");
        System.out.println(service.findByGenre("textbooks"));
        System.out.println("*****");
        System.out.println(service.getAllBooks());
        System.out.println("*****");
        service.deleteBook(3);
        System.out.println("*****");
        System.out.println(service.getAllBooks());
        service.addBook(new Book(0, "Next book of the Java", "Kevin Mainer", "fantasy"));
        System.out.println(service.getAllBooks());

    }
}
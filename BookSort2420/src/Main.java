import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
//John Barnett CSIS 2420
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        //Had to enter the direct file path to get it to work
        // have never run into this issue before
        String filepath = "C:\\Users\\John\\IdeaProjects\\BookApp\\src\\books.csv";
        File file = new File(filepath);
        List<Book> list = BookApp.getList(file);
        System.out.println(String.valueOf(list.size()));
        list.forEach(e -> System.out.println(e.toString()));
        
        //Collections.reverse(list);
        //System.out.println(String.valueOf(list.size()));
        //list.forEach(e -> System.out.println(e.toString()));

        Comparator<Book> comparator = new Comparator<Book>() {
            @Override
            public int compare(Book book1, Book book2) {
                return book2.compareTo(book1);

            }
        } ;
        list.sort(comparator);
        System.out.println(String.valueOf(list.size()));
        list.forEach(e -> System.out.println(e.toString()));

    }
}

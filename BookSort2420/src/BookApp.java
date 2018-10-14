import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class BookApp {

    List<String> errorOut = new ArrayList<>();
    public static List<Book> getList(File input) throws FileNotFoundException {
        List<Book> output = new ArrayList<>();
        Scanner reader = new Scanner(input);



        while(reader.hasNextLine()){
            String currentLine = reader.nextLine();
            Scanner lineReader = new Scanner(currentLine).useDelimiter(",");
            try{
                output.add(new Book(lineReader.next(),lineReader.next(),lineReader.nextInt()));

            }catch (Exception e){
              // System.out.println("Error:" +e);

                System.out.println("Error: "+currentLine);
            }

            }
            try {
                Collections.sort(output);

            }catch (Exception b){
            System.out.println("Error Collections:"+b);
            }
        return output;
        }


    }

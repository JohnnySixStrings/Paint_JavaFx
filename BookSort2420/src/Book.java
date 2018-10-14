public  class Book implements Comparable<Book> {
    private String title;



    private String author;



    private int year;

    public Book(String title,String author, int year){
        this.title = title;
        this.author = author;
        this.year = year;
    }
    public int getYear() {
        return year;
    }
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return this.title+" by "+this.author+" "+String.valueOf(this.year);
    }
    @Override
    public int compareTo(Book other){
     if(this.title.compareTo(other.title)==0) return 0;
     return this.title.compareTo(other.title);
    }
}

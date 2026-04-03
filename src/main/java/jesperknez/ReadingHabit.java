package jesperknez;

public class ReadingHabit
{
    private final int habitID;
    private final int userID;
    private final int bookID;
    private Book book = null;
    private final String submissionMoment;
    public int pagesRead;

    public ReadingHabit(int habitID, int userID, int bookID, int pagesRead, String submissionMoment)
    {
        this.habitID = habitID;
        this.userID = userID;
        this.bookID = bookID;
        this.pagesRead = pagesRead;
        this.submissionMoment = submissionMoment;
    }

    // Overload for when we have the full book (for example, after a join)
    public ReadingHabit(int habitID, int userID, Book book, int pagesRead, String submissionMoment)
    {
        this.habitID = habitID;
        this.userID = userID;
        this.bookID = book.getId();
        this.book = book;
        this.pagesRead = pagesRead;
        this.submissionMoment = submissionMoment;
    }

    public int getHabitID()
    {
        return habitID;
    }

    public int getUserID()
    {
        return userID;
    }

    public int getBookID()
    {
        return bookID;
    }

    public Book getBook()
    {
        return book;
    }

    public String getSubmissionMoment()
    {
        return submissionMoment;
    }

    public int getPagesRead(){
        return pagesRead;
    }
}

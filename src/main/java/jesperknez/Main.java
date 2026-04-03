package jesperknez;

import java.util.List;

public class Main {

    public static int ReadIntFromIO(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(IO.readln(prompt));
            } catch (NumberFormatException e) {
                IO.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    public static float ReadFloatFromIO(String prompt) {
        while (true) {
            try {
                return Float.parseFloat(IO.readln(prompt));
            } catch (NumberFormatException e) {
                IO.println("Invalid input. Please enter a valid decimal number.");
            }
        }
    }

    public static String ReadStringFromIO(String prompt) {
        while (true) {
            String input = IO.readln(prompt);
            if (input != null && !input.trim().isEmpty()) {
                return input;
            }
            IO.println("Invalid input. Please enter some text.");
        }
    }

    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        ReadingHabitDAO habitDAO = new ReadingHabitDAO();
        BookDAO bookDAO = new BookDAO();

        IO.println("=== BOOKTRACKER CLI ===");
        boolean quit = false;

        while (!quit) {
            IO.println("\nPlease make a choice:");
            IO.println("1. Add user");
            IO.println("2. Reading habits per user");
            IO.println("3. Update book title");
            IO.println("4. Delete ReadingHabit record");
            IO.println("5. Mean age of users");
            IO.println("6. Unique readers per book");
            IO.println("7. Total pages read by all users");
            IO.println("8. Users with more than 1 book");
            IO.println("0. Exit");

            int choice = ReadIntFromIO("> Choice: ");

            switch (choice) {
                case 1:
                    int age = ReadIntFromIO("Age: ");
                    String gender = ReadStringFromIO("Gender (m/f): ");
                    String name = ReadStringFromIO("Name: ");

                    userDAO.addUser(age, gender, name);
                    IO.println("User successfully added!");
                    break;

                case 2:
                    int uId = ReadIntFromIO("UserID: ");
                    List<ReadingHabit> habits = habitDAO.getByUserId(uId);
                    for (ReadingHabit h : habits) {
                        IO.println("Book: " + h.getBook().getTitle() + " | Pages: " + h.getPagesRead() + " | Date: " + h.getSubmissionMoment());
                    }
                    break;

                case 3:
                    int bId = ReadIntFromIO("BookID: ");
                    String title = ReadStringFromIO("New Title: ");

                    bookDAO.updateTitle(bId, title);
                    IO.println("Title successfully updated.");
                    break;

                case 4:
                    int hId = ReadIntFromIO("HabitID to delete: ");
                    habitDAO.deleteRow(hId);
                    IO.println("Record deleted.");
                    break;

                case 5:
                    IO.println("Mean age: " + userDAO.getUsersMeanAge());
                    break;

                case 6:
                    int bookId = ReadIntFromIO("BookID: ");
                    IO.println("Number of unique readers: " + userDAO.countUniqueUsersForBook(bookId));
                    break;

                case 7:
                    IO.println("Total pages read by all users: " + habitDAO.getTotalPagesRead());
                    break;

                case 8:
                    IO.println("Number of users with more than 1 book: " + userDAO.countUsersWithMultipleBooks());
                    break;

                case 0:
                    quit = true;
                    break;

                default:
                    IO.println("Invalid choice, please enter a number between 0 and 8.");
            }
        }

        IO.println("Program exited. Goodbye!");
    }
}
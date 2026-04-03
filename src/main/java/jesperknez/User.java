package jesperknez;

public class User
{
    private final int id;
    private final int age;
    private final String gender;
    private final String Name;

    public User(int id, int age, String gender, String Name)
    {
        this.id = id;
        this.age = age;
        this.gender = gender;
        this.Name = Name;
    }

    public int getId()
    {
        return id;
    }

    public int getAge()
    {
        return age;
    }

    public String getGender()
    {
        return gender;
    }

    public String getName()
    {
        return Name;
    }
}

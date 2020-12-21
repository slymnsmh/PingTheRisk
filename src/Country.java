public class Country
{
    int id;
    String name;
    String location;
    int hackerNumber;
    Player owner;

    public Country( int id, String name, String location, Player owner )
    {
        this.id = id;
        this.name = name;
        this.location = location;
        this.owner = owner;
    }

    public Country( int id, String name, String location )
    {
        this.id = id;
        this.name = name;
        this.location = location;
    }
}

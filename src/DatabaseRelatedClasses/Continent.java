package DatabaseRelatedClasses;

import ServerClasses.Country;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Continent
{
    int id;
    String name;
    int numOfCountries;
    ArrayList<Country> countries;

    public Continent( int id, String name, int numOfCountries, String countries ) throws SQLException
    {
        this.id = id;
        this.name = name;
        this.numOfCountries = numOfCountries;
        this.countries = getCountryListFromString(countries);
    }

    public Continent( int id, String name, int numOfCountries, ArrayList<Country> countries )
    {
        this.id = id;
        this.name = name;
        this.numOfCountries = numOfCountries;
        this.countries = countries;
    }

    public Country getCountry(int countryIndex)
    {
        return countries.get(countryIndex);
    }

    public ArrayList<Country> getCountryListFromString(String countries) throws SQLException
    {
        ArrayList<Country> countriesArray = new ArrayList<>();
        String countryIdStr = "";
        for ( int i = 0; i < countries.length(); i++ )
        {
            if ( countryIdStr.charAt(i) != ',' )
            {
                countryIdStr += countryIdStr.charAt(i);
                continue;
            }
            String query = "SELECT * from country WHERE id='"+countryIdStr+"'";
            Database.connect();
            Database.stmt = Database.conn.createStatement();
            ResultSet rs = Database.stmt.executeQuery(query);
            rs.next();
            Country country = new Country(Integer.parseInt(countryIdStr), countryIdStr, rs.getString("location"));
            countriesArray.add(country);
            countryIdStr = "";
        }
        return countriesArray;
    }

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public int getNumOfCountries()
    {
        return numOfCountries;
    }

    public void setNumOfCountries( int numOfCountries )
    {
        this.numOfCountries = numOfCountries;
    }

    public ArrayList<Country> getCountries()
    {
        return countries;
    }

    public void setCountries( ArrayList<Country> countries )
    {
        this.countries = countries;
    }
}

package DatabaseRelatedClasses;

import ServerClasses.Country;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Map
{
    int id;
    int numOfContinents;
    ArrayList<Continent> continents;
    ResultSet rsMap;

    public Map() throws SQLException
    {
        rsMap = getMapInfo();
        id = rsMap.getInt("id");
        numOfContinents = rsMap.getInt("num_of_continents");
        continents = getContinentListFromString();
    }

    public Country getCountry(int continentIndex, int countryIndex)
    {
        return continents.get(continentIndex).getCountry(countryIndex);
    }

    public ResultSet getMapInfo() throws SQLException
    {
        String query = "SELECT * from map";
        Database.connect();
        Database.stmt = Database.conn.createStatement();
        ResultSet rs = Database.stmt.executeQuery(query);
        if ( rs.next() )
            return rs;
        return null;
    }

    public ArrayList<Continent> getContinentListFromString() throws SQLException
    {
        ArrayList<Continent> continents = new ArrayList<>();
        String continentsStr = rsMap.getString("continents");
        String continentIdStr = "";
        for ( int i = 0; i < continentsStr.length(); i++ )
        {
            if ( continentsStr.charAt(i) != ',' )
            {
                continentIdStr += continentsStr.charAt(i);
                continue;
            }
            String query = "SELECT * from continent WHERE id='"+continentIdStr+"'";
            Database.connect();
            Database.stmt = Database.conn.createStatement();
            ResultSet rs = Database.stmt.executeQuery(query);
            rs.next();
            Continent continent = new Continent(Integer.parseInt(continentIdStr), continentIdStr, rs.getInt("num_of_countries"), rs.getString("countries"));
            continents.add(continent);
            continentIdStr = "";
        }
        return continents;
    }

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public int getNumOfContinents()
    {
        return numOfContinents;
    }

    public void setNumOfContinents( int numOfContinents )
    {
        this.numOfContinents = numOfContinents;
    }

    public ArrayList<Continent> getContinents()
    {
        return continents;
    }

    public void setContinents( ArrayList<Continent> continents )
    {
        this.continents = continents;
    }

    public ResultSet getRsMap()
    {
        return rsMap;
    }

    public void setRsMap( ResultSet rsMap )
    {
        this.rsMap = rsMap;
    }
}

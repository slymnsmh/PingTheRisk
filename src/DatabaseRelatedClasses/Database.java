package DatabaseRelatedClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database
{
    public static Connection conn;
    public static Statement stmt;
    public static final String host = "ssdwork.com:3306";
    public static final String dbName = "ssdswork_ping_the_risk";
    public static final String user = "ssdswork";
    public static final String password = "n34*2agCnh";

    public static String connect() throws SQLException
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            return "Mysql Driver'inde sorun bulunmakta!";
        }
        try
        {
            conn = DriverManager.getConnection ("jdbc:mysql://"+host+"/"+dbName+"?serverTimezone=America/Los_Angeles",user,password);
        }
        catch(Exception e)
        {
            return "Veritabanı bağlantısı başarısız!\n----------Hata mesajı----------\n" + e.getMessage() + "\n---------------------------------";
        }
        return "Veritabanı bağlantısı başarılı!";
    }
}

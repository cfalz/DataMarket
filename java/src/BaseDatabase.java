package dbinterface;

import menu.Menu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;


public abstract class BaseDatabase
{
    private String _dbname;
    private String _dbport;
    private String _dbuser;
    private String _dbpass;
    
	public BaseDatabase(String dbname, String dbport, String dbuser, String dbpass)
    {
        this._dbname = dbname;
        this._dbport = dbport;
        this._dbuser = dbuser;
        this._dbpass = dbpass;
        
    }

    public abstract void createUser(Menu menu);
    public abstract void login(Menu menu);
    public abstract void closeConnection();
    public abstract String findType();

}

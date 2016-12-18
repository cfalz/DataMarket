package dbinterface;

import menu.Menu;
import querycmd.QueryCmd;
import user.User;

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
    protected QueryCmd _qcmd; 

	public BaseDatabase(String dbname, String dbport, String dbuser, String dbpass)
    {
        this._dbname = dbname;
        this._dbport = dbport;
        this._dbuser = dbuser;
        this._dbpass = dbpass;

        try
        {
            _qcmd = new QueryCmd(dbname, dbport, dbuser, dbpass);
        }
        catch(Exception e)
        {
            System.out.println("[-] BaseDataBase :: Failed to connect.");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        
    }

    public abstract void createUser(User currentUser);
    public abstract void login(User currentUser);
    public abstract void logout();
    public abstract String findType(User currentUser);
    protected abstract boolean isValidLogin(User currentUser);

}

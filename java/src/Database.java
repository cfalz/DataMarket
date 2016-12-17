package dbinterface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;


public abstract class Database
{
	public Database(String dbname, String dbport, String dbuser, String dbpass);	

}
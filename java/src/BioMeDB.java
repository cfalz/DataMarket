package biomeDB;

import querycmd.QueryCmd;
//import dbinterface.Database;
import menu.Menu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;



public class BioMeDB implements Database
{
	private QueryCmd _qcmd;

	public BioMeDB(String dbname, String dbport, String user, String passwd) throws SQLException
	{
		try
		{
			_qcmd = new QueryCmd(dbname, dbport, user, passwd);

		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
            System.out.println("Error Connecting to db: make sure postgres is started");
            System.exit(-1);	
		}
	}


//////////////////////////////////////////////////////////////////////
// ****************************************************************
//                   BioMarketDB queries
// ****************************************************************
//////////////////////////////////////////////////////////////////////


//////////////////////////////////////////////////////////////////////
// Checks if login is valid                 
//////////////////////////////////////////////////////////////////////
public boolean isValidLogin(String uname, String pass)
{
   try
   {
   String query = String.format("SELECT name FROM Users WHERE login='%s' AND password='%s'", uname, pass); 
   int result = _qcmd.executeQuery(query);
   if(result>=1)
        return true;
   else
        return false;
   }
   catch(Exception e)
   {
        System.out.println(String.format("Error in validating login:%s", e.getMessage()));
        return false;
   }   
}//End isvalidLogin



//////////////////////////////////////////////////////////////////////
// Checks if login is valid                 
//////////////////////////////////////////////////////////////////////
boolean hasPrivilege(String user_login,String data_id)
{
    try
    {
        String query = String.format("SELECT * FROM Privileges WHERE login='%s' AND dataid='%s'",user_login, data_id);
        int res = _qcmd.executeQuery(query);
        if(res>= 1)
            return true;
        else
            return false;
    }
    catch(Exception e)
    {
        
    }
    return false;
}//end hadPrivilege

public void viewData(String data_id)
{
    try
    {
        String q = String.format("SELECT name, sourceid from Data where dataid='%s'",data_id);
        
        List<List<String>> results = _qcmd.executeQueryAndReturnResult(q);
       
        int resSize = results.size();
        int total = 0;
        for(List<String> sublist : results)
        {
           total += sublist.size();
        }//total is number of strings

        String name = results.get(0).get(0);  
        System.out.println("name: "+results.get(0).get(0));

        System.out.println("sourceid: "+results.get(0).get(1));
        String sId = results.get(0).get(1);
        
        //Constructing sources table query
        String q2 = String.format("SELECT name FROM Source WHERE sourceid='%s'",sId); 
        _qcmd.executeQueryAndPrintResult(q2);
        
        
    }
    catch(Exception e)
    {
        System.out.println("viewData Failed");
    }

}//end viewData

///////////////////////////////////////////////////////////////////////
//
// creates a user and returns the type
//
///////////////////////////////////////////////////////////////////////
public void createUser(BioMeDB db)
{
    try
    {
        List<String> info  =new ArrayList<String>();
        info = getUserInfo();
        String query = String.format("INSERT INTO Users(login, password, type) VALUES('%s','%s','%s')", info.get(0), info.get(1), "basic");
        _qcmd.executeQuery(query);
        System.out.println("user added to database");
       
    }
    catch(Exception e)
    {
	System.out.println("[-] Failed to Create New User. ");
    }

}//createUser

//Todo implement Menu getCreds() returns a List<String>[username, password]
public void login(BioMeDB db, Menu menu)
{
	try
	{
		List<String > creds = menu.getCreds();

		String username = creds.get(0);
		String pass = creds.get(1);

		if(isValidLogin(username, pass))
		{
		    System.out.println("[+] Authentication Successful.");
		    db.userLogin = username;
		    db.userType = findType();
		}
		else
		{
		    System.out.println("[-] Invalid user name or password");
		}
	}
	catch(Exception e)
	{
		System.out.println("[-] User Login Failed.");
	}
}


public String findType()
{
	try
	{
		String query = String.format("select Users.type from Users where Users.login = '%s'", userLogin);
		
		List<List<String> > typeList = _qcmd.executeQueryAndReturnResult(query);
		return typeList.get(0).get(0);
	}
	catch(Exception e)
	{
		System.out.println("[-] Finding User Type Failed.");
		return null;
	}
}


}//end BioMeDB

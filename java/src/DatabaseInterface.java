import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Scanner;
import java.io.Console;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.io.*;


public class DatabaseInterface
{
    //connection to database
    private Connection _connection = null;

    //User Login
    private static String userLogin = null;
    private static String userType = null;

    //keyboard input
    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

//////////////////////////////////////////////////////////////////////
// DatabaseInterface(dbname, dbport, user, password) 
//           This constructor connects to the database
/////////////////////////////////////////////////////////////////////
   public DatabaseInterface(String dbname, String dbport, String user, String passwd) throws SQLException
   {

        System.out.println("Connection to database");
        try
        {
        // crates url connection
        String url = "jdbc:postgresql://localhost:" + dbport + "/" + dbname;
        System.out.println("Connection URL: " + url + "\n");

        // physically connects
        this._connection = DriverManager.getConnection(url, user, passwd);
        System.out.println("Complete");

        }
        catch(Exception e)
        {
	    System.out.println(e.getMessage());
            System.out.println("Error Connecting to db: make sure postgres is started");
            System.exit(-1);
        }

   }//end DatabaseInterface


//////////////////////////////////////////////////////////////////////
// executeUpate(String sql)
//               This method takes sql instructions such as
//               CREATE, INSERT, UPDATE, DELETE, DROP
//////////////////////////////////////////////////////////////////////
   public void executeUpdate(String sql) throws SQLException
   {
    //create statement
    Statement stmt = this._connection.createStatement();
    
    //issue update instruction
    stmt.executeUpdate(sql);

    //close instruction
    stmt.close();
   }//end update



//////////////////////////////////////////////////////////////////////
//executeQueryAndPrintResults(String query)
//                  this is a debug method that takes in the query and
//                  prints results
//                  returns number of rows
//////////////////////////////////////////////////////////////////////
public int executeQueryAndPrintResult(String query) throws SQLException 
{
   //create statment
   Statement stmt = this._connection.createStatement();

   //issue query instructions
   ResultSet rs = stmt.executeQuery(query);

   //get metadata (this contains row and column info)
   ResultSetMetaData rsmd = rs.getMetaData();
   int numCol = rsmd.getColumnCount();
   int rowCount = 0;


   //iterate through result set and print
   boolean outputHeader = true;
   while(rs.next())
   {
       if(outputHeader)
       {
           for(int i=1; i<=numCol; i++)
           {
                System.out.print(rsmd.getColumnName(i) +"\t\t\t");
           }
           System.out.println();
           outputHeader = false;
       }
       for(int i=1; i<=numCol; ++i)
       {
            System.out.print(rs.getString(i) + "\t\t\t");
       }
       System.out.println();
       ++rowCount;
   }//endwhile
    
   stmt.close();
   return rowCount;
}


//////////////////////////////////////////////////////////////////////
// Executes query and Returns results as a list of lists
//////////////////////////////////////////////////////////////////////
public List<List<String>> executeQueryAndReturnResult(String query) throws SQLException {
        // creates a statement object
        Statement stmt = this._connection.createStatement();

        // issues the query instruction
        ResultSet rs = stmt.executeQuery(query);
 
      /* 
       ** obtains the metadata object for the returned result set.  The metadata 
       ** contains row and column info. 
       */
        ResultSetMetaData rsmd = rs.getMetaData();
        int numCol = rsmd.getColumnCount();
        int rowCount = 0;

        // iterates through the result set and saves the data returned by the query.
        boolean outputHeader = false;
        List<List<String>> result = new ArrayList<List<String>>();
        while (rs.next()) {
            List<String> record = new ArrayList<String>();
            for (int i = 1; i <= numCol; ++i)
                record.add(rs.getString(i));
            result.add(record);
        }//end while
        stmt.close();
        return result;
    }//end executeQueryAndReturnResult

//////////////////////////////////////////////////////////////////////
// This method issues the query to the DBMS 
// and returns the number of results
//////////////////////////////////////////////////////////////////////
public int executeQuery(String query) throws SQLException {
        // creates a statement object
        Statement stmt = this._connection.createStatement();

        // issues the query instruction
        ResultSet rs = stmt.executeQuery(query);

        int rowCount = 0;

        // iterates through the result set and count nuber of results.
        if (rs.next()) {
            rowCount++;
        }//end while
        stmt.close();
        return rowCount;
    }

//////////////////////////////////////////////////////////////////////
//  Method to fetch the last value from sequence. This
//  method issues the query to the DBMS and returns the current
//  value of sequence used for autogenerated keys
//////////////////////////////////////////////////////////////////////
public int getCurrSeqVal(String sequence) throws SQLException 
{
        Statement stmt = this._connection.createStatement();

        ResultSet rs = stmt.executeQuery(String.format("Select currval('%s')", sequence));
        if (rs.next())
            return rs.getInt(1);
        return -1;
    }


//////////////////////////////////////////////////////////////////////
//                  CleanUP 
//////////////////////////////////////////////////////////////////////
public void cleanup()
{
    try
    {
        if(this._connection !=null)
        {
            this._connection.close();
        }
    }
    catch(SQLException e)
    {

    }
}

public static int readChoice()
{
	int choice;
	do
	{
		System.out.print("[!] Enter Your Choice: "); 
		try
		{
			choice = Integer.parseInt(in.readLine());
			break;
		}
		catch(Exception e)
		{
			System.out.println("[-] -- Invalid Input. ");
			continue;
		}
	}while(true);//End do
	return choice;
}//End readChoice()


//////////////////////////////////////////////////////////////////////
//                   Main
//////////////////////////////////////////////////////////////////////
public static void main(String[] argc)
{
    String dbname = "biomarketdb";
    String dbport = "5432";
    String uname = "postgres";
    String paswd = "postgres";

    try
    {
        DatabaseInterface db = new DatabaseInterface(dbname,dbport, uname, paswd); 
        

        /*
        //Checking privilages
        System.out.println("enter user login");
        String ulogin = in.readLine();
        System.out.println(ulogin);

        System.out.println("enter dataid");
        String datid = in.readLine();
        System.out.println(datid);

        if(db.hasPrivilege(ulogin, datid))
        {
            String s = String.format("User: %s\ndata_id: %s\n", ulogin,datid);
            System.out.println("Privilege Verified! \n"+s);
            db.viewData(datid);
        }
        else
        {
            System.out.println("Access Denied!\nUser does NOT have privilege to that data");
        }
        */





	boolean menu = true;
	greeting();

	while(menu)
	{
		System.out.println("*** MAIN MENU ***");
		System.out.println("------------------");
		System.out.println("1. Create User ");
		System.out.println("2. Log In ");
		System.out.println("3. EXIT ");

		//Reset User Login 
		userLogin = null;

		switch (readChoice())
		{
			case 1: db.createUser(db); break;
			case 2: db.login(db); break;
			case 3: menu = false; System.out.println("[!] GoodBye! "); break;
			default: System.out.println("[-] Invalid Choice. "); break;
		}//End Switch

	
	if(userLogin != null)
	{
		boolean userMenu = true;
		userType = userType.trim();
		switch(userType)
		{
			/////////////// BASIC USER MENU //////////////////////
			case "basic":
			while(userMenu)
			{
				System.out.println("*** MENU ***");
				System.out.println("------------------");
				System.out.println("1. Search Users ");
				System.out.println("2. Search Data ");
				System.out.println("3. My Profile ");
				System.out.println("4. Exit ");

				switch(readChoice())
				{
					case 1: System.out.println("[!] Search Users Called, But Currently Not Implemented. "); break; 
					case 2: System.out.println("[!] Search Data Called, But Currently Not Implemented. "); break; 
					case 3: System.out.println("[!] My Profile Called, But Currently Not Implemented. "); break; 
					case 4: userMenu = false; System.out.println("[!] GoodBye! "); break;
					default: System.out.println("[-] Invalid Choice. "); break;
					
				}//End readChoice Switch
				
			}break;//End While


			/////////////// ADMIN USER MENU //////////////////////
			case "admin":
			while(userMenu)
			{
				System.out.println("*** ADMIN MENU ***");
				System.out.println("------------------");
				System.out.println("1. Search Users ");
				System.out.println("2. Search Data ");
				System.out.println("3. My Profile ");
				System.out.println("4. Update User ");
				System.out.println("5. View Statistics ");
				System.out.println("6. Exit ");

				switch(readChoice())
				{
					case 1: System.out.println("[!] Search Users Called, But Currently Not Implemented. "); break; 
					case 2: System.out.println("[!] Search Data Called, But Currently Not Implemented. "); break; 
					case 3: System.out.println("[!] My Profile Called, But Currently Not Implemented. "); break; 
					case 4: System.out.println("[!] Update User Called, But Currently Not Implemented. "); break; 
					case 5: System.out.println("[!] View Statistics Called, But Currently Not Implemented. "); break; 
					case 6: userMenu = false; System.out.println("[!] GoodBye! "); break;
					default: System.out.println("[-] Invalid Choice. "); break;
					
				}//End readChoice Switch
				
			}break;//End While

		}//End userType Switch
		
	}//End userLogin if

	}//End While
	
        db.cleanup();
    }
    catch(Exception e)
    {
        String error = String.format("Main Error: %s", e.getMessage());
        System.out.println(error);
    }

}//end Main

public static void greeting()
{
	System.out.println(
	"\n\n" +
       "//////////////////////////////////////////////////\n" +
       "//						//\n" +
       "//	  WELCOME TO THE BioMe DATAMARKET       //\n" +
       "//						//\n" +
       "//      Contributing To The Health Of Others,   //\n" +
       "//	      One Dataset At A Time.		//\n" + 
       "//						//\n" +
       "//////////////////////////////////////////////////\n" +
	"\n\n");
	
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
   int result = executeQuery(query);
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
        int res = executeQuery(query);
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
        
        List<List<String>> results = executeQueryAndReturnResult(q);
       
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
        executeQueryAndPrintResult(q2);
        
        
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
public void createUser(DatabaseInterface db)
{
    try
    {
        List<String> info  =new ArrayList<String>();
        info = getUserInfo();
        String query = String.format("INSERT INTO Users(login, password, type) VALUES('%s','%s','%s')", info.get(0), info.get(1), "basic");
        executeQuery(query);
        System.out.println("user added to database");
       
    }
    catch(Exception e)
    {
	System.out.println("[-] Failed to Create New User. ");
    }


}//createUser

public void login(DatabaseInterface db)
{
	try
	{
		System.out.println("[!] Enter username: ");
		String username = in.readLine();

		System.out.println("[!] Enter password: ");
		String pass = in.readLine();


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

///////////////////////////////////////////////////////////////////////
//
// Helper function returns list containing username, passwd in that order
//
///////////////////////////////////////////////////////////////////////
private List<String> getUserInfo()
{
    try
    {
        System.out.println("Enter Username: ");
        String uname = in.readLine();

        System.out.println("Enter Password: ");
        String passwd = in.readLine();

       
        List<String> info = new ArrayList<String>();
        info.add(uname);
        info.add(passwd);
        return info;
    }
    catch(Exception e)
    {
       List<String> error = null;
       return error;
    }

}//end getUserInfo

public String findType()
{
	try
	{
		String query = String.format("select Users.type from Users where Users.login = '%s'", userLogin);
		
		List<List<String> > typeList = executeQueryAndReturnResult(query);
		return typeList.get(0).get(0);
	}
	catch(Exception e)
	{
		System.out.println("[-] Finding User Type Failed.");
		return null;
	}
}





}///end DatabaseInterface

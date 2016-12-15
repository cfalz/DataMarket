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
            System.out.println("Error Connecting to db: make sure postgres is started");
            System.exit(-1);
        }

   }//end Connect


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
        
        System.out.println("Enter username: ");
        String username = in.readLine();

        System.out.println("Enter password: ");
        String pass = in.readLine();


        if(db.isValidLogin(username, pass))
        {
            System.out.println("Authenticated!!");
        }
        else
        {
            System.out.println("Invalid user name or password");
        }

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


        db.cleanup();
    }
    catch(Exception e)
    {
        String error = String.format("Main Error: %s", e.getMessage());
        System.out.println(error);
    }

}//end Main

//////////////////////////////////////////////////////////////////////
//                   BioMarketDB queries
//////////////////////////////////////////////////////////////////////
boolean isValidLogin(String uname, String pass)
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
}



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

}







}

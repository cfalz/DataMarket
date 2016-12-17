package querycmd;

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

public class QueryCmd
{

   //connection to database
   private Connection _connection = null;


   public QueryCmd(String dbname, String dbport, String user, String passwd) throws SQLException
   {

        try
        {
        // crates url connection
        String url = createUrl(dbport, dbname);
        //System.out.println("Connection URL: " + url + "\n");

        // physically connects
        this._connection = DriverManager.getConnection(url, user, passwd);
        //System.out.println("Complete");

        }
        catch(Exception e)
        {
	        System.out.println(e.getMessage());
            System.out.println("Error Connecting to db: make sure postgres is started");
            System.exit(-1);
        }
   }

	//////////////////////////////////////////////////////////////////////
	// createUrl(portOfDatabase, nameofDatabase)
	// creates the url string for the connection
	//////////////////////////////////////////////////////////////////////
   private String createUrl(String dbport, dbname)
   {
        String url = "jdbc:postgresql://localhost:" + dbport + "/" + dbname;
        return url;
   }



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


}//end QueryCmd class
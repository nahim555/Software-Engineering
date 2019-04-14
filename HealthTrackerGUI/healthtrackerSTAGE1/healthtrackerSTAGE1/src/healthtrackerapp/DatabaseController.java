
package healthtrackerapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ilyas
 */
public class DatabaseController {
    
    private Connection myconn;
    
    /**
     * Constructor to initialise database
     */
    public DatabaseController(){
        myconn = null;
        connect();
        
    }
    
    /**
     * Method to connect to database 
     */
    public void connect(){
        
        try{
            myconn = DriverManager.getConnection("jdbc:derby://localhost:1527/APP", "APP", "APP");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    /**
     * Method to call database 
     * @return 
     */
    public Connection getConnect(){
        return myconn;
    }
    
}

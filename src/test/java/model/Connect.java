package model;


import java.sql.ResultSet;
import java.sql.Statement;

public class Connect {
    String [] parameters = Environment.readConnFile();
    private java.sql.Connection  con = null;
    private final String url = parameters[0];
    private final String serverName= parameters[1];
    private final String portNumber = parameters[2];
    private final String databaseName= parameters[3];
    private final String userName = parameters[4];
    private final String password = parameters[5];
    private final String instance = parameters[6];

    String [] query = Environment.readQueryFile();
    private final String status_update = query[0];
    private final String currency_query = query[1];

    public Connect(){}

    private String getConnectionUrl(){
        return url + serverName + ":" + portNumber + ";databaseName=" + databaseName + ";Instance=" + instance + ";";
    }

    private java.sql.Connection getConnection(){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = java.sql.DriverManager.getConnection(getConnectionUrl(), userName, password);
            //if(con!=null) System.out.println("Connection Successful!");
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error Trace in getConnection() : " + e.getMessage());
        }
        return con;
    }

    public void executeQuery(String idTransaction){
        Statement statement = null;
        try{
            con= this.getConnection();
            if(con!=null){
                statement = con.createStatement();
                statement.executeUpdate(status_update + idTransaction);
                closeConnection();
            }else System.out.println("Error: No active Connection");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void closeConnection(){
        try{
            if(con!=null)
                con.close();
            con=null;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public float getRate(String currency){

        Statement statement = null;
        float rate = 0;
        try{
            con= this.getConnection();
            if(con!=null){
                statement = con.createStatement();
                ResultSet rs = statement.executeQuery(currency_query + currency + "' ORDER BY Date DESC");
                while(rs.next()){
                    return rs.getFloat("Value");
                }
                rs.close();
                closeConnection();
            }else System.out.println("Error: No active Connection");
        }catch(Exception e){
            e.printStackTrace();
        }
        return rate;
    }
}

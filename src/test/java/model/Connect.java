package model;


import java.sql.Statement;

public class Connect {

    private java.sql.Connection  con = null;
    private final String url = "jdbc:sqlserver://";
    private final String serverName= "192.168.88.240";
    private final String portNumber = "1433";
    private final String databaseName= "PayOnlineSystem";
    private final String userName = "externalUser";
    private final String password = "tester123";
    public Connect(){}

    private String getConnectionUrl(){
        return url+serverName+":"+portNumber+";databaseName="+databaseName+";Instance=SQL12LISTENER;";
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
                statement.executeUpdate("UPDATE [PayOnlineSystem].[dbo].[tblTransaction] SET [Result]=2 WHERE [Id]=" + idTransaction);
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
}

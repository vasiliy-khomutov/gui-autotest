package model;


import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataProviders {

    @DataProvider (name = "listFailLogin" )
    public static Iterator<Object[]> listFailLogin (ITestContext context) {
        String inputFile = context.getCurrentXmlTest().getParameter("filenamePathFailLogin");
        String workingDir = System.getProperty("user.dir");
        List<String> testData = getFileContentList(workingDir.substring(0, workingDir.length()) + inputFile);
        List<Object[]> dataToBeReturned = new ArrayList<Object[]>();
        for (String userData : testData ) {
            dataToBeReturned.add(new Object[] { userData} );
        }
        return dataToBeReturned.iterator();
    }

    @DataProvider (name = "listFailCaptcha" )
    public static Iterator<Object[]> listFailCaptcha (ITestContext context) {
        String inputFile = context.getCurrentXmlTest().getParameter("filenamePathFailCaptcha");
        String workingDir = System.getProperty("user.dir");
        List<String> testData = getFileContentList(workingDir.substring(0, workingDir.length()) + inputFile);
        List<Object[]> dataToBeReturned = new ArrayList<Object[]>();
        for (String userData : testData ) {
            dataToBeReturned.add(new Object[] { userData} );
        }
        return dataToBeReturned.iterator();
    }

    @DataProvider (name = "listFailLoginAndPassword" )
    public static Iterator<Object[]> listFailLoginAndPassword (ITestContext context) {
        String inputFile = context.getCurrentXmlTest().getParameter("filenamePathFailLoginAndPassword");
        String workingDir = System.getProperty("user.dir");
        List<String> testData = getFileContentList(workingDir.substring(0, workingDir.length()) + inputFile);
        List<Object[]> dataToBeReturned = new ArrayList<Object[]>();
        for (String userData : testData ) {
            String[] to = userData.split("=") ;
            for(int i = 0 ; i < to.length-1; i++)  {
                dataToBeReturned.add(new Object[] { to[i], to[i+1]} );
            }
        }
        return dataToBeReturned.iterator();
    }

    @DataProvider (name = "listPayments" )
    public static Iterator<Object[]> listPayments (ITestContext context) {
        String inputFile = context.getCurrentXmlTest().getParameter("filenamePathListPayments");
        String workingDir = System.getProperty("user.dir");
        List<String> testData = getFileContentList(workingDir.substring(0, workingDir.length()) + inputFile);
        List<Object[]> dataToBeReturned = new ArrayList<Object[]>();
        for (String userData : testData ) {
            String[] to = userData.split(";") ;
            dataToBeReturned.add(to);
        }
        return dataToBeReturned.iterator();
    }

    private static List<String> getFileContentList(String filenamePath) {

        InputStream inputStream;
        List<String> lines = new ArrayList<String> ();
        try {
            //inputStream = new FileInputStream(new File(filenamePath));
            //DataInputStream in = new DataInputStream(inputStream);
            //BufferedReader br = new BufferedReader(new InputStreamReader(in));
            File fileDir = new File(filenamePath);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF8"));

            String strLine;
            while ((strLine = br.readLine()) != null)   {
                lines.add(strLine);
            }
            //in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}

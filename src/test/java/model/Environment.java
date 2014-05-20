package model;


import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import critical.TestUtils;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Environment {

    public static WebDriver createDriver(){
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        //other driver and setting
        return driver;
    }


    // TODO readfiles
    public static String [] readConnFile() {
        String [] parameters = new String[5];
        try {
            FileReader reader = new FileReader("C:\\secureParametersAutotest\\db_connect.txt");
            BufferedReader in = new BufferedReader(reader);
            String string;
            while ((string = in.readLine()) != null){
                parameters=string.split("=");
            }
            in.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        return parameters;
    }

    public static String [] readQueryFile() {
        String [] parameters = new String[5];
        try {
            FileReader reader = new FileReader("C:\\secureParametersAutotest\\db_query.txt");
            BufferedReader in = new BufferedReader(reader);
            String string;
            while ((string = in.readLine()) != null){
                parameters=string.split(";");
            }
            in.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        return parameters;
    }

    public static String [] readFile() {
        String [] parameters = new String[5];
            try {
                FileReader reader = new FileReader("C:\\secureParametersAutotest\\parameters.txt");
                BufferedReader in = new BufferedReader(reader);
                String string;
                    while ((string = in.readLine()) != null){
                        parameters=string.split("=");
                    }
                in.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        return parameters;
    }

    public static String [] readAZFile() {
        String [] parameters = new String[5];
        try {
            FileReader reader = new FileReader("C:\\secureParametersAutotest\\gw_azeri.txt");
            BufferedReader in = new BufferedReader(reader);
            String string;
            while ((string = in.readLine()) != null){
                parameters=string.split("=");
            }
            in.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        return parameters;
    }

    public static HttpPost createPOSTRequest(String url){
        HttpPost request = new HttpPost(url);
        return request;
    }

    public static HttpEntityEnclosingRequestBase setEntityRequest(HttpEntityEnclosingRequestBase request, String bodyRequest){
        try {
            request.setEntity(new StringEntity(bodyRequest));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return request;
    }

    public static HttpRequest setHeadersRequest(HttpRequest request, String...headers){
        if(headers == null || headers.length == 0){
            return request;
        }
        return TestUtils.setHeader(request, headers);
    }

    public static List<String> getResponceRequest(HttpUriRequest request){

        try {
            HttpResponse response = createDefaultClient().execute(request);
            request.abort();
            InputStream input  = response.getEntity().getContent();
            return CharStreams.readLines(new InputStreamReader(input, Charsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static DefaultHttpClient createDefaultClient(){

        try{
            DefaultHttpClient client = new DefaultHttpClient();
            SSLContext sslContext = SSLContext.getInstance("TLS");
            X509TrustManager trustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }};

            sslContext.init(null, new TrustManager[]{trustManager}, null);
            SSLSocketFactory sslFactory = new SSLSocketFactory(sslContext);
            sslFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            ClientConnectionManager clientConMgr = client.getConnectionManager();
            SchemeRegistry schemeReg = clientConMgr.getSchemeRegistry();
            schemeReg.register(new Scheme("https", sslFactory, 443));
            return new DefaultHttpClient(clientConMgr, client.getParams());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return  null;
    }
}

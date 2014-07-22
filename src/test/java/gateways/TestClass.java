package gateways;


import testlink.api.java.client.TestLinkAPIClient;
import testlink.api.java.client.TestLinkAPIException;

public class TestClass implements Constatns {

    public static void reportTestCaseResult(String projectName, String testplanName, String testcaseName, String buildName,
                                            String msg,String result) throws TestLinkAPIException {

        TestLinkAPIClient testlinkAPIClient = new TestLinkAPIClient(DEVKEY, URLTEST);
        /*System.out.println(projectName);
        System.out.println(testplanName);
        System.out.println(testcaseName);
        System.out.println(buildName);
        System.out.println(msg);
        System.out.println(result);*/

        testlinkAPIClient.reportTestCaseResult(projectName, testplanName, testcaseName, buildName, msg, result);
    }
}

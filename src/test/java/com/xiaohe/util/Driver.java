package com.xiaohe.util;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.util.Properties;

public class Driver {
    public static RemoteWebDriver driver;

    public Driver() {}

    public static void init(String drivername) throws IOException {
        Properties properties = ReadProperties.getValue("globle.properties");
        String webdriverName = properties.getProperty("webdriver.name");
        String driverstr = properties.getProperty("driver");
        switch (drivername.toUpperCase()){
            case "CHROME":
                driver=chromeDriver(webdriverName,driverstr);
                break;
            case "FIREFOX":
                driver=firefoxDriver(webdriverName,driverstr);
                break;
            case "EDGE":
                driver=edgeDriver(webdriverName,driverstr);
                break;
        }
    }
    public static ChromeDriver chromeDriver(String drivername,String driverpath){
        System.setProperty(drivername,driverpath);
        ChromeDriver driver = new ChromeDriver();
        return driver;
    }
    public static FirefoxDriver firefoxDriver(String drivername,String driverpath){
        System.setProperty(drivername,driverpath);
        FirefoxDriver driver = new FirefoxDriver();
        return driver;
    }
    public static EdgeDriver edgeDriver(String drivername,String driverpath){
        System.setProperty(drivername,driverpath);
        EdgeDriver driver = new EdgeDriver();
        return driver;
    }
}

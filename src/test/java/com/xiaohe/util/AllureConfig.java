package com.xiaohe.util;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AllureConfig {
    public static void allureConfig(RemoteWebDriver driver,String illustrate) throws FileNotFoundException {
        File screenshotAs = driver.getScreenshotAs(OutputType.FILE);
        Allure.addAttachment(illustrate, "image/png", new FileInputStream(screenshotAs), "png");
    }
}

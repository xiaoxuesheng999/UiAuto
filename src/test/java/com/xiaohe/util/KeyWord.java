package com.xiaohe.util;


import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class KeyWord {
    static Logger logger=Logger.getLogger(KeyWord.class);
    public static WebElement elementWait(RemoteWebDriver driver,String xpath) {
        WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofMillis(5000));
        WebElement myElement = driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        return myElement;
    }

    public static void KwClick(RemoteWebDriver driver,String xpath){
        elementWait(driver,xpath).click();
    }

    public static void KwSendKey(RemoteWebDriver driver,String xpath,String msg){
        elementWait(driver,xpath).sendKeys(msg);
    }

    public static void KwClean(RemoteWebDriver driver,String xpath){
        elementWait(driver,xpath).clear();
    }

    public static void KwJsScript(RemoteWebDriver driver,String js){
        ((JavascriptExecutor)driver).executeScript(js);
    }

    public static RemoteWebDriver KwSwitchToPage(RemoteWebDriver driver,String url){
        ((JavascriptExecutor) driver).executeScript("window.open('"+url+"', '_blank');");
        String mainWindowHandle = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(mainWindowHandle)) {
                /* 切换到新标签页*/
                driver.switchTo().window(handle);
                logger.info("当前网址标题:"+driver.getTitle());
            }
        }
        return driver;
}

    public static void KwScreenShot(RemoteWebDriver driver,String xpth,String pic_num) throws IOException {
        File screenshot1 = driver.findElement(By.xpath(xpth)).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot1, new File("captcha"+pic_num+".png"));
    }
}

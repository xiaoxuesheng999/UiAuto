package com.xiaohe.page.dangdangwang;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaohe.entity.CookieFromJson;
import com.xiaohe.util.ComparePicture;
import com.xiaohe.util.Driver;
import com.xiaohe.util.KeyWord;
import org.apache.log4j.Logger;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

public class BasePage extends Driver {
    static Logger logger=Logger.getLogger(BasePage.class);

    public BasePage(){
        super();
    };
    public static void readCookiesJson() throws FileNotFoundException, UnsupportedEncodingException {

        FileInputStream cookiesInputStream = new FileInputStream("cookies.json");
        InputStreamReader reader = new InputStreamReader(cookiesInputStream, "UTF-8");
        Gson gson = new Gson();
        Type listType = new TypeToken<List<CookieFromJson>>(){}.getType();
        List<CookieFromJson> cookiesFromJson = gson.fromJson(reader, listType);
        for (CookieFromJson cookieFromJson : cookiesFromJson) {
            Cookie cookie = cookieFromJson.toSeleniumCookie();
            driver.manage().addCookie(cookie);
        }
        driver.navigate().refresh();
    }
    public static void writeCookiesJson() throws IOException, InterruptedException {

        init("chrome");

        driver.get(LoginPage.url);
        driver.manage().window().maximize();

        KeyWord.KwClick(driver,LoginPage.loginTagElement);

        KeyWord.KwSendKey(driver,LoginPage.userInputBoxElement,LoginPage.username);
        KeyWord.KwSendKey(driver,LoginPage.passInputBoxElement,LoginPage.password);

        KeyWord.KwClick(driver,LoginPage.protocolElement);

        KeyWord.KwClick(driver,LoginPage.loginButtonElement);
        Thread.sleep(2000);
        boolean flag=true;
        while (flag) {

            Object picUrl = ((JavascriptExecutor) driver).executeScript("return document.querySelector(\"#bgImg\").src;");
            RemoteWebDriver newPageDriver = KeyWord.KwSwitchToPage(driver, picUrl.toString());
            KeyWord.KwScreenShot(newPageDriver, LoginPage.verifyPic, "1");
            KeyWord.KwJsScript(newPageDriver, "window.close();");
            for (String windowHandle : driver.getWindowHandles()) {
                driver.switchTo().window(windowHandle);
            }

            Object picUrl2 = ((JavascriptExecutor) driver).executeScript("return document.querySelector(\"#simg\").src;");
            RemoteWebDriver newPageDriver2 = KeyWord.KwSwitchToPage(driver, picUrl2.toString());
            KeyWord.KwScreenShot(newPageDriver2, LoginPage.verifyPic, "2");
            KeyWord.KwJsScript(newPageDriver2, "window.close();");
            for (String windowHandle : driver.getWindowHandles()) {
                driver.switchTo().window(windowHandle);
            }

            String imagePath = "D:\\workspace\\Idea-workspace\\xiaohe\\MyUiTest\\captcha1.png";
            String imagePath2 = "D:\\workspace\\Idea-workspace\\xiaohe\\MyUiTest\\captcha2.png";
            double offset = ComparePicture.comparePicture(imagePath, imagePath2, 71);

            Actions actions = new Actions(driver);
            actions.moveToElement(KeyWord.elementWait(driver, LoginPage.sliderBtn)).clickAndHold().moveByOffset(50, 0).perform();
            Thread.sleep(1000);
            actions.moveToElement(KeyWord.elementWait(driver, LoginPage.sliderBtn)).clickAndHold().moveByOffset((int) offset, 0).release().perform();
            Thread.sleep(3000);
            try {
                KeyWord.elementWait(driver,"//*[@id=\"nickname\"]/span");
                flag=false;
            }catch (TimeoutException e){
                flag=true;
            }
        }
        Thread.sleep(2000);
        Set<Cookie> cookies = driver.manage().getCookies();

        Gson gson = new Gson();
        Type cookieSetType = new TypeToken<Set<Cookie>>(){}.getType();
        String jsonString = gson.toJson(cookies, cookieSetType);


        FileWriter writer = new FileWriter("cookies.json");
        writer.write(jsonString);
        writer.close();

        logger.info("Cookies have been written to cookies.json");

    }

//    public static void main(String[] args) throws IOException, InterruptedException {
//
//        init("chrome");
//
//        driver.get("https://product.dangdang.com/29638318.html#ddclick_http://book.dangdang.com/01.21.htm");
//        driver.manage().window().maximize();
//        readCookiesJson();
//    }

}

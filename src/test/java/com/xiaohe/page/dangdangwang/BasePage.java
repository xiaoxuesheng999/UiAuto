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
        /* 从文件中读取cookies*/
        FileInputStream cookiesInputStream = new FileInputStream("cookies.json");
        InputStreamReader reader = new InputStreamReader(cookiesInputStream, "UTF-8");
        Gson gson = new Gson();
        Type listType = new TypeToken<List<CookieFromJson>>(){}.getType();
        List<CookieFromJson> cookiesFromJson = gson.fromJson(reader, listType);


        /* 将cookies添加到浏览器中*/
        for (CookieFromJson cookieFromJson : cookiesFromJson) {
            Cookie cookie = cookieFromJson.toSeleniumCookie();
            driver.manage().addCookie(cookie);
        }

        /* 刷新页面（如果需要的话）*/
        driver.navigate().refresh();

    }
    public static void writeCookiesJson() throws IOException, InterruptedException {
      /*  读取配置初始化浏览器*/
        init("chrome");
        /*浏览器驱动访问页面*/
        driver.get(LoginPage.url);
        driver.manage().window().maximize();
        /*等待登录元素出现点击*/
        KeyWord.KwClick(driver,LoginPage.loginTagElement);
       /* 等待输入框元素出现输入*/
        KeyWord.KwSendKey(driver,LoginPage.userInputBoxElement,LoginPage.username);
        KeyWord.KwSendKey(driver,LoginPage.passInputBoxElement,LoginPage.password);
       /* 勾选条款标签*/
        KeyWord.KwClick(driver,LoginPage.protocolElement);
       /* 点击登录*/
        KeyWord.KwClick(driver,LoginPage.loginButtonElement);
        Thread.sleep(2000);
        boolean flag=true;
        while (flag) {
           /* 获取主图*/
            Object picUrl = ((JavascriptExecutor) driver).executeScript("return document.querySelector(\"#bgImg\").src;");
            RemoteWebDriver newPageDriver = KeyWord.KwSwitchToPage(driver, picUrl.toString());
            KeyWord.KwScreenShot(newPageDriver, LoginPage.verifyPic, "1");
            KeyWord.KwJsScript(newPageDriver, "window.close();");
            for (String windowHandle : driver.getWindowHandles()) {
                driver.switchTo().window(windowHandle);
            }
            /*获取模板图*/
            Object picUrl2 = ((JavascriptExecutor) driver).executeScript("return document.querySelector(\"#simg\").src;");
            RemoteWebDriver newPageDriver2 = KeyWord.KwSwitchToPage(driver, picUrl2.toString());
            KeyWord.KwScreenShot(newPageDriver2, LoginPage.verifyPic, "2");
            KeyWord.KwJsScript(newPageDriver2, "window.close();");
            for (String windowHandle : driver.getWindowHandles()) {
                driver.switchTo().window(windowHandle);
            }
            /*对比图片，获取图片偏移坐标*/
            String imagePath = "D:\\workspace\\Idea-workspace\\xiaohe\\MyUiTest\\captcha1.png";
            String imagePath2 = "D:\\workspace\\Idea-workspace\\xiaohe\\MyUiTest\\captcha2.png";
            double offset = ComparePicture.comparePicture(imagePath, imagePath2, 71);
           /* 模拟鼠标拖动*/
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
       /*  使用Gson将Cookies转换为JSON字符串*/
        Gson gson = new Gson();
        Type cookieSetType = new TypeToken<Set<Cookie>>(){}.getType();
        String jsonString = gson.toJson(cookies, cookieSetType);

        /* 将JSON字符串写入文件*/
        FileWriter writer = new FileWriter("cookies.json");
        writer.write(jsonString);
        writer.close();

        logger.info("Cookies have been written to cookies.json");

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        /*BasePage.writeCookiesJson();*/
     /* 读取配置初始化浏览器*/
        init("chrome");
        /*浏览器驱动访问页面*/
        driver.get("https://product.dangdang.com/29638318.html#ddclick_http://book.dangdang.com/01.21.htm");
        driver.manage().window().maximize();
        readCookiesJson();
    }

}

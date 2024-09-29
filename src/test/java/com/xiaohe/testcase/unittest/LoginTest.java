package com.xiaohe.testcase.unittest;
import com.xiaohe.page.dangdangwang.LoginPage;
import com.xiaohe.util.*;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;
import java.io.*;
import org.apache.log4j.Logger;


@Epic("单元测试")
@Feature("登录页面")
public class LoginTest extends Driver {

    static Logger logger=Logger.getLogger(LoginTest.class);
    LoginTest(){
        super();
    }



    @Story("初始化")
    @BeforeTest
    public static void initPage() throws IOException {
        //读取配置初始化浏览器
        init("chrome");
        //浏览器驱动访问页面
        driver.get(LoginPage.url);
        driver.manage().window().maximize();
        //等待登录元素出现点击
        KeyWord.KwClick(driver,LoginPage.loginTagElement);
        AllureConfig.allureConfig(driver,"初始化登录界面");
    }


    @Story("无密码登录")
    @Test(priority = 0)
    public static void loginWithoutUsername() throws IOException {
        //等待输入框元素出现输入
        KeyWord.KwSendKey(driver,LoginPage.userInputBoxElement,LoginPage.username);
        //勾选条款标签
        KeyWord.KwClick(driver,LoginPage.protocolElement);
        //点击登录
        KeyWord.KwClick(driver,LoginPage.loginButtonElement);
        String js="return document.querySelector("+LoginPage.errorTagElement+").textContent";
        Object o = driver.executeScript(js);
        //断言提示语
        Assert.assertEquals(o.toString(),"请输入用户名和密码 ");
        AllureConfig.allureConfig(driver,"提示:请输入用户名和密码");
        //清理输入框
        KeyWord.KwClick(driver,LoginPage.userInputClearElement);
    }
    @Story("无用户名登录")
    @Test(priority = 1)
    public static void loginWithoutPassword() throws IOException {
        //等待输入框元素出现输入
        KeyWord.KwSendKey(driver,LoginPage.passInputBoxElement,LoginPage.password);
        //点击登录
        KeyWord.KwClick(driver,LoginPage.loginButtonElement);
        String js="return document.querySelector("+LoginPage.errorTagElement+").textContent";
        //断言提示语
        Object o = driver.executeScript(js);
        Assert.assertEquals(o.toString(),"请输入用户名和密码 ");
        AllureConfig.allureConfig(driver,"提示:请输入用户名和密码");
        //清理输入框
        KeyWord.KwClick(driver,LoginPage.passwordInputClearElement);
    }
    @Story("无协议登录")
    @Test(priority = 2)
    public static void loginWithoutProtocol() throws IOException {
        //取消勾选条款标签
        KeyWord.KwClick(driver,LoginPage.protocolElement);
        //等待输入框元素出现输入
        KeyWord.KwSendKey(driver,LoginPage.userInputBoxElement,LoginPage.username);
        KeyWord.KwSendKey(driver,LoginPage.passInputBoxElement,LoginPage.password);
        //点击登录
        KeyWord.KwClick(driver,LoginPage.loginButtonElement);
        String js="return document.querySelector("+LoginPage.errorTagElement+").textContent";
        Object o = driver.executeScript(js);
        AllureConfig.allureConfig(driver,"提示请阅读并同意协议");
        //断言
        Assert.assertEquals(o.toString(),"请阅读并同意协议 ");
    }
    @Story("向下滑动")
    @Test(priority = 3)
    public static void slideDown() throws FileNotFoundException {
       KeyWord.KwJsScript(driver,"window.scrollTo(0, document.body.scrollHeight)");
        AllureConfig.allureConfig(driver,"拖动条向下滑动");
    }
    @Story("向上滑动")
    @Test(priority = 4)
    public static void slideUp() throws FileNotFoundException {
        KeyWord.KwJsScript(driver,"window.scrollTo( document.body.scrollHeight,0)");
        AllureConfig.allureConfig(driver,"拖动条向上滑动");
    }
    @Story("验证登录")
    @Test(priority = 5)
    public static void verifylogin() throws IOException, InterruptedException {
        //勾选条款标签
        KeyWord.KwClick(driver,LoginPage.protocolElement);
        //点击登录
        KeyWord.KwClick(driver,LoginPage.loginButtonElement);
        Thread.sleep(2000);
        boolean flag=true;
        while (flag) {
            //获取主图
            Object picUrl = ((JavascriptExecutor) driver).executeScript("return document.querySelector(\"#bgImg\").src;");
            RemoteWebDriver newPageDriver = KeyWord.KwSwitchToPage(driver, picUrl.toString());
            KeyWord.KwScreenShot(newPageDriver, LoginPage.verifyPic, "1");
            KeyWord.KwJsScript(newPageDriver, "window.close();");
            for (String windowHandle : driver.getWindowHandles()) {
                driver.switchTo().window(windowHandle);
            }
            //获取模板图
            Object picUrl2 = ((JavascriptExecutor) driver).executeScript("return document.querySelector(\"#simg\").src;");
            RemoteWebDriver newPageDriver2 = KeyWord.KwSwitchToPage(driver, picUrl2.toString());
            KeyWord.KwScreenShot(newPageDriver2, LoginPage.verifyPic, "2");
            KeyWord.KwJsScript(newPageDriver2, "window.close();");
            for (String windowHandle : driver.getWindowHandles()) {
                driver.switchTo().window(windowHandle);
            }
            //对比图片，获取图片偏移坐标
            String imagePath = "D:\\workspace\\Idea-workspace\\xiaohe\\MyUiTest\\captcha1.png";
            String imagePath2 = "D:\\workspace\\Idea-workspace\\xiaohe\\MyUiTest\\captcha2.png";
            double offset = ComparePicture.comparePicture(imagePath, imagePath2, 71);
            //模拟鼠标拖动
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
        AllureConfig.allureConfig(driver,"登录成功!");
    }
    @Story("关闭浏览器")
    @AfterTest
    public static void closeBrowser(){
        driver.quit();
    }
}

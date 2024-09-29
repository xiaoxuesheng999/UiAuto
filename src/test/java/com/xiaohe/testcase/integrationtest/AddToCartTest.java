package com.xiaohe.testcase.integrationtest;

import com.xiaohe.page.dangdangwang.*;
import com.xiaohe.util.KeyWord;
import com.xiaohe.util.AllureConfig;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Epic("集成测试")
@Feature("购物车添加商品")
public class AddToCartTest extends BasePage {
    static Logger logger=Logger.getLogger(AddToCartTest.class);
    AddToCartTest(){
        super();
    }
    @Story("初始化")
    @BeforeTest
    public static void initPage() throws IOException, InterruptedException {
       /* 读取配置初始化浏览器*/
        init("chrome");
       /*浏览器驱动访问页面*/
        driver.get(LoginPage.url);
        driver.manage().window().maximize();
       /* 判断项目是否存在cookies.json文件,有就直接套用登录，没有就重新登录写入cookies.json文件*/
        if(Files.exists(Paths.get("D:\\workspace\\Idea-workspace\\xiaohe\\MyUiTest\\cookies.json"))){
            BasePage.readCookiesJson();
        }else{
            BasePage.writeCookiesJson();
        }
        AllureConfig.allureConfig(driver,"魔法记忆书购买页面");
    }
    @Story("搜索书品添加购物车")
    @Test(priority = 0)
    public static void shopping() throws FileNotFoundException, InterruptedException {
        KeyWord.elementWait(driver, HomePage.searchInputBox).sendKeys("记忆术");
        AllureConfig.allureConfig(driver,"搜索:记忆书");
        KeyWord.elementWait(driver, HomePage.searchBtn).click();
        AllureConfig.allureConfig(driver,"跳转至记忆书列表");
        KeyWord.KwJsScript(driver,"window.scrollTo(0,500)");
        AllureConfig.allureConfig(driver,"向下滑动500px");
        KeyWord.KwClick(driver,MagicMemoryBookPage.addToCartBtn);
        for (String windowHandle : driver.getWindowHandles()) {
            if(!windowHandle.equals(driver.getWindowHandle())){
                driver.switchTo().window(windowHandle);
            }
        }
        AllureConfig.allureConfig(driver,"跳转购物车");
        KeyWord.KwClick(driver, ShoppingCart.deletetag);
        AllureConfig.allureConfig(driver,"删除购买书籍");
        KeyWord.KwClick(driver,ShoppingCart.deleteConfirmSure);
        Thread.sleep(3000);
        AllureConfig.allureConfig(driver,"清理数据");

    }
    @Story("清理数据,关闭浏览器")
    @AfterTest
    public static void closeBrowser()  {
        driver.quit();
    }
}

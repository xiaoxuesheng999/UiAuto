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

@Epic("���ɲ���")
@Feature("���ﳵ�����Ʒ")
public class AddToCartTest extends BasePage {
    static Logger logger=Logger.getLogger(AddToCartTest.class);
    AddToCartTest(){
        super();
    }
    @Story("��ʼ��")
    @BeforeTest
    public static void initPage() throws IOException, InterruptedException {

        init("chrome");

        driver.get(LoginPage.url);
        driver.manage().window().maximize();

        if(Files.exists(Paths.get("D:\\workspace\\Idea-workspace\\xiaohe\\MyUiTest\\cookies.json"))){
            BasePage.readCookiesJson();
        }else{
            BasePage.writeCookiesJson();
        }
        AllureConfig.allureConfig(driver,"�����鹺��ҳ��");
    }
    @Story("������Ʒ��ӹ��ﳵ")
    @Test(priority = 0)
    public static void shopping() throws FileNotFoundException, InterruptedException {
        KeyWord.elementWait(driver, HomePage.searchInputBox).sendKeys("������");
        AllureConfig.allureConfig(driver,"����:������");
        KeyWord.elementWait(driver, HomePage.searchBtn).click();
        AllureConfig.allureConfig(driver,"��ת���������б�");
        KeyWord.KwJsScript(driver,"window.scrollTo(0,500)");
        AllureConfig.allureConfig(driver,"���»���500px");
        KeyWord.KwClick(driver,MagicMemoryBookPage.addToCartBtn);
        for (String windowHandle : driver.getWindowHandles()) {
            if(!windowHandle.equals(driver.getWindowHandle())){
                driver.switchTo().window(windowHandle);
            }
        }
        AllureConfig.allureConfig(driver,"��ת���ﳵ");
        KeyWord.KwClick(driver, ShoppingCart.deletetag);
        AllureConfig.allureConfig(driver,"ɾ�������鼮");
        KeyWord.KwClick(driver,ShoppingCart.deleteConfirmSure);
        Thread.sleep(3000);
        AllureConfig.allureConfig(driver,"��������");

    }
    @Story("��������,�ر������")
    @AfterTest
    public static void closeBrowser()  {
        driver.quit();
    }
}

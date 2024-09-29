package com.xiaohe;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Temp {
    public static void main(String[] args) {
        // 设置系统属性以指定chromedriver的路径
        // 请确保这里的路径与你的chromedriver实际路径一致
        System.setProperty("webdriver.chrome.driver", "D:\\Chrome\\chromedriver-win64\\chromedriver.exe");

        // 初始化WebDriver对象
        WebDriver driver = new ChromeDriver();

//        try {
            // 打开第一个页面
            driver.get("https://www.baidu.com");

            // 等待页面加载（如果需要）
            // ...

            // 使用JavaScript打开一个新标签页
            JavascriptExecutor js = (JavascriptExecutor) driver;
            // 这里以Google为例打开新标签页
            js.executeScript("window.open('https://www.dangdang.com', '_blank');");
            js.executeScript("window.open('https://www.jd.com', '_blank');");

            // 注意：Selenium默认不会切换到新打开的标签页
            // 如果你需要在新标签页中执行操作，你可能需要使用某种方式来切换标签页
            // Selenium没有直接切换标签页的API，但你可以通过窗口句柄（window handles）来实现

            // 获取所有窗口句柄
            String mainWindowHandle = driver.getWindowHandle();
            for (String handle : driver.getWindowHandles()) {
                driver.switchTo().window(handle);
                System.out.println(handle);
                System.out.println(driver.getTitle());
//                if (!handle.equals(mainWindowHandle)) {
//                    // 切换到新标签页
//                    driver.switchTo().window(handle);
//                    // 在这里对新标签页进行操作
//                    // 例如，你可以检查URL是否是预期的URL
//                    System.out.println(driver.getCurrentUrl());
//
//
//                    // 操作完成后，可以切换回原来的标签页
//                    driver.switchTo().window(mainWindowHandle);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
        }
    }
}

package com.rambo.erian;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;


public class Fetcher {
    private WebDriver webDriver;
    private Selenium selenium;

    // 第三方的登录工具的用户名，我这里用的是新浪微博
    private String username = "freerambo";
    // 第三方的登录工具的密码，我这里用的是新浪微博
    private String password = "zybwan";

    public Fetcher() throws Exception {
        // 设置google浏览器的驱动位置
        System.setProperty("webdriver.chrome.driver" , "E:/it_jar_file/chromedriver_win32/chromedriver.exe");
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().pageLoadTimeout(12000, TimeUnit.SECONDS);
        selenium = new WebDriverBackedSelenium(webDriver, "http://www.iteye.com");
        selenium.setTimeout("120000");
    }

    public void fetch1() throws Exception {
        // 这是入口网址
        webDriver.get("http://www.iteye.com/login");
        waitForPageToLoad();

        // 选择第三方登录工具
        selenium.click("css=div.third a[href='/auth/weibo']");
        waitForPageToLoad();

        // 输入用户名
        webDriver.findElement(By.id("userId")).sendKeys(username);
        // 输入密码
        webDriver.findElement(By.id("passwd")).sendKeys(password);

        Thread.sleep(2000);

        // 点击登录按钮
        selenium.click("css=p.oauth_formbtn a[node-type='submit']");
    }
    
    public void fetch() throws Exception {
        // 这是入口网址
        webDriver.get("http://www.iteye.com/login");
//        waitForPageToLoad();

        // 选择第三方登录工具
//        selenium.click("css=div.third a[href='/auth/weibo']");
//        waitForPageToLoad();

        // 输入用户名
        webDriver.findElement(By.id("user_name")).sendKeys(username);
        // 输入密码
        webDriver.findElement(By.id("password")).sendKeys(password);

        Thread.sleep(2000);

        // 点击登录按钮
        selenium.click("id=button");
    }

    private void waitForPageToLoad() {
        selenium.waitForPageToLoad("120000");
    }
}
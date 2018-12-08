/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fb;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 *
 * @author PC
 */
public class FB {

    public static ChromeDriver chromeDriver;
    static Thread a;
    private boolean status = true;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    double timesleep = 1;

    public void runCMT() {
        a = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (status) {
                            autoCMT();
                        } else {
                            Thread.sleep(500);
                        }
                    }

                } catch (InterruptedException ex) {
                    closeChrome();
                    a.stop();
                }
            }
        };
    }

    public void closeChrome() {
        chromeDriver.close();
    }

    public void setStart(String link, double time) {
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--disable-notifications");
        
        String user = System.getenv("USERNAME");
        option.addArguments("user-data-dir=C:/" + user + "/user_name/AppData/Local/Google/Chrome/User Data");
        chromeDriver = new ChromeDriver(option);
        chromeDriver.get(link);
        WebElement html = chromeDriver.findElement(By.tagName("html"));
        html.sendKeys(Keys.chord(Keys.CONTROL, Keys.ADD));
        html.sendKeys(Keys.chord(Keys.CONTROL, "0"));
        status = true;
        timesleep = time;
        runCMT();
        a.start();
    }

    synchronized void autoCMT() throws InterruptedException {
        if (!chromeDriver.findElementsByClassName("_6--c").isEmpty()) {
            List<WebElement> mes = chromeDriver.findElementsByClassName("_6--c");
            System.out.println(mes.size());
            for (int i = 1; i < mes.size(); i++) {
                mes.get(i).click();
            }
        }
        List<WebElement> e = chromeDriver.findElementsByClassName("_2r-r");
        while (e.size() == 0) {
            e = chromeDriver.findElementsByClassName("_2r-r");
        }

        System.out.println(e.size());
        e.get(e.size() - 1).click();
        List<WebElement> listIcon = chromeDriver.findElementsByClassName("_5r8i");
        while (listIcon.size() == 0) {

            listIcon = chromeDriver.findElementsByClassName("_5r8i");
            Thread.sleep(500);
        }

        Random r = new Random();
        System.out.println(listIcon.size() - 1);
        listIcon.get(r.nextInt(listIcon.size() - 1)).click();

        Thread.sleep((long) (timesleep * 60000));

    }

}

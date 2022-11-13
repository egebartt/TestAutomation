package tests;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.*;

import org.openqa.selenium.interactions.Actions;
import pages.TrendyolPage;
import utilities.ConfigReader;
import utilities.Driver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

// Ege Bartu Teker

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TrendyolTest {
    TrendyolPage trendyolPage=new TrendyolPage();
    Actions actions = new Actions(Driver.getDriver());
    JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
    static String randomSize, basketSize;
    static String [] orginalDiscountedPrices, basketPrice;

    @Test // Network sayfasına gidilir.
    public void test01(){
        Driver.getDriver().get(ConfigReader.getProperty("trendyolUrl"));
    }

    @Test // Cookies kapatma işlemi yapılır.
    public void test02(){trendyolPage.closeCookies.click();}

    @Test // Network url’nin geldiği kontrol edilir.
    public void test03(){
        String expectedUrl="https://www.trendyol.com/";
        String actualURL = Driver.getDriver().getCurrentUrl();
        Assert.assertEquals(expectedUrl,actualURL);
    }

    @Test // Arama sekmesinde “ceket” yazdırılır ve arama yaptırılır.
    public void test04() throws InterruptedException {
        trendyolPage.searchBar.sendKeys("ceket" + Keys.ENTER);
        actions.click().build().perform();
        Thread.sleep(Duration.ofSeconds(1));
    }

    @Test // Ürün listeleme sayfasında daha fazla göster butonuna kadar scroll yapılır.
    public void test05() throws InterruptedException {
        js.executeScript("window.scroll(0,3000)");
        Thread.sleep(Duration.ofSeconds(1));
    }

    @Test // 2.sayfaya geçildiği kontrol edilir. (url değişiyor)
    public void test06(){
        String expectedPage2="https://www.trendyol.com/sr?q=ceket&qt=ceket&st=ceket&os=1&pi=2";
        String actualPage = Driver.getDriver().getCurrentUrl();
        Assert.assertEquals(expectedPage2,actualPage);
    }

    @Test // Ürün listeleme sayfasında İndirimli ilk ürüne hover edilirek rastgele beden seçimi yapılır.
    public void test07() throws InterruptedException {
        actions.moveToElement(trendyolPage.HoverfirstProduct).click(trendyolPage.HoverfirstProduct).build().perform();

        ArrayList<String> tabs2 = new ArrayList<String> (Driver.getDriver().getWindowHandles());
        Driver.getDriver().switchTo().window(tabs2.get(0));
        Driver.getDriver().close();
        Driver.getDriver().switchTo().window(tabs2.get(1));

        Random rand = new Random();
        int randomProduct = rand.nextInt(trendyolPage.productSize.size());
        randomSize = trendyolPage.productSize.get(randomProduct).getText();
        trendyolPage.productSize.get(randomProduct).click();

        orginalDiscountedPrices = trendyolPage.productPrice.getText().replace("TL","").replace(",",".").split(" ");
    }

    @Test // Ürün Sepete eklenir
    public void test08() throws InterruptedException {
        trendyolPage.addToBasket.click();
        Thread.sleep(Duration.ofSeconds(1));
    }

    @Test // Sağ alt köşede açılan Sepete git butonu seçilir.
    public void test09(){
        trendyolPage.goToBasket.click();
    }

    @Test // Ürüne ait beden ve fiyat bilgisinin sepette doğru geldiği kontrol edilir.
    public void test10()throws InterruptedException {
        basketSize = trendyolPage.productSizeAtBasket.getText();
        //System.out.println("BasketSize: "+BasketSize);
        //System.out.println("RandomSize: "+randomSize);
        Assert.assertTrue(basketSize.contains(randomSize));

        basketPrice = trendyolPage.productPriceAtBasket.getText().replace(" TL", "").replace(",",".").split("\n");
        //System.out.println("BasketPrice: "+Arrays.toString(BasketPrice));
        //System.out.println("BasketPrice: "+Arrays.toString(orginalDiscountedPrices));

        Assert.assertTrue(basketPrice[0].equalsIgnoreCase(orginalDiscountedPrices[0]));
        Assert.assertTrue(basketPrice[1].equalsIgnoreCase(orginalDiscountedPrices[1]));
        Thread.sleep(Duration.ofSeconds(1));
    }

    @Test // Sepete eklenen ürünün eski fiyatının indirimli fiyatından büyük olduğu kontrol ettirilir.
    public void test11(){
        Assert.assertTrue(Double.parseDouble(basketPrice[0])>Double.parseDouble(basketPrice[1]));
    }

    @Test // Devam et butonuna tıklanır.
    public void test12(){
        trendyolPage.confirmBasket.click();
    }

    @Test // Kullanıcı bilgileri csv formatından çekilerek doldurulur (E-posta - şifre csv uzantılı dosyadan okunması gerekmektedir.)
    public void test13() throws IOException {
        String line1 = Files.readAllLines(Paths.get("src/test/data.csv")).get(0);
        String line2 = Files.readAllLines(Paths.get("src/test/data.csv")).get(1);
        trendyolPage.emailBox.sendKeys(line1);
        trendyolPage.passwordBox.sendKeys(line2);
    }

    @Test // Giriş yap butonunun geldiği kontrol edilir.
    public void test14(){
        Assert.assertTrue(trendyolPage.loginButtonVisible.isDisplayed());
    }

    @Test // Network logosuna tıklanır.
    public void test15(){
        trendyolPage.networkLogo.click();
    }

    @Test // Anasayfa üzerinde çanta logosuna tıklanarak Sepetim ekranın sağ tarafında açılır.
    public void test16(){
        actions.keyDown(Keys.CONTROL).moveToElement(trendyolPage.myBasket).click().build().perform();

        ArrayList<String> tabs = new ArrayList<String>(Driver.getDriver().getWindowHandles());
        Driver.getDriver().switchTo().window(tabs.get(0));
        Driver.getDriver().close();
        Driver.getDriver().switchTo().window(tabs.get(1));
    }

    @Test // // Ürün sepetten çıkarılarak sepetin boş olduğu kontrol edilir.
    public void test17(){
        trendyolPage.deleteProduct.click();
        trendyolPage.confirmToDelete.click();
    }

}

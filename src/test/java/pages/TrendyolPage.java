package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;

public class TrendyolPage {
    public TrendyolPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(xpath="//div[@title='Kapat']") // Cookie kapatma
    public WebElement closeCookies;

    @FindBy(xpath="//input[@data-testid='suggestion']") // Arama kutusu
    public WebElement searchBar;

    @FindBy(xpath="(//div[@class=\"prc-cntnr lowest-price discountedPriceBox\"])[1]") //ilk ürüne tıklama
    public WebElement HoverfirstProduct;

    @FindBy(xpath="//div[@class='selected sp-itm' or @class='sp-itm']") // Ürün beden ölçüsü
    public List<WebElement> productSize;

    @FindBy(xpath="//div[@class='featured-prices']") // Ürünün orjinal ve indirimli fiyatı
    public WebElement productPrice;

    @FindBy(xpath="//button[@class='add-to-basket']") // Sepete ekleme
    public WebElement addToBasket;

    @FindBy(xpath="//a[@class='link account-basket']") // Sepete gitme
    public WebElement goToBasket;

    @FindBy(xpath="//p[text()='Beden']") // Sepetteki ürünün Beden ölçüsü
    public WebElement productSizeAtBasket;

    @FindBy(xpath="//div[@class='pb-basket-item-price']") // Sepetteki ürünün fiyatı
    public WebElement productPriceAtBasket;

    @FindBy(xpath="//span[text()='Sepeti Onayla']") // Sepeti onaylama
    public WebElement confirmBasket;

    @FindBy(id="login-email") // Kullanıcı email kutusu
    public WebElement emailBox;

    @FindBy(id="login-password-input") // Kullanıcı şifre kutusu
    public WebElement passwordBox;

    @FindBy(xpath="//button[@type='submit']") // giriş yap butonunun geldiğinin kontrolü
    public WebElement loginButtonVisible;

    @FindBy(xpath="//img[@src='https://cdn.dsmcdn.com/web/logo/ty-web.svg']") // sitenin logosu
    public WebElement networkLogo;

    @FindBy(xpath="//p[@class=\"link-text\" and text()='Sepetim']") // Sepetim butonu
    public WebElement myBasket;

    @FindBy(xpath="//i[@class='i-trash']") // Ürün silme
    public WebElement deleteProduct;

    @FindBy(xpath="//button[text()='Sil']") // Ürünün silme işleminin onayı
    public WebElement confirmToDelete;


}

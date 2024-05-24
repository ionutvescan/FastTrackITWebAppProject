package PageReusables;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "svg[data-icon='sign-in-alt']")
    private WebElement goToLogin;

    @FindBy(css = "svg[data-icon='sign-out-alt']")
    private WebElement logout;

    @FindBy(id = "user-name")
    private WebElement userNameField;

    @FindBy(id = "password")
    private WebElement pwdField;

    @FindBy(css = ".btn-primary")
    private WebElement loginButton;

    @FindBy(linkText = "dino")
    private WebElement accountName;

    @FindBy(xpath = "//span[contains(text(), 'Hello guest!')]")
    private WebElement greetMessage;

    @FindBy(css = ".shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(css = "svg[data-icon='shopping-cart']")
    private WebElement shoppingCart;

    @FindBy(css = "svg[data-icon='heart']")
    private WebElement wishlist;

    @FindBy(css = "svg[data-icon='shopping-bag']")
    private WebElement shoppingBag;

    public WebElement getGoToLogin(){
        return goToLogin;
    }

    public WebElement getLogout(){
        return logout;
    }

    public WebElement getUserNameField(){
        return userNameField;
    }

    public WebElement getPwdField(){
        return pwdField;
    }

    public WebElement getLoginButton(){
        return loginButton;
    }

    public WebElement getAccount(){
        return accountName;
    }

    public WebElement getGreetMessage(){
        return greetMessage;
    }

    public WebElement getShoppingCart(){
        return shoppingCart;
    }

    public void waitForElementToAppear(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public boolean numbersOfItemAdded(String number){
        String actualNumber = cartBadge.getText().trim();
        if(actualNumber.equals(number)){
            return true;
        }
        return false;
    }

    public boolean productNameInShoppingCart(String product){
        shoppingCart.click();
        String productName = driver.findElement(By.linkText(product)).getText();
        if(productName.equals(product))
            return true;
        return false;
    }

    public boolean productNameInWishlist(String product){
        wishlist.click();
        String productName = driver.findElement(By.className("card-link")).getText();
        if(productName.equals(product))
            return true;
        return false;
    }

    public void backToHomePage(){
        shoppingBag.click();
    }
}

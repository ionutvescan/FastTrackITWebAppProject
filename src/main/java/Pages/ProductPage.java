package Pages;

import PageReusables.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class ProductPage extends BasePage {

    public WebDriverWait wait;
    public ProductPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @FindBy(css = "svg[data-icon='cart-plus']")
    private WebElement cartButton;

    @FindBy(css = "svg[class='svg-inline--fa fa-heart fa-w-16 fa-3x ']")
    private WebElement wishlist;

    @FindBy(css = "small[class='text-muted']")
    private WebElement productName;

    @FindBy(css = "div[class='col col-lg-2 text-center col'] p")
    private WebElement productPrice;

    public void addToCart(){
        cartButton.click();
    }

    public void addToWishlist(){
        waitForElementToAppear(wishlist);
        wishlist.click();
    }

    public boolean verifyNumbersOfItemAdded(String number){
        return numbersOfItemAdded(number);
    }

    public boolean verifyProductNameInShoppingCart(String product){
        return productNameInShoppingCart(product);
    }

    public boolean verifyProductNameInWishlist(String product){
        return productNameInWishlist(product);
    }

    public void returnToHomePage(){
        backToHomePage();
    }

    public String getProductName(){
        return productName.getText();
    }

    public String getProductPrice(){
        return productPrice.getText().split(" ")[0].trim();
    }

    public void goToShoppingCart(){
        getShoppingCart().click();
    }
}

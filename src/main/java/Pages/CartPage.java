package Pages;

import PageReusables.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage extends BasePage {

    public WebDriverWait wait;
    public CartPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @FindBy(xpath = "//div[@class='row'] / div[1] / following-sibling::div[1] /div")
    private WebElement unitPrice;

    @FindBy(xpath = "//div[@class='row'] / div[1] / following-sibling::div[2] /div")
    private WebElement totalPrice;

    @FindBy(css = "svg[class='svg-inline--fa fa-plus-circle fa-w-16 ']")
    private WebElement incrementQuantity;

    @FindBy(css = "svg[data-icon='minus-circle']")
    private WebElement decrementQuantity;

    @FindBy(id = "item_1_title_link")
    private WebElement productNameLink;

    @FindBy(css = "svg[data-icon='trash']")
    private WebElement bin;

    @FindBy(css = ".text-center.container")
    private WebElement noProductMessage;

    @FindBy(css = ".btn.btn-success")
    private WebElement checkoutButton;


    public String getUnitPrice(){
        return unitPrice.getText().trim();
    }

    public String getTotalPrice(){
        return totalPrice.getText().trim();
    }

    public void incrementProductQuantity(){
        incrementQuantity.click();
    }

    public void decrementProductQuantity(){
        waitForElementToAppear(decrementQuantity);
        decrementQuantity.click();
    }

    public void goBackToProduct(){
        productNameLink.click();
    }

    public void removeProduct(){
        bin.click();
    }

    public String friendlyMessage(){
        return noProductMessage.getText();
    }

    public void goToCheckoutPage(){
        checkoutButton.click();
    }

}

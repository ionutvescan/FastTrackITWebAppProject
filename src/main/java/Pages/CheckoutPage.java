package Pages;

import PageReusables.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage extends BasePage {

    public WebDriverWait wait;
    public CheckoutPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @FindBy(id = "first-name")
    private WebElement firstNameField;

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    @FindBy(id = "address")
    private WebElement addressField;

    @FindBy(css = ".btn.btn-success")
    private WebElement continueCheckoutButton;

    @FindBy(xpath = "//div[@class='col']/div[2][contains(text(), 'Cash on delivery')]")
    private WebElement paymentInfo;

    @FindBy(xpath = "//div[@class='col']/div[4][contains(text(), 'CHOO CHOO DELIVERY!')]")
    private WebElement shippingInfo;

    @FindBy(css = ".btn.btn-success")
    private WebElement completeOrderButton;

    @FindBy(xpath = "//tr[@class='amount-total'] / td[2]")
    private WebElement totalAmount;

    @FindBy(css = ".text-center")
    private WebElement orderCompleteMessage;

    @FindBy(css = ".btn.btn-success")
    private WebElement continueShoppingButton;

    @FindBy(css = ".btn.btn-danger")
    private WebElement returnToCartPageButton;

    @FindBy(css = ".error")
    private WebElement errorMessage;

    public void provideInfo(String firstName, String lastName, String address){
        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        addressField.sendKeys(address);
        continueCheckoutButton.click();
    }

    public String getPaymentInfo(){
        return paymentInfo.getText();
    }

    public String getShippingInfo(){
        return shippingInfo.getText();
    }

    public void goBackToCartPage(){
        returnToCartPageButton.click();
    }

    public String getTotalPrice(){
       return totalAmount.getText().trim();
    }

    public String getOrderMessage(){
        return errorMessage.getText();
    }

    public void completeOrder(){
        completeOrderButton.click();
    }

    public String getCompleteOrderSuccessfulMessage(){
        return orderCompleteMessage.getText();
    }

    public void continueShopping(){
        continueShoppingButton.click();
    }

}

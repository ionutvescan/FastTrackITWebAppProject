package Pages;

import PageReusables.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class HomePage extends BasePage {
    public WebDriverWait wait;
    public HomePage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @FindBy(id = "input-search")
    private WebElement searchField;

    @FindBy(css = ".btn.btn-light.btn-sm")
    private WebElement searchButton;

    @FindBy(className = "card-link")
    private List<WebElement> productNames;

    @FindBy(css = ".sort-products-select.form-control.form-control-sm")
    private WebElement sortingProducts;

    @FindBy(css = ".card-text span")
    private List<WebElement> sortedPrices;

    /*@FindBy(xpath = "//div[@class='card']//a[contains(text(), 'Incredible Concrete Hat')]/parent::div/parent::div//button[@class='btn btn-link'] / following-sibling :: button[1]")
    private List<WebElement> addToCartIcon;
    @FindBy(xpath = "//div[@class='card']//a[contains(text(), 'Incredible Concrete Hat')]/parent::div/parent::div//button[@class='btn btn-link']")
    private List<WebElement> addToWishlistIcon;*/

    @FindBy(tagName = "a")
    private List<WebElement> links;

    public void logIntoApplication(){
        getGoToLogin().click();
        waitForElementToAppear(getUserNameField());
        getUserNameField().sendKeys("dino");
        getPwdField().sendKeys("choochoo");
        getLoginButton().click();
    }

    public String getAccountName(){
        return getAccount().getText();
    }

    public void logOutOfApplication(){
        getLogout().click();
    }

    public String getGreetingMessage(){
        return getGreetMessage().getText();
    }

    public void searchProduct(String productName){
        searchField.sendKeys(productName);
        searchButton.click();
    }

    public Boolean getProductName(String productName){
        List<String> products = productNames.stream().map(WebElement::getText)
                .filter(text -> text.toLowerCase().contains(productName)).toList();
        for(String product : products){
            if(product.toLowerCase().contains(productName)){
                return true;
            }
        }
        return false;
    }

    List<String> initialList;

    Select select = new Select(sortingProducts);

    public void selectProductBy(String text){
        initialList = productNames.stream().map(WebElement::getText).toList();
        select.selectByVisibleText(text);
    }

    public boolean isSortedByNameAsc(){
        List<String> filteredList = productNames.stream().map(WebElement::getText).toList();
        if(filteredList.equals(initialList)){
            return true;
        }
        return false;
    }

    public boolean isSortedByNameDesc(){
        List<String> filteredList = productNames.stream().map(WebElement::getText).toList();
        int count = initialList.size() - 1;
        for(int i = 0; i < filteredList.size(); i++){
            if(!filteredList.get(i).equals(initialList.get(count))){
                return false;
            }
            count--;
        }
        return true;
    }

    public boolean isSortedByPriceAsc(){
        List<Float> prices = sortedPrices.stream().map(element -> Float.parseFloat(element.getText())).toList();
        for(int i = 1; i < prices.size(); i++){
            if(prices.get(i) < prices.get(i-1))
                return false;
        }
        return true;
    }

    public boolean isSortedByPriceDesc(){
        List<Float> prices = sortedPrices.stream().map(element -> Float.parseFloat(element.getText())).toList();
        for(int i = 1; i < prices.size(); i++){
            if(prices.get(i) > prices.get(i-1))
                return false;
        }
        return true;
    }

    public void addToCart(String product){
        productNames.stream().filter(x->x.getText().equals(product)).findFirst().ifPresent(x->{
            WebElement cartButton = x.findElement(By.xpath("../..//button[@class='btn btn-link']"));
            cartButton.click();
        });
    }

    public void addToWishlist(String product){
        productNames.stream().filter(x->x.getText().equals(product)).findFirst().ifPresent(x->{
            WebElement wishlistButton = x.findElement(By.xpath("../..//button[@class='btn btn-link']/following-sibling :: button[1]"));
            wishlistButton.click();
        });
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

    public void verifyBrokenLinks() throws IOException {
        SoftAssert softAssert = new SoftAssert();
        for(WebElement link : links){
            String url = link.getAttribute("href");
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();
            int responseCode = connection.getResponseCode();
            softAssert.assertTrue(responseCode < 400,
                    "Link " + link.getText() + " has response code " + responseCode + " : ");
        }
        softAssert.assertAll();
    }

    public void selectProduct(String product){
        WebElement productSelected = productNames.stream().filter(x->x.getText().contains(product)).findFirst().orElse(null);
        if(productSelected != null){
            productSelected.click();
        }
    }
}


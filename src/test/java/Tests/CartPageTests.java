package Tests;

import Pages.CartPage;
import Pages.HomePage;
import Pages.ProductPage;
import TestReusables.Hooks;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class CartPageTests extends Hooks {
    public HomePage homePage;
    public ProductPage productPage;
    public CartPage cartPage;

    @BeforeMethod
    public void setupPageObject() {
        homePage = new HomePage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
    }

    @Test (dataProvider = "selectProductData")
    public void verifyProductPrice(HashMap<String,String> productName){
        homePage.selectProduct(productName.get("productName"));
        productPage.addToCart();
        productPage.goToShoppingCart();
        String actualPrice = cartPage.getUnitPrice();
        Assert.assertEquals(actualPrice, "$15.9");
    }

    @Test (dataProvider = "selectProductData")
    public void increaseProductQuantity(HashMap<String,String> productName){
        homePage.selectProduct(productName.get("productName"));
        productPage.addToCart();
        productPage.goToShoppingCart();
        cartPage.incrementProductQuantity();
        String actualTotalPrice = cartPage.getTotalPrice();
        Assert.assertEquals(actualTotalPrice, "$31.98");
    }

    @Test (dataProvider = "selectProductData")
    public void returnToProductPage(HashMap<String,String> productName){
        homePage.selectProduct(productName.get("productName"));
        productPage.addToCart();
        productPage.goToShoppingCart();
        cartPage.goBackToProduct();
        String actualProductName = productPage.getProductName();
        Assert.assertEquals(actualProductName,productName.get("productName"));
    }

    @Test (dataProvider = "selectProductData")
    public void removeProduct(HashMap<String,String> productName){
        homePage.selectProduct(productName.get("productName"));
        productPage.addToCart();
        productPage.goToShoppingCart();
        cartPage.removeProduct();
        String message = cartPage.friendlyMessage();
        Assert.assertEquals(message, "How about adding some products in your cart?");
    }

    @Test (dataProvider = "selectProductData")
    public void removeProductThroughProductQuantity(HashMap<String,String> productName){
        homePage.selectProduct(productName.get("productName"));
        productPage.addToCart();
        productPage.goToShoppingCart();
        cartPage.decrementProductQuantity();
        String message = cartPage.friendlyMessage();
        Assert.assertEquals(message, "How about adding some products in your cart?");
    }

    @DataProvider(name = "selectProductData")
    public Object[][] getData() throws IOException {
        List<HashMap<String,String>> data = getJsonData(
                "C:\\Users\\Ionut\\Desktop\\FastTrackITWebApp\\src\\test\\java\\TestData\\selectProduct.json");
        return new Object[][] {{data.get(0)}};
    }

}

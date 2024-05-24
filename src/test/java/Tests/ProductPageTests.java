package Tests;

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

public class ProductPageTests extends Hooks {
    public HomePage homePage;
    public ProductPage productPage;

    @BeforeMethod
    public void setupPageObject() {
        homePage = new HomePage(driver);
        productPage = new ProductPage(driver);
    }

    @Test (dataProvider = "selectProductData")
    public void addProductToCart(HashMap<String,String> productName){
        homePage.selectProduct(productName.get("productName"));
        productPage.addToCart();
        boolean isAdded = productPage.verifyNumbersOfItemAdded("1");
        Assert.assertTrue(isAdded);
        boolean hasProductName = productPage.verifyProductNameInShoppingCart(productName.get("productName"));
        Assert.assertTrue(hasProductName);
    }

    @Test (dataProvider = "selectProductData")
    public void addProductToWishlist(HashMap<String,String> productName){
        homePage.selectProduct(productName.get("productName"));
        productPage.addToWishlist();
        boolean isAdded = productPage.verifyNumbersOfItemAdded("1");
        Assert.assertTrue(isAdded);
        boolean hasProductName = productPage.verifyProductNameInWishlist(productName.get("productName"));
        Assert.assertTrue(hasProductName);
    }

    @Test (dataProvider = "selectProductData")
    public void verifyPrice(HashMap<String,String> productName){
        homePage.selectProduct(productName.get("productName"));
        String actualPrice = productPage.getProductPrice();
        Assert.assertEquals(actualPrice, productName.get("productPrice"));
    }

    @Test (dataProvider = "selectProductData")
    public void verifyProductName(HashMap<String,String> productNames){
        homePage.selectProduct(productNames.get("productName"));
        String productName = productPage.getProductName();
        Assert.assertEquals(productName, productNames.get("productName"));
    }

    @Test (dataProvider = "selectProductData")
    public void goToHomePage(HashMap<String,String> productName){
        homePage.selectProduct(productName.get("productName"));
        productPage.returnToHomePage();
    }

    @DataProvider(name = "selectProductData")
    public Object[][] getData() throws IOException {
        List<HashMap<String,String>> data = getJsonData(
                "C:\\Users\\Ionut\\Desktop\\FastTrackITWebApp\\src\\test\\java\\TestData\\selectProduct.json");
        return new Object[][] {{data.get(0)}};
    }
}

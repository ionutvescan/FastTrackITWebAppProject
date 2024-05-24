package Tests;

import Pages.HomePage;
import TestReusables.Hooks;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class HomePageTests extends Hooks {

    public HomePage homePage;

    @BeforeMethod
    public void setupPageObject() {
        homePage = new HomePage(driver);
    }

    @Test (dataProvider = "searchProductData")
    public void searchForProduct(HashMap<String,String> productName){
        homePage.searchProduct(productName.get("productName"));
        boolean hasProductName = homePage.getProductName(productName.get("productName"));
        Assert.assertTrue(hasProductName);
    }

    @Test
    public void sortByNameAsc(){
        homePage.selectProductBy("Sort by name (A to Z)");
        boolean isSorted = homePage.isSortedByNameAsc();
        Assert.assertTrue(isSorted);
    }

    @Test
    public void sortByNameDesc(){
        homePage.selectProductBy("Sort by name (Z to A)");
        boolean isSorted = homePage.isSortedByNameDesc();
        Assert.assertTrue(isSorted);
    }

    @Test
    public void sortByPriceAsc(){
        homePage.selectProductBy("Sort by price (low to high)");
        boolean isSorted = homePage.isSortedByPriceAsc();
        Assert.assertTrue(isSorted);
    }

    @Test
    public void sortByPriceDesc(){
        homePage.selectProductBy("Sort by price (high to low)");
        boolean isSorted = homePage.isSortedByPriceDesc();
        Assert.assertTrue(isSorted);
    }

    @Test (dataProvider = "addProductData")
    public void addProductToCart(HashMap<String,String> productName){
        homePage.addToCart(productName.get("productName"));
        boolean isAdded = homePage.verifyNumbersOfItemAdded("1");
        Assert.assertTrue(isAdded);
        boolean hasProductName = homePage.verifyProductNameInShoppingCart(productName.get("productName"));
        Assert.assertTrue(hasProductName);
    }

    @Test (dataProvider = "addProductData")
    public void addProductToWishlist(HashMap<String,String> productName){
        homePage.addToWishlist(productName.get("productName"));
        boolean isAdded = homePage.verifyNumbersOfItemAdded("1");
        Assert.assertTrue(isAdded);
        boolean hasProductName = homePage.verifyProductNameInWishlist(productName.get("productName"));
        Assert.assertTrue(hasProductName);
    }

    @Test
    public void login(){
        homePage.logIntoApplication();
        String accountName = homePage.getAccountName();
        Assert.assertEquals(accountName, "dino");
    }

    @Test
    public void logout(){
        homePage.logIntoApplication();
        homePage.logOutOfApplication();
        String greetMessage = homePage.getGreetingMessage();
        Assert.assertEquals(greetMessage, "Hello guest!");
    }

    @Test
    public void verifyLinks() throws IOException {
        homePage.verifyBrokenLinks();
    }

    @DataProvider(name = "searchProductData")
    public Object[][] getData1() throws IOException {
        List<HashMap<String,String>> data = getJsonData(
                "C:\\Users\\Ionut\\Desktop\\FastTrackITWebApp\\src\\test\\java\\TestData\\searchProduct.json");
        return new Object[][] {{data.get(0)}};
    }

    @DataProvider(name = "addProductData")
    public Object[][] getData2() throws IOException {
        List<HashMap<String,String>> data = getJsonData(
                "C:\\Users\\Ionut\\Desktop\\FastTrackITWebApp\\src\\test\\java\\TestData\\addProduct.json");
        return new Object[][] {{data.get(0)}};
    }
}

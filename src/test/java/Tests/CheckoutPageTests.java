package Tests;

import Pages.CartPage;
import Pages.CheckoutPage;
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

public class CheckoutPageTests extends Hooks {
    public HomePage homePage;
    public ProductPage productPage;
    public CartPage cartPage;
    public CheckoutPage checkoutPage;

    @BeforeMethod
    public void setupPageObject() {
        homePage = new HomePage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @Test (dataProvider = "provideInfoData")
    public void verifyDeliveryInfoSelected(HashMap<String,String> productInfo){
        homePage.selectProduct(productInfo.get("productName"));
        productPage.addToCart();
        productPage.goToShoppingCart();
        cartPage.goToCheckoutPage();
        checkoutPage.provideInfo(productInfo.get("firstName"), productInfo.get("lastName"), productInfo.get("address"));
        String shippingInfo = checkoutPage.getShippingInfo();
        Assert.assertEquals(shippingInfo, productInfo.get("shippingInfo"));
    }

    @Test (dataProvider = "provideInfoData")
    public void verifyPaymentInfoSelected(HashMap<String,String> productInfo){
        homePage.selectProduct(productInfo.get("productName"));
        productPage.addToCart();
        productPage.goToShoppingCart();
        cartPage.goToCheckoutPage();
        checkoutPage.provideInfo(productInfo.get("firstName"), productInfo.get("lastName"), productInfo.get("address"));
        String paymentInfo = checkoutPage.getPaymentInfo();
        Assert.assertEquals(paymentInfo, productInfo.get("paymentInfo"));
    }

    @Test (dataProvider = "provideInfoData")
    public void returnToCartPageFromCheckoutInfoSection(HashMap<String,String> productInfo){
        homePage.selectProduct(productInfo.get("productName"));
        productPage.addToCart();
        productPage.goToShoppingCart();
        cartPage.goToCheckoutPage();
        checkoutPage.goBackToCartPage();
    }

    @Test (dataProvider = "provideInfoData")
    public void returnToCartPageFromCheckoutSummarySection(HashMap<String,String> productInfo){
        homePage.selectProduct(productInfo.get("productName"));
        productPage.addToCart();
        productPage.goToShoppingCart();
        cartPage.goToCheckoutPage();
        checkoutPage.provideInfo(productInfo.get("firstName"), productInfo.get("lastName"), productInfo.get("address"));
        checkoutPage.goBackToCartPage();
    }

    @Test (dataProvider = "provideInfoData")
    public void verifyTotalPrice(HashMap<String,String> productInfo){
        homePage.selectProduct(productInfo.get("productName"));
        productPage.addToCart();
        productPage.goToShoppingCart();
        cartPage.goToCheckoutPage();
        checkoutPage.provideInfo(productInfo.get("firstName"), productInfo.get("lastName"), productInfo.get("address"));
        String totalPrice = checkoutPage.getTotalPrice();
        Assert.assertEquals(totalPrice, productInfo.get("productPrice"));
    }

    @Test (dataProvider = "provideInfoData")
    public void verifyErrorMessageWhenFirstNameNotSelected(HashMap<String,String> productInfo){
        homePage.selectProduct(productInfo.get("productName"));
        productPage.addToCart();
        productPage.goToShoppingCart();
        cartPage.goToCheckoutPage();
        checkoutPage.provideInfo("", productInfo.get("lastName"), productInfo.get("address"));
        String errorMessage = checkoutPage.getOrderMessage();
        Assert.assertEquals(errorMessage, "First Name is required");
    }

    @Test (dataProvider = "provideInfoData")
    public void verifyErrorMessageWhenLastNameNotSelected(HashMap<String,String> productInfo){
        homePage.selectProduct(productInfo.get("productName"));
        productPage.addToCart();
        productPage.goToShoppingCart();
        cartPage.goToCheckoutPage();
        checkoutPage.provideInfo(productInfo.get("firstName"), "", productInfo.get("address"));
        String errorMessage = checkoutPage.getOrderMessage();
        Assert.assertEquals(errorMessage, "Last Name is required");
    }

    @Test (dataProvider = "provideInfoData")
    public void verifyErrorMessageWhenAddressNotSelected(HashMap<String,String> productInfo){
        homePage.selectProduct(productInfo.get("productName"));
        productPage.addToCart();
        productPage.goToShoppingCart();
        cartPage.goToCheckoutPage();
        checkoutPage.provideInfo(productInfo.get("firstName"), productInfo.get("lastName"), "");
        String errorMessage = checkoutPage.getOrderMessage();
        Assert.assertEquals(errorMessage, "Address is required");
    }

    @Test (dataProvider = "provideInfoData")
    public void completeOrder(HashMap<String,String> productInfo){
        homePage.selectProduct(productInfo.get("productName"));
        productPage.addToCart();
        productPage.goToShoppingCart();
        cartPage.goToCheckoutPage();
        checkoutPage.provideInfo(productInfo.get("firstName"), productInfo.get("lastName"), productInfo.get("address"));
        checkoutPage.completeOrder();
        String successfulMessage = checkoutPage.getCompleteOrderSuccessfulMessage();
        Assert.assertEquals(successfulMessage, "Thank you for your order!");
    }

    @Test (dataProvider = "provideInfoData")
    public void returnToShoppingAfterCompleteOrder(HashMap<String,String> productInfo){
        homePage.selectProduct(productInfo.get("productName"));
        productPage.addToCart();
        productPage.goToShoppingCart();
        cartPage.goToCheckoutPage();
        checkoutPage.provideInfo(productInfo.get("firstName"), productInfo.get("lastName"), productInfo.get("address"));
        checkoutPage.completeOrder();
        String successfulMessage = checkoutPage.getCompleteOrderSuccessfulMessage();
        Assert.assertEquals(successfulMessage, "Thank you for your order!");
        checkoutPage.continueShopping();
    }

    @DataProvider(name = "provideInfoData")
    public Object[][] getData() throws IOException {
        List<HashMap<String,String>> data = getJsonData(
                "C:\\Users\\Ionut\\Desktop\\FastTrackITWebApp\\src\\test\\java\\TestData\\provideInfoCheckout.json");
        return new Object[][] {{data.get(0)}};
    }

}

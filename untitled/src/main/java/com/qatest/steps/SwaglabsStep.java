package com.qatest.steps;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.dom4j.DocumentException;
import org.testng.Assert;

import java.util.HashMap;


public class SwaglabsStep {
    static HashMap<String,String> mapItems = new HashMap<>();
    static Swaglabs swaglabs = new Swaglabs();
    @Given("navigate to the website")
    public void navigateToWebsite() throws InterruptedException {
        swaglabs.OpenBrowse();
    }

    @When("enter {string} and {string} and click LOGIN button")
    public void enterLoginInformation(String userName,String passWord) {
        swaglabs.EditBox("user-name").sendKeys(userName);
        swaglabs.EditBox("password").sendKeys(passWord);
        swaglabs.Button("login-button").click();
    }
    @Then("PRODUCTS page displays")
    public void productsPage() throws InterruptedException {
        Thread.sleep(3000);
        Assert.assertEquals(swaglabs.Title(),"PRODUCTS");

    }
    @Then("Login failure with {string}")
    public void loginFailed(String errorMessage) {
        Assert.assertEquals(swaglabs.LoginError().getText(),errorMessage);
    }
    @Given("{string} the products and add {int} to cart to CHECKOUT")
    public void filterAndAddItemsToCart(String filterOption, int items) {
        swaglabs.Filter(filterOption);
        for(int j=0;j<items;j++){
            mapItems.put(swaglabs.ProductItemName(j),swaglabs.ProductItemPrice(j));
            swaglabs.AddToCart(j);
        }

        Assert.assertEquals(swaglabs.ShopCartBadge(),items);

        swaglabs.ShopCart().click();

        Assert.assertEquals(swaglabs.Title(),"YOUR CART");
        Assert.assertEquals(swaglabs.CartItem().size(),items);

        for(int i=0;i<items;i++){
            Assert.assertEquals(swaglabs.CartItemPrice(i),mapItems.get(swaglabs.CartItemName(i)));
        }
        swaglabs.Button("checkout").click();

        Assert.assertEquals(swaglabs.Title(),"CHECKOUT: YOUR INFORMATION");
    }

    @And("enter {string}, {string} and {string} and continue")
    public void enterZipContinue(String firstName, String lastName, String zipCode) {
        swaglabs.EditBox("firstName").sendKeys(firstName);
        swaglabs.EditBox("lastName").sendKeys(lastName);
        swaglabs.EditBox("postalCode").sendKeys(zipCode);
        swaglabs.Button("continue").click();

        Assert.assertEquals(swaglabs.Title(),"CHECKOUT: OVERVIEW");
    }

    @Then("check the total amount of {int} and press FINISH")
    public void checkTheTotalAmountAndPressFINISH(int items) {
        Assert.assertEquals(swaglabs.CheckoutItems().size(),items);

        float temp = 0;
        for(int i=0;i<items;i++){
            Assert.assertEquals(swaglabs.CheckoutItemPrice(i),mapItems.get(swaglabs.CheckoutItemName(i)));
            temp += Float.parseFloat(swaglabs.CartItemPrice(i).replace('$','\0'));

        }

        Assert.assertEquals(temp,swaglabs.SubTotal());
        Assert.assertEquals(Float.parseFloat(String.format("%.2f",swaglabs.SubTotal()+swaglabs.Tax())),swaglabs.Total());

        swaglabs.Button("finish").click();
    }

    @Then("thank you for your order")
    public void thankYouForYourOrder() {
        Assert.assertEquals(swaglabs.Title(),"CHECKOUT: COMPLETE!");
        Assert.assertEquals(swaglabs.ThankYou(),"Your order has been dispatched, and will arrive just as fast as the pony can get there!");
    }

    @Then("use login info from external xml file to login")
    public void loginWithInfoFromExternalFile() throws DocumentException, InterruptedException {
        swaglabs.retrieveTestData();
        for (HashMap<String, String> testUser : swaglabs.testUsers) {
            String username = testUser.keySet().toString().replace('[', '\0').replace(']', '\0');
            String password = testUser.values().toString().replace('[', '\0').replace(']', '\0');
            enterLoginInformation(username, password);
            productsPage();
            navigateToWebsite();
        }
    }

}

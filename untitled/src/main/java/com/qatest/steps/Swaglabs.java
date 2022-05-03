package com.qatest.steps;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class Swaglabs {
    static ChromeDriver driver;
    static{
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
    }
    static ArrayList<HashMap<String,String>> testUsers = new ArrayList<>();
    public void OpenBrowse() throws InterruptedException {
        driver.get("https://www.saucedemo.com");
        Thread.sleep(2000);
    }

    public WebElement EditBox(String valueOfNameLocator){
        return driver.findElement(By.name(valueOfNameLocator));
    }

    public WebElement Button(String valueOfNameLocator){
        return driver.findElement(By.name(valueOfNameLocator));
    }

    public WebElement LoginError(){
        return driver.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]/h3"));
    }

    public String Title() {
        return driver.findElement(By.className("title")).getText();
    }

    public String ThankYou(){
        return driver.findElement(By.className("complete-text")).getText();
    }

    public void Filter(String filterOption){
        WebElement elementFilter = driver.findElement(By.className("product_sort_container"));
        Select select = new Select(elementFilter);
        select.selectByVisibleText(filterOption);
    }

    public WebElement ProductItems(int item){
        return driver.findElements(By.className("inventory_item_description")).get(item);
    }

    public String ProductItemName(int item){
        return ProductItems(item).findElement(By.className("inventory_item_name")).getText();
    }
    public String ProductItemPrice(int item){
        return ProductItems(item).findElement(By.className("inventory_item_price")).getText();
    }

    public void AddToCart(int item){
        ProductItems(item).findElement(By.tagName("button")).click();
    }

    public int ShopCartBadge(){
        return Integer.parseInt(driver.findElement(By.className("shopping_cart_badge")).getText());
    }

    public WebElement ShopCart(){
        return driver.findElement(By.className("shopping_cart_link"));
    }

    public List<WebElement> CartItem(){
        return driver.findElements(By.className("cart_item"));
    }

    public String CartItemName(int item){
        return CartItem().get(item).findElement(By.className("inventory_item_name")).getText();
    }

    public String CartItemPrice(int item){
        return CartItem().get(item).findElement(By.className("inventory_item_price")).getText();
    }

    public List<WebElement> CheckoutItems(){
        return driver.findElements(By.className("cart_item"));
    }

    public String CheckoutItemName(int item){
        return CheckoutItems().get(item).findElement(By.className("inventory_item_name")).getText();
    }

    public String CheckoutItemPrice(int item){
        return CheckoutItems().get(item).findElement(By.className("inventory_item_price")).getText();
    }
    public float SubTotal(){
        return Float.parseFloat(driver.findElement(By.className("summary_subtotal_label")).getText().split(":")[1].trim().replace('$','\0'));
    }

    public float Tax(){
        return Float.parseFloat(driver.findElement(By.className("summary_tax_label")).getText().split(":")[1].trim().replace('$','\0'));
    }

    public float Total(){
        return Float.parseFloat(driver.findElement(By.className("summary_total_label")).getText().split(":")[1].trim().replace('$','\0'));
    }

    public void CloseBrowse(){
        driver.close();
    }

    public void retrieveTestData() throws DocumentException {
        String filePath = "src/main/resources/testdata.xml";
        SAXReader saxReader = new SAXReader();
        Document testData = saxReader.read(new File(filePath));
        Element testDataset = testData.getRootElement();
        Iterator<Element> it = testDataset.elementIterator();
        while (it.hasNext()){
            Element users = it.next();
            HashMap<String,String> row = new HashMap<>();
            row.put(users.element("username").getText(),users.element("password").getText());
            testUsers.add(row);
        }
    }

}

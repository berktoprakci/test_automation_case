import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

public class TestCase12 extends BaseTest{

    String url = "https://automationexercise.com/";
    List<WebElement> cartButtons;
    List<String> productNames = new ArrayList<>();
    List<String> productPrices = new ArrayList<>();
    List<WebElement> cartNames;
    List<WebElement> cartPrices;
    List<WebElement> cartQuantities;
    List<WebElement> cartTotalPrices;

    @Test
    public void Case12 () {
        driver.get(url);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".features_items .col-sm-4")));
        Assert.assertEquals(driver.getCurrentUrl(),url);
        Assert.assertEquals(driver.getTitle(), "Automation Exercise");
        Assert.assertEquals(driver.findElements(By.cssSelector(".features_items .col-sm-4")).size(), 34);

        driver.findElement(By.cssSelector("a[href='/products']")).click();

        wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(".single-products"),34));
        cartButtons = driver.findElements(By.cssSelector(".add-to-cart"));
        productNames.add(driver.findElements(By.cssSelector(".single-products p")).get(0).getText().trim());
        productNames.add(driver.findElements(By.cssSelector(".single-products p")).get(2).getText().trim());
        productPrices.add(driver.findElements(By.cssSelector(".single-products h2")).get(0).getText().trim());
        productPrices.add(driver.findElements(By.cssSelector(".single-products h2")).get(2).getText().trim());

        new Actions(driver).moveToElement(cartButtons.get(0)).perform();
        wait.until(ExpectedConditions.visibilityOf(cartButtons.get(1)));
        wait.until(ExpectedConditions.elementToBeClickable(cartButtons.get(1)));
        cartButtons.get(0).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-dismiss='modal']")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-dismiss='modal']")));
        driver.findElement(By.cssSelector("button[data-dismiss='modal']")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("button[data-dismiss='modal']")));

        new Actions(driver).moveToElement(cartButtons.get(2)).perform();
        wait.until(ExpectedConditions.visibilityOf(cartButtons.get(3)));
        wait.until(ExpectedConditions.elementToBeClickable(cartButtons.get(3)));
        cartButtons.get(2).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.modal-body a[href='/view_cart']")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.modal-body a[href='/view_cart']")));
        driver.findElement(By.cssSelector("div.modal-body a[href='/view_cart']")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.modal-body a[href='/view_cart']")));

        wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("tbody > tr "),2));
        cartNames = driver.findElements(By.cssSelector(".cart_description a"));
        cartPrices = driver.findElements(By.cssSelector(".cart_price p"));
        cartQuantities = driver.findElements(By.cssSelector(".cart_quantity button"));
        cartTotalPrices = driver.findElements(By.cssSelector(".cart_total_price"));

        Assert.assertEquals(productNames.get(0),cartNames.get(0).getText().trim());
        Assert.assertEquals(productNames.get(1),cartNames.get(1).getText().trim());
        Assert.assertEquals(productPrices.get(0),cartPrices.get(0).getText().trim());
        Assert.assertEquals(productPrices.get(1),cartPrices.get(1).getText().trim());
        Assert.assertEquals(cartQuantities.get(0).getText(),"1");
        Assert.assertEquals(cartQuantities.get(1).getText(),"1");
        Assert.assertEquals(cartTotalPrices.get(0).getText().trim(),productPrices.get(0));
        Assert.assertEquals(cartTotalPrices.get(1).getText().trim(),productPrices.get(1));

    }
}

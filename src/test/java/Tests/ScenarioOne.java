package Tests;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ScenarioOne {
    public static WebDriver driver;
    
    @BeforeClass
    public static void Setup(){
        System.setProperty("webdriver.chrome.driver", "D:\\Work\\untitled\\src\\test\\resources\\drivers\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-search-engine-choice-screen");
        driver = new ChromeDriver(options);
        driver.get("https://magento.softwaretestingboard.com/");
        driver.manage().window().maximize();
    }
    
    @After
    public void after() {
        driver.close();
    }
    
    @AfterClass
    public static void afterClass() {
        driver.quit();
    }
    
    @Test
    public void test1() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        //▪ Using navigation menu, find mens Hoodies & Sweatshirts section.
        Actions actions = new Actions(driver);
        WebElement hover1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Men']")));
        actions.moveToElement(hover1).perform();
        WebElement hover2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li/a/span[text()='Men']/../following-sibling::ul/li/a/span[text()='Tops']")));
        actions.moveToElement(hover2).perform();
        WebElement item = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ui-id-20")));
        item.click();
        
        //▪ Check/Assert that the displayed number of jackets matches the selected  number of jackets displayed per page. 
        List<WebElement> olElements = driver.findElements(By.xpath("//ol[@class='products list items product-items']//li"));
        int count = olElements.size();
        List<WebElement> elements = driver.findElements(By.xpath("//select[@id='limiter']//option[@selected]"));
        WebElement shownElement = null;
        
        for (WebElement element : elements) {
            if (element.isDisplayed()) {
                shownElement = element;
                break;
            }
        }
        
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", shownElement);
        assert shownElement != null;
        String limiterValue = shownElement.getText();
        Assert.assertEquals(count, Integer.parseInt(limiterValue));
        
        //▪ Select “Frankie Sweatshirt” and open its details.
        WebElement frankieSweatshirt = driver.findElement(By.xpath("//a[contains(text(), 'Frankie Sweatshirt')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", frankieSweatshirt);
        frankieSweatshirt.click();
        
        //▪ Select size, colour and quantity.
        String quantity = "2";
        driver.findElement(By.xpath("//div[@class = 'swatch-option text' and text()='M']")).click();
        driver.findElement(By.xpath("//div[@option-label='Yellow']")).click();
        WebElement qty = driver.findElement(By.id("qty"));
        qty.clear();
        qty.sendKeys(quantity);
        //another way
        //driver.findElement(By.id("qty")).sendKeys(Keys.BACK_SPACE, "2");
        
        //▪ Add product to cart and check that cart icon is updated with product quantity.
        driver.findElement(By.xpath("//span[text()='Add to Cart']")).click();
        WebElement cartQty = driver.findElement(By.xpath("//span[@class='counter-number']"));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//span[@class='counter-number']"), quantity));
        String assertQuantity = cartQty.getText();
        Assert.assertEquals(quantity, assertQuantity);
        
        //▪ Open cart and check if product match the one You added to the cart.
        driver.findElement(By.xpath("//a[@class='action showcart']")).click();
        WebElement product = driver.findElement(By.xpath("//a[contains(text(), 'Frankie  Sweatshirt')]"));
        Assert.assertTrue(product.isDisplayed());
        
        //▪ Proceed to checkout
        driver.findElement(By.xpath("//button[text()='Proceed to Checkout']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("customer-email")));
        
        //▪ Complete the order.
        driver.findElement(By.id("customer-email")).sendKeys("TestAdress@domain.com");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("firstname")));
        driver.findElement(By.name("firstname")).sendKeys("Juan");
        driver.findElement(By.name("lastname")).sendKeys("Francisco");
        driver.findElement(By.name("company")).sendKeys("Empresa Falsa");
        driver.findElement(By.name("street[0]")).sendKeys("C. Falsa 445");
        driver.findElement(By.name("street[1]")).sendKeys("Piso 2, Apartamento 1");
        driver.findElement(By.name("street[2]")).sendKeys("C. Falsa 48");
        driver.findElement(By.name("city")).sendKeys("Ciudad de México");
        WebElement selectElement = driver.findElement(By.name("country_id"));
        Select select = new Select(selectElement);
        select.selectByVisibleText("Mexico");
        selectElement = driver.findElement(By.name("region_id"));
        select = new Select(selectElement);
        select.selectByVisibleText("Estado de México");
        driver.findElement(By.name("postcode")).sendKeys("11001");
        driver.findElement(By.name("telephone")).sendKeys("5553428400");
        driver.findElement(By.xpath("//span[@class='price' and text()='$10.00']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Next']")));
        WebElement next = driver.findElement(By.xpath("//span[text()='Next']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", next);
        next.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Place Order']")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//img[@alt='Loading...']")));
        driver.findElement(By.xpath("//span[text()='Place Order']")).click();
    }
    
}

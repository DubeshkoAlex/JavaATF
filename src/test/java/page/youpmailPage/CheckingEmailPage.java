package page.youpmailPage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.AbstractPage;
import utils.StringUtils;

import java.time.Duration;
import java.util.List;

public class CheckingEmailPage extends AbstractPage {

    private final String PAGE_URL = "https://yopmail.com/ru/wm";
    private final Logger logger = LogManager.getRootLogger();

    @FindBy(id = "refresh")
    private WebElement refreshButton;
//    @FindBy(xpath = "//table//td[4]")
//    private List<WebElement> costInTheLetter;
    private final By costFromTheLetterLocator = By.xpath("//table//td[4]");


    public CheckingEmailPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(this.driver,this);
    }

    @Override
    public CheckingEmailPage openPage() {
        driver.navigate().to(PAGE_URL);
        return this;
    }

    public String getCost(){
        List<WebElement> costFromTheLetter;
        int tryCount = 0;
        do{
            costFromTheLetter = driver.findElements(costFromTheLetterLocator);
            logger.info("try to refresh number: " + tryCount);
            logger.info("costInTheLetter.size() == " + costFromTheLetter.size());
            driver.navigate().refresh();
            driver.switchTo().frame("ifmail");
            tryCount++;
        }while (costFromTheLetter.size()==0 && tryCount<5);
        logger.info("all tryings are done, try ro get cost from the letter");
        logger.info("Cost in the letter text: " + costFromTheLetter.get(0).getText());
        return StringUtils.getValue(costFromTheLetter.get(0),"(?<=USD\\s)[\\d,.]*");
    }

}

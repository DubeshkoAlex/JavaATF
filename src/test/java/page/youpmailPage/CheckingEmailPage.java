package page.youpmailPage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import page.AbstractPage;
import utils.StringUtils;

import java.util.List;

public class CheckingEmailPage extends AbstractPage {

    private final String PAGE_URL = "https://yopmail.com/ru/wm";
    private final Logger logger = LogManager.getRootLogger();

    @FindBy(id = "refresh")
    private WebElement refreshButton;
    @FindBy(xpath = "//table//td[4]")
    private List<WebElement> costInTheLetter;


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
        int tryCount = 0;
        while (costInTheLetter.size()==0 && tryCount<5){
            logger.info("try to refresh number: " + tryCount);
            driver.navigate().refresh();
            driver.switchTo().frame("ifmail");
            tryCount++;
        }
        logger.info("all tryings are done, try ro get cost from the letter");
        logger.info("Cost in the letter text: " + costInTheLetter.get(0).getText());
        return StringUtils.getValue(costInTheLetter.get(0),"(?<=USD\\s)[\\d,.]*");
    }

}

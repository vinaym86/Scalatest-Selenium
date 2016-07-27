import org.openqa.selenium.{Keys, WebDriver}
import org.openqa.selenium.chrome.ChromeDriver
import org.scalatest.concurrent.Eventually
import org.scalatest.selenium.WebBrowser
import org.scalatest.time.{Seconds, Span}
import org.scalatest.{FlatSpec, ShouldMatchers}

class ExampleTest extends FlatSpec with ShouldMatchers with WebBrowser with Eventually{

  implicit val webDriver: WebDriver ={
    //change this to point to where you have your driver
    System.setProperty("webdriver.chrome.driver","drivers/chromedriver")
    new ChromeDriver
  }

  //---------------- First test -------------------------
  "Google search for blog.rizvn.com" should "display blog page as first link" in {
    //1. go to page
    go to ("https://www.google.co.uk")

    //2. fill search field name is q
    textField("q").value = "blog.rizvn.com"

    //3. submit form by pressing enter
    pressKeys(Keys.ENTER.toString)

    //4. repeately check for up to 10 seconds for the ajax results to be returned
    eventually(timeout = timeout(Span(10, Seconds))){

      //click on link that has text Code and Stuff on the results page
      click on partialLinkText("Code and Stuff")
    }

    pageTitle should be ("Code and Stuff")
  }


  //---------------- Second test -------------------------
  "Incorrect twitter login" should "login display failure error message" in {
    //1. go to page
    go to ("https://twitter.com/")

    //2. click on login link
    click on linkText("Log In")

    //3. fill in login form
    textField("session[username_or_email]").value = "test"
    pwdField("session[password]").value = "test"

    //4. press enter
    pressKeys(Keys.ENTER.toString)

    //5. find element class cand check text
    className("message-text").element.text should include("did not match our records")
  }

}
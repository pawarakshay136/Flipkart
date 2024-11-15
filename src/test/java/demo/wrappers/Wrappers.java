package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.NumberFormat;
import java.time.Duration;

public class Wrappers {

    // public static void flipkartSeacrchBar(ChromeDriver driver, By xpath, String searchTerm) {
    //     WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    //     WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(xpath));
    //     searchBox.sendKeys(searchTerm + Keys.ENTER);
    // }
    
    public static void flipkartSeacrchBar(WebDriver driver, By locator, String textToEnter){
        System.out.println("Sending Keys");
        Boolean success;
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement inputBox = driver.findElement(locator);
            inputBox.clear();
            inputBox.sendKeys(textToEnter);
            inputBox.sendKeys(Keys.ENTER);
            success = true;
            System.out.println("moving to next step");

        } catch (Exception e) {
            System.out.println("Exception Occured! " + e.getMessage());
            success = false;
        }
        
    }

    public static void clickOnElementWrapper(WebDriver driver, By locator){
        System.out.println("Clicking");
        Boolean success;
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement clickableElement= driver.findElement(locator);
            clickableElement.click();
            success = true;

        }catch(Exception e){
            System.out.println("Exception Occured! " + e.getMessage());
            success = false;

        }
    }

    public static Boolean searchRatingAndCount(WebDriver driver, By locator, double starRating){
        System.out.println("Searching and printing count");
        int washingMachineCount = 0;
        Boolean success;
        try{
            List<WebElement> starRatingElements = driver.findElements(locator);
            for(WebElement starRatingElement : starRatingElements){
                if(Double.parseDouble(starRatingElement.getText())<= starRating){
                    washingMachineCount++;
                }
            }
            System.out.println("Count of washing machine which has star rating less than or equal to " + starRating
                    + ": " + washingMachineCount);

         success =true;
        }catch(Exception e){
            System.out.println("Exception Occured! " + e.getMessage());
            success = false;

        }
        return success;
    }
    
    public static Boolean titleAndDiscount(WebDriver driver, By locator, int discount){
        Boolean success;
        try {
            List<WebElement> productRows = driver.findElements(locator);
            HashMap<String, String> iphoneTitleDiscountMap = new HashMap<>();
            for (WebElement productRow : productRows) {
                // Check if the productRow contains the discount element
                List<WebElement> discountElements = productRow.findElements(By.xpath(".//div[@class='UkUFwK']/span"));
                
                // Proceed only if the discount element is present
                if (!discountElements.isEmpty()) {
                    String discountPercent = discountElements.get(0).getText();
                    int discountValue = Integer.parseInt(discountPercent.replaceAll("[^\\d]", ""));
                    System.out.println(discountValue);
                    
                    if (discountValue > discount) {
                        // Check if the title element exists
                        List<WebElement> titleElements = productRow.findElements(By.xpath(".//div[@class='KzDlHZ']"));
                        
                        if (!titleElements.isEmpty()) {
                            String iphoneTitle = titleElements.get(0).getText();
                            iphoneTitleDiscountMap.put(discountPercent, iphoneTitle);
                            System.out.println("Found discount greater than " + discount + " percent");
                            System.out.println(iphoneTitle);
                        } else {
                            System.out.println("Title element not found for the product.");
                        }
                    }
                } else {
                    System.out.println("Discount element not found in this product row.");
                }
            }
            

            Thread.sleep(3000);

            for (Map.Entry<String, String> iphoneTitleDiscounts : iphoneTitleDiscountMap.entrySet()) {
                System.out.println("Iphone discount percentage :: " + iphoneTitleDiscounts.getKey()
                        + " and its tile :: " + iphoneTitleDiscounts.getValue());
            }
            success = true;
        } catch (Exception e) {
            System.out.println("Exception Occured! ");
            e.printStackTrace();
            success = false;
        }
        return success;
        
   
   
    }

    public static Boolean printTitleandImageURL(WebDriver driver, By locator){
        Boolean success;
        try {
            List<WebElement> userReviewElements = driver.findElements(locator);
            Set<Integer> userReviewSet = new HashSet<>();
            for (WebElement userReviewElement : userReviewElements) {
                int userReview = Integer.parseInt(userReviewElement.getText().replaceAll("[^\\d]", ""));
                userReviewSet.add(userReview);
            }
            List<Integer> userReviewCountList = new ArrayList<>(userReviewSet);
            Collections.sort(userReviewCountList,Collections.reverseOrder());
            System.out.println(userReviewCountList);
            NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
            LinkedHashMap<String, String> productDetailsMap = new LinkedHashMap<>();
            for (int i = 0; i < 5; i++) {
                String formattedUserReviewCount = "("+numberFormat.format(userReviewCountList.get(i))+")";
                String productTitle = driver.findElement(By.xpath("//div[@class='slAVV4']//span[contains(text(),'"
                        +formattedUserReviewCount+ "')]/../../a[@class='wjcEIp']")).getText();
                String productImgURL = driver.findElement(By.xpath("//div[@class='slAVV4']//span[contains(text(),'"
                        +formattedUserReviewCount+ "')]/../..//img[@class='DByuf4']")).getAttribute("src");
                String hightestReviewCountAndProductTitle = String.valueOf(i+1)+" highest review count: "+formattedUserReviewCount+" Title: "+productTitle;
                productDetailsMap.put(hightestReviewCountAndProductTitle, productImgURL);
            }
            //print title and image url of coffee mug
            for(Map.Entry<String,String> productDetails : productDetailsMap.entrySet()){
                System.out.println(productDetails.getKey()+" and Product image url: "+productDetails.getValue());
            }
            success = true;
        } catch (Exception e) {
            System.out.println("Exception Occured! ");
            e.printStackTrace();
            success = false;
        }
        return success;
    }
    
}

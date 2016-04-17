package cn.tju.xcy;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class Login {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	public static void main(String[] args) throws Exception{
		Login test = new Login();
		String csvFilePath = "info.csv";
		String line = "";
		String split = ",";
		boolean verify = false;
		BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
		String resultFilePath = "result.csv";
		BufferedWriter bw = new BufferedWriter(new FileWriter(resultFilePath));
		try {
			test.setUp();
			while((line = br.readLine()) != null){
				String[] id_mail = line.split(split);
				verify = test.testVerifyLogin(id_mail[0], id_mail[1]);
				bw.write(id_mail[0] + "," + id_mail[1] + ",");
				if(verify){
					bw.write("true");
				}
				else{
					bw.write("false");
				}
				bw.newLine();
			}
			test.tearDown();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			bw.close();
			br.close();
		}
		System.out.println("END!");
	}
	
	public void setUp() throws Exception {
		File pathToBinary = new File("D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
		FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
		FirefoxProfile firefoxProfile = new FirefoxProfile();  
	    driver = new FirefoxDriver(ffBinary, firefoxProfile);
	    baseUrl = "http://www.ncfxy.com";
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	public boolean testVerifyLogin(String id, String mail) throws Exception {
	    driver.get(baseUrl + "/index.html");
	    driver.findElement(By.id("name")).clear();
	    driver.findElement(By.id("name")).sendKeys("");
	    driver.findElement(By.id("name")).clear();
	    driver.findElement(By.id("name")).sendKeys(id);
	    driver.findElement(By.id("pwd")).clear();
	    
	    driver.findElement(By.id("pwd")).sendKeys(id.substring(4));
	    driver.findElement(By.id("submit")).click();
	    
	    WebElement mailEle = driver.findElement(By.id("table-main"));
	    String realMail = mailEle.getText().split("\n")[0].substring(3);
	    if(realMail.equals(mail)){
	    	return true;
		}	
	    return false;
    }
	
	 public void tearDown() throws Exception {
		    driver.quit();
		    String verificationErrorString = verificationErrors.toString();
		    if (!"".equals(verificationErrorString)) {
		      fail(verificationErrorString);
		    }
	}
}

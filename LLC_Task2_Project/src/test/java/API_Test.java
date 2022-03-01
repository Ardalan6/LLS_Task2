import static io.restassured.RestAssured.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import io.restassured.RestAssured;

public class API_Test {

	public static void main(String[] args) throws IOException {
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";

		String response = when().get("todos/1").then().assertThat().log().all().statusCode(200).extract().response()
				.asString();
		
		System.out.println("Verifying that the record contains a title");
		
		if(response.contains("title")) {
			System.out.println("Success, record contains a title!");
		}else {
			System.out.println("Fail, record does not contain a title!");
		}		

		
		System.out.println("Writing API response message into excel sheet");
		
		FileInputStream fis = new FileInputStream("src/test/resources/TestData/Response.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet("Sheet1");
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue(response);
		FileOutputStream fos = new FileOutputStream("src/test/resources/TestData/Response.xlsx");
		workbook.write(fos);
		fos.close();
		workbook.close();
		
	}
}

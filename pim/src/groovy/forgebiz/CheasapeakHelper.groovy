package forgebiz

import com.gargoylesoftware.htmlunit.html.*
import com.gargoylesoftware.htmlunit.*
import org.apache.commons.io.FileUtils;
import java.util.List;
import java.util.Map;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import java.util.logging.*;
import org.apache.commons.logging.LogFactory;
import java.util.logging.Logger;
import java.util.logging.Level;

class CheasapeakHelper {





	HtmlPage loggedInPage = null;
	public CheasapeakHelper() {
		this.loggedInPage = doLogin();
	}

	private HtmlPage doLogin() {
		String login = "http://www.gare.com/v3/account.cfm";
		//final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3_6);
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
		
		webClient.setIncorrectnessListener(new IncorrectnessListener() {
			
					@Override
					public void notify(String arg0, Object arg1) {
						// TODO Auto-generated method stub
			
					}
				});
			
			
		/*
		 webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		 webClient.getOptions().setThrowExceptionOnScriptError(false);
		 */
		//LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

		//java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
		//java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);

		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);


		Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
		//webClient.se
		webClient.getCookieManager().setCookiesEnabled(true);
		HtmlPage page = webClient.getPage(login);
		final HtmlForm form = page.getFormByName("NTALoginForm");
		final HtmlSubmitInput button =  form.getInputByName("submit");
		final HtmlTextInput textField =  form.getInputByName("Username");
		final HtmlPasswordInput pwd =  form.getInputByName("Password");
		textField.setValueAttribute("masrhodes@yahoo.com");
		pwd.setValueAttribute("saffire");
		final HtmlPage page2 =  button.click();
		return page2;
	}

	private HtmlPage doSearch(HtmlPage loggedInPage,String sku) {
		final HtmlForm SearchFields = loggedInPage.getFormByName("SearchFields");
		final HtmlTextInput SearchKeywords =  SearchFields.getInputByName("SearchKeywords");
		SearchKeywords.setValueAttribute( sku);
		final HtmlImageInput submit =  SearchFields.getInputByName("submit");
		HtmlPage searchResultPage =  submit.click();

		return searchResultPage;
	}
	public Map getFacts(String sku2) {
		
		String sku = sku2.substring(1);
		
		
		//println "sku=" + sku;
		Map results = new HashMap();


		HtmlPage resultsPage = doSearch(loggedInPage,sku);


	//	println "results page=" + resultsPage.asXml();
		def tables = resultsPage.getByXPath("//table[@class='listTable']");
		
		if (tables == null) {
			return;
		}

		HtmlTable listTable =  (HtmlTable)tables.get(0);


		//should be one row
		HtmlTableRow row = listTable.getRow(1);
		println row.asText()
		
		//for ( HtmlTableRow row : listTable.getRows()) {

		/*
		for (final HtmlTableCell cell : row.getCells()) {
			println("index=" + cell.getIndex() + " " +  cell.asText());
		}
		println "3 " + row.getCell(3).asText();
		println "4 " +  row.getCell(4).asText();
		//println "5 " +  row.getCell(5).asText();
*/
		HtmlTableCell cell2 = row.getCell(3);

	//	println "4th=" + cell2.getTextContent();

		Integer caseSize = Integer.parseInt(cell2.getTextContent());
		results.put("number_in_case", caseSize);

		HtmlForm formx = row.getElementsByTagName("form").get(0);
		HtmlHiddenInput prox = formx.getInputByName("ProductID");
		results.put("gare_id", prox.getValueAttribute());

		//}


		//println results.toString();
		return results;

	}
}

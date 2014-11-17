package forgebiz

import java.text.*;

import org.apache.commons.io.FileUtils;
import org.apache.solr.common.SolrDocument

import java.util.List;
import java.util.Map;




class ProductQuantity {

	//Product product
	SolrDocument productDocument
	
	int quantity = 1;


	public int hashCode() {
		return product.id.intValue();
	}

	public boolean equals(Object object) {
		return object.hashCode() == this.hashCode();
	}
	
	public String toString() {
		return productDocument.toString() + ", quantity=" + quantity;
	}

	String getCartLink() {
		String vendor = getManufacturer();
		if (vendor.equals("Gare")) {
			//need to get the sku, strip the G
//			Attribute sku = Attribute.findByName("sku");
//			AttributeValue skuav = getAttributeValue(product,sku);
//			//this will strip the leading zero
//			String ProductID = skuav.stringValue.substring(1);
//			String ProductQTY = params.quantity;

		}
	}

	public String getExtendedCost2() {
		Double currencyAmount = new Double(getExtendedCost());
		Currency currentCurrency = Currency.getInstance(Locale.default);
		NumberFormat currencyFormatter =
				NumberFormat.getCurrencyInstance(Locale.default);
		return currencyFormatter.format(currencyAmount);
	}
	
	
	public String getCost2() {
		Double currencyAmount = new Double(getCost());
		Currency currentCurrency = Currency.getInstance(Locale.default);
		NumberFormat currencyFormatter =
				NumberFormat.getCurrencyInstance(Locale.default);
		return currencyFormatter.format(currencyAmount);
	}
	
	
	private double getCost() {
		def cost = productDocument.getFieldValue("cost_d");
		if (cost == null) {
			return 0.0;
		}
		return cost;
		
	}
	
	public double getExtendedCost() {
		double cost = getCost();
		//double cost_d = cost.asDouble();
		int numberInCase = getNumberInCase();
		double extendedCost = (double)cost * (double)quantity * (double)numberInCase;
		return extendedCost;
		
		
	}
	
	
	String getName() {
		def name = productDocument.getFieldValue("name");
		if (name == null) {
			return "";
		}
		return name;
		
	}

	
	

	String getDescription() {
		def description = productDocument.getFieldValue("description");
		if (description == null) {
			return "";
		}
		return description;
		
	}
	
	
	String getDetailUrl() {
		def detail_url = productDocument.getFieldValue("detail_url_s");
		return detail_url;
		
	}
	
	
	String getImageUrl() {
		def name = productDocument.getFieldValue("product_image_url_s");
		if (name == null) {
			return "/pim/assets/120px-No_image_available.svg.png";
		}
		//  http://localhost:8080/pim/assets/forgebiz-logo-forge-125.png
		return name;
		
	}
	
	Integer getNumberSold() {
		def number_sold_i = productDocument.getFieldValue("number_sold_i");
		if (number_sold_i == null) {
			return 0;
		}
		return number_sold_i;
		
	}
	
	Integer getProductId() {
		def productId = productDocument.getFieldValue("product_id_l");
		if (productId == null) {
			return 0;
		}
		return productId;
		
	}


	Integer getNumberInCase() {
		def nic = productDocument.getFieldValue("number_in_case_i");
		if (nic == null) {
			return 1;
		}
		return nic;
	}

	String getSize() {
		def size = productDocument.getFieldValue("size_s");
		if (size == null) {
			return "";
		}
		return size;
		
	}
	
	String getSku() {

		def sku = productDocument.getFieldValue("sku");
		if (sku == null) {
			return "";
		}
		return sku;
	}


	String getManufacturer() {
		def manu = productDocument.getFieldValue("manu");
		if (manu == null) {
			return "";
		}
		return manu;
	}
}

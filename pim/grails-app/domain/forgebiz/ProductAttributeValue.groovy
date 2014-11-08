package forgebiz

import java.text.*;

class ProductAttributeValue {

	Product product
	Attribute attribute

	static transients = ["currency", "indexValue" ]


	transient String getCurrency() {
		Double currencyAmount = new Double(doubleValue);
		Currency currentCurrency = Currency.getInstance(Locale.default);
		NumberFormat currencyFormatter =
				NumberFormat.getCurrencyInstance(Locale.default);
		return currencyFormatter.format(currencyAmount);
	}

	def String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("${attribute.name} ");
		
		String indexValue = getIndexValue();
		
		if (indexValue != null) {

			sb.append(" ${indexValue} ");
		}
		//return "${attribute.name} ${stringValue} ${doubleValue}"
		return sb.toString();
	}
	
	transient String getIndexValue() {
		if (attribute.type == 'String') {
			return stringValue;
		} else if (attribute.type == 'Double') {
			return doubleValue;
		}else if (attribute.type == 'Integer') {
			return integerValue;
		}
		
		return "";
		
		
	}

	String stringValue
	Double doubleValue
	Integer integerValue
	

	static constraints = {
		stringValue nullable:true
		doubleValue nullable:true
		integerValue nullable:true
		attribute unique:['product']
	}
	
	//added the following to see if this will allow the removeFrom to work
	public int hashCode() {
		return id.intValue();
	}
	
	public boolean equals(Object object) {
		return object.hashCode() == this.hashCode();
	}

	
	
}

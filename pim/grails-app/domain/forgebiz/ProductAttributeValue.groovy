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
		product nullable:false
		attribute unique:['product']
		stringValue nullable:true
		doubleValue nullable:true
		integerValue nullable:true
		
		
		
		integerValue( validator: { val, obj ->
			//now that the integer is a primitive when it is passed in, it's always going to be positive
			//and we have a test that is passing in a string instead, so let's verify that the string (and all other values)
			//have not been set
			
		
			if (obj.attribute.type == 'Integer') {
				
				
				//if the type is a Integer, everything else should be null/empty
				if (obj.stringValue) {
					return false;
				}else if (obj.doubleValue) {
					return false;
				}
				
				println "attribute type is Integer=" +  obj.integerValue
				return (obj.integerValue != null)
			}
		})
		

	}
	
	//added the following to see if this will allow the removeFrom to work
	public int hashCode() {
		if (id == null) {
			return super.hashCode();
		}
		return id.intValue();
	}
	
	public boolean equals(Object object) {
		return object.hashCode() == this.hashCode();
	}

	
	
}

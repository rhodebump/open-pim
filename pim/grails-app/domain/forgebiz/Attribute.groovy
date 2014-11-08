package forgebiz

import java.util.Date;

class Attribute {

	String name
	String type


	Date dateCreated
	Date lastUpdate

	static transients = ["indexName"]

	static constraints = {
		dateCreated nullable:true, display:false
		lastUpdate nullable:true, display:false

		type(inList: [
			"String",
			"Integer",
			"Double",
			"Boolean",
			"Date"
		])
	}

	transient String getIndexName() {
		if (type == 'String') {
			return name + "_s";
		}else if (type == 'Integer') {
			return name + "_i";
		}else if (type == 'Double') {
			return name + "_d";
		}else if (type == 'Boolean') {
			return name + "_b";
		}
		return name;
	}


	def String toString() {
		return "${name}"
	}


	public int hashCode() {
		return id.intValue();
	}

	public boolean equals(Object object) {
		return object.hashCode() == this.hashCode();
	}
}

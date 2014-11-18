
import forgebiz.*

class BootStrap {

	def init = { servletContext ->


		User user = User.findByUsername('admin');
		if (user == null) {
			def adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true)
			def userRole = new Role(authority: 'ROLE_USER').save(flush: true)
			def testUser = new User(username: 'admin', password: 'forgebiz')
			testUser.save(flush: true)
			UserRole.create testUser, adminRole, true
		} else{
			//no need to do data imports
			return;
		}
		createAttributes();

		println "query1";
		Query query1 = createQuery("Most profitable item mix with correct size distribution, Gare,Chesapeake");
		println "constraint 1";
		createConstraint("manufacturer","Gare",query1);
		createConstraint("manufacturer","Chesapeake",query1);


		query1.errors.allErrors.each { println it }


		query1.save(failOnError:true);

		Query query2  =createQuery("Most profitable item mix with correct size distribution, Gare Only");
		createConstraint("manufacturer","Gare",query2);
		query2.save(failOnError:true);

		Query query3 = createQuery("Most profitable item mix with correct size distribution, Chesapeake Only");
		createConstraint("manufacturer","Chesapeake",query3);
		query3.save(failOnError:true);
	}
	def destroy = {
	}


	private void createAttributes() {

		createAttribute("manufacturer","String");
		createAttribute("number_sold","Integer");
		//createAttribute("sum");
		createAttribute("cost","Double");
		//createAttribute("rank");
		createAttribute("size","String");
		createAttribute("sku","String");
		//createAttribute("profitablity");
	}



	private void createAttribute(String name,String type) {
		Attribute attribute = new Attribute();
		attribute.name = name;
		attribute.type = type;
		attribute.save();
	}
	private Query createQuery(String name) {
		Query query = new Query();
		query.name = name;
		createConstraint("size","Small", ,query).percentage = 10.0;
		createConstraint("size","Medium", query).percentage = 65.0;
		createConstraint("size","Large",query).percentage = 15.0;
		createConstraint("size","Extra Large",query).percentage = 10.0;

		createSorts(query);
		return query;
	}

	private Constraint createConstraint(String attrname,String attrvalue,Query query){
		Constraint constraint = new Constraint();
		Attribute attr = Attribute.findByName(attrname);
		if (attr == null) {
			throw new IllegalArgumentException("attribute name not found:" + attrname);
		}
		constraint.attribute = attr;
		constraint.attributeValue = attrvalue;



		query.addToQconstraints(constraint);

		constraint.validate();
		constraint.errors.allErrors.each { println it }


		return constraint;
	}

	private void createSorts(Query query) {

		createSort("number_sold","Descending",query);
	}

	private void createSort(String attributeName,String orderby,Query query) {
		Sort sortx = new Sort();
		Attribute attr = Attribute.findByName(attributeName);
		sortx.attribute = attr;
		sortx.orderby = orderby;
		query.addToQsorts(sortx);
		//sortx.save();
	}
}

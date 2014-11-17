
import forgebiz.*

class BootStrap {

	def init = { servletContext ->

		createQuery();
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

		
		
	}
	def destroy = {
	}


	private void createAttributes() {

		createAttribute("vendor","String");
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
	private void createQuery() {
		Query query = new Query();
		query.name = "Most profitable item mix with correct size distribution";
		createConstraint("size","Small", 10.0,query);
		createConstraint("size","Medium", 65.0,query);
		createConstraint("size","Large", 15.0,query);
		createConstraint("size","Extra Large", 10.0,query);
		createSorts(query);
		query.save(failOnError:true);
	}

	private void createConstraint(String attrname,String attrvalue,double percent,Query query){
		Constraint constraint = new Constraint();
		Attribute attr = Attribute.findByName(attrname);
		constraint.attribute = attr;
		constraint.attributeValue = attrvalue;
		constraint.percentage = percent;
		query.addToQconstraints(constraint);
		//constraint.save();
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

package pim
import forgebiz.*;
import org.apache.solr.client.solrj.SolrServer
//import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

import org.apache.solr.common.SolrInputDocument;


class SolrSyncJob {
	static triggers = {


		simple repeatInterval: 5000l // execute job once in 5 seconds
	}


	def execute() {
		// execute job
		log.error("Doing SolrSyncJob job")


		Product.withTransaction{

			def c = Product.createCriteria()
			def results = c {

				//between("balance", 500, 1000)
				eq("indexed", false)
				//or {
				//	like("holderFirstName", "Fred%")
				//	like("holderFirstName", "Barney%")
				//}
				maxResults(100)
				//order("lastUpdate", "asc")
			}


			//def results = Product.findAllByIndexed(false);


			results.each  { 

				processProduct(it);
			}

		}



	}


	def processProduct(def product) {
		println "processProduct";
		//SolrServer server = null;
		//server = new CommonsHttpSolrServer(solrURL)
		HttpSolrServer server = SearchHelper.getServer();

		//DefaultHttpClient client = (DefaultHttpClient) server.getHttpClient();

		SolrInputDocument doc = new SolrInputDocument()
		doc.addField("id", "product_" + product.id);
		doc.addField("sku_s", product.sku);
		doc.addField("name", product.name);
		doc.addField("product_id_l", product.id);
		
		
		for(ProductAttributeValue pav:product.productAttributeValues) {
			String attrName = pav.attribute.indexName;
			doc.addField(attrName, pav.indexValue);
		}

		server.add(doc);
		server.commit();

		product.indexed = true;
		product.save();

	}

}

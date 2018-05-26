package sparql;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

import data.MapPoint;

public class DataHandler {

	public DataHandler() {
	}

	public List<MapPoint> queryCategory(String category, int limit) {
		List<MapPoint> mapPoints = new LinkedList<>();

		Query query = QueryFactory.create(
				"prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>" + 
				"prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
				"prefix rdfsns: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				"prefix lgdo: <http://linkedgeodata.org/ontology/>" +
				"select ?sub ?lat ?lon ?label " + 
				"where { " +
				"    ?sub geo:lat ?lat ." + 
				"    ?sub geo:long ?lon ." + 
				"    ?sub rdfs:label ?label ." +
				"    ?sub rdfsns:type lgdo:" + category + " ." + 
				"} limit " + limit);

		try (QueryExecution qexec = QueryExecutionFactory.sparqlService("http://linkedgeodata.org/sparql", query)){
			ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				QuerySolution qs = results.next();
				String subject = qs.get("sub").toString();
				double lat = Double.parseDouble(qs.get("lat").toString().substring(0, qs.get("lat").toString().indexOf("^^")));
				double lon = Double.parseDouble(qs.get("lon").toString().substring(0, qs.get("lon").toString().indexOf("^^")));
				String label = qs.get("label").toString();
				mapPoints.add(new MapPoint(subject, lat, lon, label));
			}
		}

		return mapPoints;
	}
	
	// funktion wird vielleicht noch gebraucht
		private Model queryLinkedGeoDataCategory(String category, int limit) {
			Model model = ModelFactory.createDefaultModel();
			try {
				Query query = QueryFactory.create(
						"prefix lgdr: <http://linkedgeodata.org/triplify/> "  + 
						"prefix lgdo: <http://linkedgeodata.org/ontology/> " + 
						"prefix rdfs: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " + 
						"select ?s ?p ?o where { " +
						"	?s ?p ?o . " + 
						"    	{ " + 
						"           select ?s where { " + 
						"      			bind(lgdo:" + category + " as ?cat) " + 
						"				?s rdfs:type ?cat . " + 
						"    	} " + 
						"    	limit " + limit +
						"  	}" + 
						"}");
				QueryExecution qexec = QueryExecutionFactory.sparqlService("http://linkedgeodata.org/sparql", query);
				ResultSet rs = qexec.execSelect();
				while (rs.hasNext()) {
					QuerySolution qs = rs.next();
					Iterator<String> itVars = qs.varNames();
					String p = qs.get(itVars.next().toString()).toString();
					String o = qs.get(itVars.next().toString()).toString();
					String s = qs.get(itVars.next().toString()).toString();
					Resource resource = model.createResource(s);
					Property property = model.createProperty(p);
					resource.addLiteral(property, o);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return model;
		}

}

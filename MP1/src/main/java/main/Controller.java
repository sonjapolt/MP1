package main;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.rdf.model.Model;

import data.MapPoint;
import sparql.DataHandler;

public class Controller {

	private String selectedCategory;
	private int limit;
	private DataHandler dataHandler;

	public Controller() {
		this.selectedCategory = getCategories().get(0);
		this.limit = 1000;
		this.dataHandler = new DataHandler();
	}

	public String getCategory() {
		System.out.println(this.selectedCategory.toString());
		return this.selectedCategory.toString();
	}

	public List<String> getCategories() {
		return Arrays.asList("Airport", "Beach", "Bridge", "BusStation", "BusStop", "Cemetery", "Crematorium",
				"FireStation", "Courthouse", "Embassy", "Hospital", "Museum", "MusicVenue", "PlaceOfWorship",
				"Playground", "TrainStation", "Zoo");
	}

	public int getLimit() {
		return this.limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getSelectedCategory() {
		return this.selectedCategory;
	}

	public void setSelectedCategory(String selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	public List<MapPoint> getMapPoints() {
		return this.dataHandler.queryCategory(selectedCategory, limit);
	}
}

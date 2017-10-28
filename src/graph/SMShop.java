package graph;

public class SMShop extends SMNode {

	private int storeID;
	private int category;

	public SMShop(long id, double lon, double lat, int storeID, int category) {
		super(id, lon, lat);
		this.category = category;
		this.storeID = storeID;
	}

	public int getStoreID() {
		return storeID;
	}

	public String getFixedCategory() {
		if (category < 1000 && category > 99) {
			return "0"+category;
		}
		return category+"";
	}

	public int getCategory() {
		return category;
	}

}

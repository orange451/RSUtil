package scripts.util.ge;

import scripts.dax_api.shared.jsonSimple.JSONObject;
import scripts.util.misc.ItemWrapper;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;

public class GEItem implements ItemWrapper {
	
	private int id;
	
	private String name;
	
	private boolean members;
	
	private int buyAverage;
	
	private int buyQuantity;
	
	private int sellAverage;
	
	private int sellQuantity;
	
	private int overallAverage;
	
	private int overallQuantity;
	
	private int sp;
	
	public GEItem(JSONObject data) {
		this.id = get(data.get("id"));
		this.name = data.get("name").toString();
		this.members = ((Boolean)data.get("members")).booleanValue();
		this.buyAverage = get(data.get("buy_average"));
		this.buyQuantity = get(data.get("buy_quantity"));
		this.sellAverage = get(data.get("sell_average"));
		this.sellQuantity = get(data.get("sell_quantity"));
		this.overallAverage = get(data.get("overall_average"));
		this.overallQuantity = get(data.get("overall_quantity"));
		this.sp = get(data.get("sp"));
	}
	
	@Override
	public ItemIds getItem() {
		return ItemNames.get(this.getId());
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isMembers() {
		return this.members;
	}
	
	public void setMembers(boolean members) {
		this.members = members;
	}
	
	public int getBuyAverage() {
		return this.buyAverage;
	}
	
	public void setBuyAverage(int average) {
		this.buyAverage = average;
	}
	
	public int getBuyQuantity() {
		return this.buyQuantity;
	}
	
	public void setBuyQuantity(int buyQuantity) {
		this.buyQuantity = buyQuantity;
	}
	
	public int getSellAverage() {
		return this.sellAverage;
	}
	
	public void setSellAverage(int average) {
		this.sellAverage = average;
	}
	
	public int getSellQuantity() {
		return this.sellQuantity;
	}
	
	public void setSellQuantity(int quantity) {
		this.sellQuantity = quantity;
	}
	
	public int getOverallAverage() {
		return this.overallAverage;
	}
	
	public void setOverallAverage(int average) {
		this.overallAverage = average;
	}
	
	public int getOverallQuantity() {
		return this.overallQuantity;
	}
	
	public void setOverallQuantity(int quantity) {
		this.overallQuantity = quantity;
	}
	
	public int getSP() {
		return this.sp;
	}

	private int get(Object object) {
		if ( object instanceof String ) {
			try {
				return (int)Double.parseDouble(object.toString());
			} catch(Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
		if ( object instanceof Number )
			return ((Number)object).intValue();
		
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("id", this.getId());
		json.put("name", this.getName());
		json.put("members", this.isMembers());
		json.put("sp", this.getSP());
		json.put("buy_average", this.getBuyAverage());
		json.put("buy_quantity", this.getBuyQuantity());
		json.put("sell_average", this.getSellAverage());
		json.put("sell_quantity", this.getSellQuantity());
		json.put("overall_average", this.getOverallAverage());
		json.put("overall_quantity", this.getOverallQuantity());
		return json;
	}
}

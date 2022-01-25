package scripts.util.ge;

import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import scripts.dax_api.shared.jsonSimple.JSONObject;
import scripts.util.misc.ItemWrapper;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;

@Deprecated
public class GEItem implements ItemWrapper {
	
	/** ID of the item */
	private int id;
	
	/** Display name of the item */
	@Deprecated
	private String name;
	
	/** Is item Members only */
	@Deprecated
	private boolean members;
	
	/** Average price bought */
	private int buyAverage;
	
	/** Average quantity bought */
	@Deprecated
	private int buyQuantity;
	
	/** Average price sold */
	private int sellAverage;
	
	/** Average quantity sold */
	@Deprecated
	private int sellQuantity;
	
	/** Overall average price */ 
	private int overallAverage;
	
	/** Overall average quantity */
	@Deprecated
	private int overallQuantity;
	
	/** sp */
	private int sp;
	
	public GEItem(int id, JsonObject data) {
		this.id = id;
		this.name = toStringSafe(data.get("name"));
		this.members = toBooleanSafe(data.get("members"));
		
		this.buyAverage = toIntegerSafe(data.get("high"));
		this.buyQuantity = toIntegerSafe(data.get("buy_quantity"));
		
		this.sellAverage = toIntegerSafe(data.get("low"));
		this.sellQuantity = toIntegerSafe(data.get("sell_quantity"));
		
		this.overallAverage = (int) ((double)(buyAverage + buyQuantity)/2d);
		this.overallQuantity = toIntegerSafe(data.get("overall_quantity"));
		
		this.sp = toIntegerSafe(data.get("sp"));
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

	@Deprecated
	public String getName() {
		return this.name;
	}

	@Deprecated
	public void setName(String name) {
		this.name = name;
	}

	@Deprecated
	public boolean isMembers() {
		return this.members;
	}

	@Deprecated
	public void setMembers(boolean members) {
		this.members = members;
	}
	
	public int getBuyAverage() {
		return this.buyAverage;
	}
	
	public void setBuyAverage(int average) {
		this.buyAverage = average;
	}

	@Deprecated
	public int getBuyQuantity() {
		return this.buyQuantity;
	}

	@Deprecated
	public void setBuyQuantity(int buyQuantity) {
		this.buyQuantity = buyQuantity;
	}
	
	public int getSellAverage() {
		return this.sellAverage;
	}
	
	public void setSellAverage(int average) {
		this.sellAverage = average;
	}

	@Deprecated
	public int getSellQuantity() {
		return this.sellQuantity;
	}

	@Deprecated
	public void setSellQuantity(int quantity) {
		this.sellQuantity = quantity;
	}
	
	public int getOverallAverage() {
		return this.overallAverage;
	}
	
	public void setOverallAverage(int average) {
		this.overallAverage = average;
	}

	@Deprecated
	public int getOverallQuantity() {
		return this.overallQuantity;
	}

	@Deprecated
	public void setOverallQuantity(int quantity) {
		this.overallQuantity = quantity;
	}
	
	@Deprecated
	public int getSP() {
		return this.sp;
	}

	private int toIntegerSafe(Object object) {
		if ( object instanceof JsonElement ) {
			return ((JsonElement)object).getAsInt();
		}
		
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
	
	private String toStringSafe(Object object) {
		if ( object == null )
			return "";
		
		return object.toString();
	}
	
	private boolean toBooleanSafe(Object object) {
		if ( object instanceof JsonElement ) {
			return ((JsonElement)object).getAsBoolean();
		}
		
		if ( object == null )
			return false;
		
		if ( !(object instanceof Boolean) )
			return false;
		
		return ((Boolean)object).booleanValue();
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("id", this.getId());
		json.put("name", this.getName());
		json.put("members", this.isMembers());
		json.put("sp", this.getSP());
		json.put("high", this.getBuyAverage());
		json.put("buy_quantity", this.getBuyQuantity());
		json.put("low", this.getSellAverage());
		json.put("sell_quantity", this.getSellQuantity());
		json.put("overall_average", this.getOverallAverage());
		json.put("overall_quantity", this.getOverallQuantity());
		return json;
	}
}

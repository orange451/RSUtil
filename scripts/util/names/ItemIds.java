package scripts.util.names;

import scripts.dax_api.shared.helpers.RSItemHelper;

public abstract class ItemIds {
	public abstract int[] getIds();

	public String getName() {
		return RSItemHelper.getItemName(getIds()[0]);
	}
}

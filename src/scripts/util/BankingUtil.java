package scripts.util;

import org.tribot.api2007.Banking;
import org.tribot.api2007.types.RSItem;
import scripts.util.names.ItemNames;






public class BankingUtil
{
	public static int getAmountOfItems(ItemNames item)
	{
		RSItem[] is = Banking.find(item.getIds());
		if (is == null) {
			return 0;
		}
		int amt = 0;
		for (int i = 0; i < is.length; i++) {
			amt += is[i].getStack();
		}

		return amt;
	}





	public static boolean withdrawFood(int amount)
	{
		if (!Banking.isBankScreenOpen()) {
			return false;
		}
		ItemNames[] names = ItemUtil.getFood();
		for (int i = 0; i < names.length; i++) {
			if (Banking.withdraw(amount, names[i].getIds())) {
				return true;
			}
		}
		return false;
	}
}
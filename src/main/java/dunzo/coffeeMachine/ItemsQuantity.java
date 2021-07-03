package dunzo.coffeeMachine;

import java.util.HashMap;
import java.util.Map;

public class ItemsQuantity {

	public static final String YES = "Yes";
	public static final String NO = "No";
	public  HashMap<String, InventoryItem> inventorycontext;
	
	
	public ItemsQuantity(HashMap<String, InventoryItem> inventorycontext) {
		super();
		this.inventorycontext = inventorycontext;
	}

	public  Boolean consume(String ingredientName, int quanta) {
		return inventorycontext.get(ingredientName).consume(quanta) < 0 ? Boolean.FALSE : Boolean.TRUE;
	}

	public void printInventoryState() {
		for (Map.Entry<String, InventoryItem> x : inventorycontext.entrySet()) {
			System.out.println(x.getValue().getIngredientName() + "," + x.getValue().quantity);
		}
		
	}
	
	public  String isItemLowInStock(String name) {
		if(!inventorycontext.containsKey(name)) {
			addOrRefillItemInInventoryContext(name,0);
			return YES;
		}else {
			return inventorycontext.get(name).quantity == 0 ? YES : NO; 
		}
	}

	// assumption : No-one will give incorrect item name 
	// if this oppose business case, we should not call it from isItemLowInStock
	public  void addOrRefillItemInInventoryContext(String name, int quanta) {
		if (inventorycontext.containsKey(name)) {
			inventorycontext.get(name).add(quanta);
		}else {
			inventorycontext.put(name, new InventoryItem(name, quanta));
		}
	}

}

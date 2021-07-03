package dunzo.coffeeMachine;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunningContext implements IRunningContext {

	private Logger log = LoggerFactory.getLogger(App.class.getSimpleName());
	private Beverage beverage;
	private ItemsQuantity itemsQuantity;

	@Override
	public void setContextParameter(Beverage beverage,ItemsQuantity itemsQuantity) {
		this.beverage = beverage;
		this.itemsQuantity = itemsQuantity;
		
	}

	@Override
	public String call() throws Exception {
		
		Boolean canbePrepared = Boolean.TRUE;
		StringBuffer ans = new StringBuffer();
		for (Ingredient ingredient : beverage.getIngredient()) {
			if (itemsQuantity.inventorycontext.containsKey(ingredient.getIngredientName())) {
				if (canbePrepared && !itemsQuantity.consume(ingredient.getIngredientName(), ingredient.quantity)) {
					canbePrepared = Boolean.FALSE;
					ans.append(beverage.getName()).append(" cannot be prepared because ").append(ingredient.getIngredientName()).append(" is not sufficient");
					//currently We are showing only one item insufficient or unavailable : It can be change as per business requirement
				}
			}else if (canbePrepared) {
				ans.append(beverage.getName()).append(" cannot be prepared because ").append(ingredient.getIngredientName()).append(" is not available");
				canbePrepared = Boolean.FALSE;
				//currently We are showing only one item insufficient or unavailable : It can be change as per business requirement
			}
		}
		
		log.debug(canbePrepared ? beverage.getName() + " is prepared" : ans.toString());
		
		return canbePrepared ? beverage.getName() + " is prepared": ans.toString();
	}

	public Beverage getBeverage() {
		return beverage;
	}

	public void setBeverage(Beverage beverage) {
		this.beverage = beverage;
	}

}

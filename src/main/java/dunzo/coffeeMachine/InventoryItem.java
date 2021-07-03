package dunzo.coffeeMachine;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class InventoryItem extends Ingredient {

	private final Lock itemLock;
	private Boolean lowInStock;

	public InventoryItem(String ingredientName, Integer quantity) {
		super(ingredientName, quantity);
		if (quantity < 0) {
			throw new IllegalArgumentException("Quantity cannot be negative");
		}
		this.itemLock = new ReentrantLock();
		this.lowInStock = quantity == 0 ? Boolean.TRUE : Boolean.FALSE;
	}

	public Integer add(Integer quanta) {

		if (quanta < 0) {
			throw new IllegalArgumentException("Adding Quanta cannot be negative");
		}
		try {
			itemLock.lock();
			quantity = quantity + quanta;
			lowInStock = Boolean.FALSE;
		} finally {
			itemLock.unlock();
		}

		return quantity;
	}

	public Integer consume(Integer quanta) {

		if (quanta < 0) {
			throw new IllegalArgumentException("Quantity cannot be negative");
		}
		try {
			itemLock.lock();
			if (quantity < quanta) {
				lowInStock = Boolean.TRUE;
				quantity = 0;   // assuming Item is getting consumed till it gets finished
				return -1;
			}
			quantity = quantity - quanta;
			if (quantity == 0)
				lowInStock = Boolean.FALSE;
		} finally {
			itemLock.unlock();
		}

		return quantity;
	}

	public boolean isLowInStock() {
		return lowInStock;
	}

	public void setLowInStock(Boolean lowInStock) {
		this.lowInStock = lowInStock;
	}

	public Lock getItemLock() {
		return itemLock;
	}

}

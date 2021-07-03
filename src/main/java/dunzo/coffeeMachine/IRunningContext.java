package dunzo.coffeeMachine;

import java.util.concurrent.Callable;

public interface IRunningContext extends Callable<String> {
	void setContextParameter(Beverage beverage,ItemsQuantity itemsQuantity); // initializing running context to function outlet 
}

import java.util.EventObject;


public class ControlEvent extends EventObject {

	Setting setting;
	public ControlEvent(Object source, Setting s) {
		super(source);
		setting = s;
	}

}

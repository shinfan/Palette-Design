import java.util.EventListener;


public interface ControlListener extends EventListener {
	public void controlEventOccured(ControlEvent e);
}

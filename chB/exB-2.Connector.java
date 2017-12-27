// 리스트 B.2 Connector 클래스

public class Connector {
    public interface Listener {
	public void online(Session s);
	public void offline(Session s);
    }
    public void addListener(Listener l);
    public void removeListener(Listener l);
    public void requestConnect(boolean toConnect);
    ...
}

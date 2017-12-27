// 리스트 B.3 변경된 Connector.Listener

public interface Listener {
    public void online(Session s);
    public void offline(Session s);
    public interface TearDownCallBack {
	public void tornDown();
    }
    public void tearDown(Session s, TearDownCallback cb);
}

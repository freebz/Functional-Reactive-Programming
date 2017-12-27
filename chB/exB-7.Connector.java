// 리스트 B.7 버그가 있는 협력적인 세션 정리

public class Connector {
    public interface Listener {
	public void online(Session s);
	public void offline(Session s);
	public interface TearDownCallBack {
	    public void tornDown();
	}
	public void tearDown(Session s, TearDownCallBack cb);
    }
    private boolean shouldBeOnline;
    private Map<Session, Listener> activeSessions = new HashMap<>();
    ...

    public void requestConnect(boolean req) {
	this.shouldBeOnline = req;
	update();
    }

    private void notifyTearDown() {
	for (Map.Entry<Session, Listener> e : activeSessions.entrySet()) {
	    final Session s = e.getKey();
	    final Listener l = e.getValue();
	    Listener.TearDownCallBack cb =
		new Listener.TearDownCallBack() {
		    void tornDown() {
			activeSessions.remove(s);
			update();
		    }
		};
	    e.getKey().tearDown(s, cb);
	}
    }

    private void update() {
	switch (state) {
	    ...
	    case State.ONLINE:
		if (!shouldBeOnline) {
		    notifyTearDown();            // 버그 1
		    state = State.TEARING_DOWN;
		}
		break;
	    case State.TEARING_DOWN:
		if (activeSessions.isEmpty()) {
		    notifyOffline();             // 버그 2
		    state = State.OFFLINE;
		}
		else
		    break;
		...
	}
    }
    ...
}

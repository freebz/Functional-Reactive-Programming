// 리스트 1-1  리스너: 관찰자 패턴

public class ListBox {
    public interface Listener {
	void itemSelected(int index);
    }

    private List<Listener> listeners = new ArrayList<>();
    public void addListener(Listener l) {
	listener.add(l);
    }
    public void removeListener(listener l) {
	listener.remove(l);
    }
    protected void notifyItemSelected(int index) {
	for (l : listeners) l.itemSelected(index);
    }
}

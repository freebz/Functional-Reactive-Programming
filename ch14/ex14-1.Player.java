// 리스트 14.1 관찰자 인터페이스를 제공하는 미디어 플레이어 컴포넌트

public class Player {
    public interface Listener {
	public void paused(boolean isPaused);
	public void seconds(int seconds);
	public void ended();
    }
    private List<Listener> listeners = new ArrayList<>();
    public void addListener(Listener l) {
	listeners.add(l);
    }
    public void removeListener(Listener l) {
	listeners.add(l);
    }
    public void removeListener(Listener l) {
	listeners.remove(l);
    }
    public void play(Track t) { ... }
    public void pause() { ... }
    public void resume() { ... }
    public boolean isPaused() { ... }
    public int getSeconds() { ... }
    private void notifyPaused(boolean p) {
	for (l : listeners) l.paused(p); }
    private void notifySeconds(int s) {
	for (l : listeners) l.seconds(s); }
    private void notifyEnded() {
	for (l : listeners) l.ended(); }
}

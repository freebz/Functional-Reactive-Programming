// 리스트 14.3 관찰자 인터페이스를 사용해 다음 노래를 재생하는 컨트롤러

public class Controller implements Player.Listener {
    public Controller(Player player) {
	this.player = player;
	player.addListener(this);
	playNext();
    }
    private Player player;
    public void ended() {
	playNext();
    }
    public void playNext() { ... }
}

// 리스트 14.4 다음 노래를 재생하는 FRP화한 컨트롤러

public class Controller {
    public Controller(Player player) {
	this.player = player;
	player.sEnded.listen(() -> playNext());
	playNext();
    }
    private Player player;
    public void playNext() { ... }
}

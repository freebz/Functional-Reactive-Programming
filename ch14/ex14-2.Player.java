// 리스트 14.2 FRP화한 미디어 플레이어 컴포넌트

public class Player {
    public void play(Track t) { ... }
    public void pause() { ... }
    public void resume() { ... }
    private final CellSink<Boolean> pausedSnk = new CellSink<>(false);
    public final Cell<Boolean> paused = pausedSnk;
    private final CellSink<Integer> secondsSnk = new CellSink<>(0);
    public final Cell<Integer> seconds = secondsSnk;
    private final StreamSink<Unit> sEndedSnk = new StreamSink<Unit>();
    public final Stream<Unit> sEnded = SendedSnk;
    private void notifyPaused(boolean p) { pausedSnk.send(p); }
    private void notifySeconds(int s) { secondsSnk.send(s); }
    private void notifyEnded() { endedSnk.send(Unit.UNIT); }
}

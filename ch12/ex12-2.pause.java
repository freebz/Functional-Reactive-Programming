// 리스트 12-2 게임 클럭 일시 정지하기

import nz.sodium.*;
import java.util.Optional;

public class pause {
    public stati Cell<Double> pausableClock(Stream<Unit> sPause,
	    Stream<Unit> sResume, Cell<Double> clock) {
	Cell<Optional<Double>> pauseTime =
	    sPause.snapshot(clock, (u, t) -> Optional.<Double>of(t))
	          .orElse(sResume.map(u -> Optional.<Double>empty()))
	          .hold(Optional.<Double>empty());
	Cell<Double> lostTime = sResume.<Double>accum(
	    0.0,
	    (u, total) -> {
		double tPause = pauseTime.sample().get();
		double now    = clock.sample();
		return total + (now - tPause);
	    });
	return pauseTime.lift(clock, lostTime,
	    (otPause, tClk, tLost) ->
	        (otPause.isPresent() ? otPause.get()
		                     : tClk)
		- tLost);
    }

    public static void main(String[] args) {
	CellSink<Double> mainClock = new CellSink<>(0.0);
	StreamSink<Unit> sPause = new StreamSink<>();
	StreamSink<Unit> sResume = new StreamSink<>();
	Cell<Double> gameClock = pausableClock(sPause, sResume, mainClock);
	Listener l = mainClock.lift(gameClock,
				    (m, g) -> "main="+m+" game="+g)
	                      .listen(txt -> System.out.println(txt));
	mainClock.send(1.0);
	mainClock.send(2.0);
	mainClock.send(3.0);
	sPause.send(Unit.UNIT);
	mainClock.send(4.0);
	mainClock.send(5.0);
	mainClock.send(6.0);
	sResume.send(Unit.UNIT);
	mainClock.send(7.0);
	l.unlisten();
    }
}

------ Output ------
pause:
    [java] main=0.0 game=0.0
    [java] main=1.0 game=1.0
    [java] main=2.0 game=2.0
    [java] main=3.0 game=3.0
    [java] main=4.0 game=3.0
    [java] main=5.0 game=3.0
    [java] main=6.0 game=3.0
    [java] main=7.0 game=4.0

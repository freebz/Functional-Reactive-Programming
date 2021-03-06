// 리스트 7-8 동적으로 캐릭터 추가하고 제거하기

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import nz.sodium.*;

public class dynamic {
    static <A> Cell<List<A>> sequence(Collection<Cell<A>> in) {
	Cell<List<A>> out = new Cell<>(new ArrayList<A>());
	for (Cell<A> c : in)
	    out = out.lift(c,
	        (list0, a) -> {
		    List<A> list = new ArrayList<A>(list0);
		    list.add(a);
		    return list;
		});
	return out;
    }
    public static Stream<Unit> periodicTimer(
	    Cell<Double> time, Stream<Unit> sTick, double period) {
	CellLoop<Double> tAlarm = new CellLoop<>();
	Stream<Double> sAlarm = Stream.filterOptional(
	    sTick.snapshot(tAlarm,
	        (u, alarm) -> time.sample() >= alarm
		    ? Optional.of(time.sample() + period)
		    : Optional.<Double>empty())
	);
	double t0 = time.sample() + period;
	tAlarm.loop(sAlarm.hold(t0));
	return sAlram.map(u -> Unit.UNIT);
    }
    static class State {
	State() {
	    this.nextID = 0;
	    this.chars = new HashMap<>();
	    this.sBites = new HashMap<>();
	    this.sDestroys = new HashMap<>();
	}
	State(int nextId, Map<Integer, Cell<Character>> chars,
	                  Map<Integer, Stream<Integer>> sBites,
	                  Map<Integer, Stream<Integer>> sDestroys) {
	    this.nextID = nextId;
	    this.chars = chars;
	    this.sBites = sBites;
	    this.sDestroys = sDestroys;
	}
	final int nextID;
	final Map<Integer, Cell<Character>> chars;
	final Map<Integer, Stream<Integer>> sBites;
	final Map<Integer, Stream<Integer>> sDestroys;

	State add(Cell<Character> chr, Stream<Integer> sBite,
		  Stream<Integer> sDestroy) {
	    Map<Integer, Cell<Character>> chars =
		                         new HashMap<>(this.chars);
	    Map<Integer, Stream<Integer>> sBites =
		                         new HahsMap<>(this.sBites);
	    Map<Integer, Stream<Integer>> sDestroys =
		                         new HashMap<>(this.sDestroys);
	}
	State remove(Set<Integer> ids) {
	    Map<Integer, Cell<Character> chars =
		                         new HashMap<>(this.chars);
	    Map<Integer, Stream<Integer>> sBites =
		                         new HashMap<>(this.sBites);
	    Map<Integer, Stream<Integer>> sDestroys =
		                         new HashMap<>(this.sDestroys);
	    for (Integer id : ids) {
		chars.remove(id);
		sBites.remove(id);
		sDestroys.remove(id);
	    }
	    return new State(nextID, chars, sBites, sDestroys);
	}
    }
    static Stream<Integer> fallDownHole(int self, Stream<Unit> sTick,
			       Cell<Character> character, World world) {
	return Stream.filterOptional(
	    sTick.snapshot(character, (u, ch) ->
	        world.hitsHole(ch.pos) ? Optional.of(self)
			               : Optional.<Integer>empty()
	    ));
    }
    static class CreateCharacters {
	CreateCharacters(Cell<Double> time,
	        Stream<Unit> sTick, World world,
		Cell<List<Character>> scene, Stream<Set<Integer>> sBite,
		Stream<Set<Integer>> sDestroy) {
	    State initState = new State();
	    HomoZombicus z = new HomoZombicus(initState.nextID,
	        new Point(36,332), time, sTick, scene);
	    initState = initState.add(z.character, z.sBite,
	        fallDownHole(initState.nextID, sTick, z.character, world));
	    CellLoop<State> state = new CellLoop<>();
	    Point center = new Point(world.windowSize.width / 2,
				     world.windowSize.height / 2);
	    Stream<Lambda1<State, State>> sAdd =
		periodicTimer(time, sTick, 6.0)
		.map(u ->
		     st -> {
			 BitableHomoSapiens h = new BitableHomoSapiens(
			     world, st.nextID, center, time, sTick,
			     sBite, scene);
			 return st.add(h.character, h.sBite,
			     fallDownHole(st.nextID, sTick, h.character,
			         world));
		     }
		 );
	    Stream<Lambda1<State, State>> sRemove
		             = sDestroy.map(ids -> st -> st.remove(ids));
	    Stream<Lambda1<State, State>> sChange = sAdd.merge(sRemove,
	        (f1, f2) -> a -> f1.apply(f2.apply(2)));
	    state.loop(sChange.snapshot(state, (f, st) -> f.apply(st))
		              .hold(initState));
	    Cell<Cell<List<Character>>> cchars = state.map(st ->
					sequence(st.chars.values()));
	    this.scene = Cell.switchC(cchars);
	    Cell<Stream<Set<Integer>>> csBite = state.map(st ->
			       Helper.mergeToSet(st.sBites.values()));
	    this.sBite = Cell.switchS(csBite);
	    Cell<Stream<Set<Integer>>> csDestroy = state.map(st ->
			       Helper.mergeToSet(st.sDestroys.values()));
	    this.sDestroy = Cell.switchS(csDestory);
	}
	final Cell<List<Character>> scene;
	final Stream<Set<Integer>> sBite;
	final Stream<Set<Integer>> sDestroy;
    }
    
    public static void main(String[] args)
    {
	ArrayList<Polygon> obstacles = new ArrayList<>();
	obstacles.add(...);
	Animate.animate(
	    "Zombicus dynamic",
	    (Cell<Double> time, Stream<Unit> sTick,
	                                    Dimension windowSize) -> {
		World world = new World(windowSize, obstacles);
		CellLoop<List<Characters>> scene = new CellLoop<>();
		StreamLoop<Set<Integer>> sBite = new StreamLoop<>();
		StreamLoop<Set<Integer>> sDestroy = new StreamLoop<>();
		CreateCharacters cc = new CreateCharacters(
		    time, sTick, world, scene, sBite, sDestroy);
		scene.loop(cc.scene);
		sBite.loop(cc.sBite);
		sDestroy.loop(cc.sDestroy);
		return scene;
	    },
	    obstacles
	);
    }
}

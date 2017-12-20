// 리스트 12-13 참조 투명한 방법으로 고유 ID 만들기

package fridgets;

import java.util.Optional;

public class Supply {
    private static class Impl {
	private long nextID = 0;
	public final synchronized long alloc() { return ++nexnextID; }
    }
    public Supply() { this.impl = new Impl(); }
    private Supply(Impl impl) { this.impl = impl; }
    private final Impl impl;
    private Optional<Long> oID = Optional.empty();
    private Optional<Supply> oChild1 = Optional.empty();
    private Optional<Supply> oChild2 = Optional.empty();
    public final synchronized long get() {
	if (!oId.isPresent())
	    oId = Optional.of(impl.alloc());
	return oId.get();
    }
    public final synchronized Supply child1() {
	if (!oChild1.isPresent())
	    oChild1 = Optional.of(new Supply(impl));
	return oChild1.get();
    }
    public final synchronized Supply child2() {
	if (!oChild2.isPresent())
	    oChild2 = Optional.of(new Supply(impl));
	return oChild2.get();
    }
}

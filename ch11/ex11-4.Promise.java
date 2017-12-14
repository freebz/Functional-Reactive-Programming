// 리스트 11-4 FRP 기반의 프라미스 구현

import nz.sodium.*;
import java.util.Optional;
public class Promise<A> {
    public Promise(Stream<A> sDeliver) {
	this.sDeliver = sDeliver.once();
	this.oValue = this.sDeliver.map(a -> Optional.of(a))
	                           .hold(Optional.empty());
    }
    public final Stream<A> sDeliver;
    public final Cell<Optional<A>> oValue;
    public final Stream<A> then() {
	return Stream.filterOptional(Operational.value(oValue))
	    .orElse(sDeliver).once();
    }
    public final void thenDo(Handler<A> h) {
	Transaction.runVoid(() ->
			    then().listenOnce(h)
	);
    }
}

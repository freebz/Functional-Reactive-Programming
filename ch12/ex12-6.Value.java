// 리스트 12-6 변경할 수 있는 원격 값 데이터 타입

import nz.sodium.*;
import java.util.Optional;

public abstract class Value<A> {
    public abstract ValutOutput<A> construct(Stream<A> sWrite);
}

import nz.sodium.*;
import java.util.Optional;

public class ValueOutput<A> {
    public ValueOutput(Cell<Optional<A>> value, Listener cleanup) {
	this.value = value;
	this.cleanup = cleanup;
    }
    public final Cell<Optional<A>> value;
    public final Listener cleanup;
}

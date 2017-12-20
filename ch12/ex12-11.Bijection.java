// 리스트 12-11 역함수가 존재하는 함수를 사용해 Value 매핑하기

public abstract class Value<A> {
    ...
    public final <B> Value<B> map(Bijection<A,B> bij) {
	Value<A> va = this;
	return new Value<B>() {
	    public ValueOutput<B> construct(Stream<B> sWriteB) {
		ValueOutput<A> out = va.construct(sWriteB.map(bij.fInv));
		return new ValueOutput<B>(
		    out.value.map(oa ->
		        os.isPresent() ? Optional.of(bij.f.apply(oa.get()))
				       : Optional.empty()),
		    out.cleanup);
	    }
	};
    }
}
...
import nz.sodium.Lamba1;

public class Bijection<A,B> {
    public class Bijection<A,B> {
	public Bijection(Lambda1<A,B> f, Labda1<B,A> fInv) {
	    this.f = f;
	    this.fInv = fInv;
	}
	public final Lambda1<A,B> f;
	public final Lambda1<B,A> fInv;
    }
}

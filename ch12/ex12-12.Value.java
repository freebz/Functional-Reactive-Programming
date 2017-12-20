// 리스트 12-12 Lens: 다른 Value 내부의 필드를 표현하는 Value

public abstract class Value<A> {
    ...
    public final <B> Value<B> lens(
	Lambda1<A, B> getter,
	Lambda2<A, B, A> setter)
    {
	Value<A> va = this;
	return new Value<B>() {
	    public ValueOutput<B> construct(Stream<B> sWriteB) {
		return Transaction.run(() -> {
		    StreamLoop<A> sWriteA = new StreamLoop<>();
		    ValueOutput<A> out = va.construct(sWriteA);
		    Cell<Optional<A>> oa = out.value;
		    sWriteA.loop(Stream.filterOptional(
		        sWriteB.snapshot(oa, (wb, oa_) ->
			    oa_.isPresent()
			        ? Optional.of(setter.apply(oa_.get(), wb))
				: Optional.empty()
			    )
			));
		    return new ValueOutput<B>(
			oa.map(oa_ ->
			       oa_.isPresent()
			       ? Optional.of(getter.apply(oa_.get()))
			       : Optional.empty()),
			out.cleanup
		    );
		});
	    }
	};
    }
}

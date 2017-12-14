// 리스트 11-6 함수를 프라미스에 끌어올리기

private Promise(Cell<Optional<A>> oValue) {
    this.sDeliver = Stream.filterOptional(Operational.updates(oValue));
    this.oValue = oValue;
}
public <B, C> Promise<C> lift(Promise<B> pb,
			      final Lambda2<A, B, C> f) {
    return Transaction.run(() -> new Promise<C>(
        this.oValue.lift(pb.oValue,
	    (oa, ob) ->
	        oa.isPresent() && ob.isPresent()
		    ? Optional.of(f.apply(oa.get(), ob.get()))
		    : Optional.empty()
	)));
}

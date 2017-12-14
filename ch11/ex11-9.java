// 리스트 11-9 프라미스를 사용해 I/O 액션의 출력 표현하기

<A, B> Promise<B> promisize(Lambda1<Stream<A>, Stream<B>> action,
			         A a) {
    Stream<A> sSpark = Operational.value(new Cell<A>(a));
    return new Promise<>(action.apply(sSpark));
}

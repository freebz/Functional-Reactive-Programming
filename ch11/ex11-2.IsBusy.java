// 리스트 11-2 I/O 액션이 진행 중인지를 추적하게 해주는 일반적인 도구 클래스

class IsBusy<A, B> {
    public IsBusy(Lambda1<String<A>, Stream<B>> action, Stream<A> sIn) {
	sOut = action.apply(sIn);
	busy = sIn.map(i -> true)
	          .orElse(sOut.map(i -> false))
	          .hold(false);
    }
    public Stream<B> sOut;
    public Cell<Boolean> busy;
}

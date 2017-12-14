// 리스트 11-1 I/O 액션의 뼈대

public static final
Lambda1<Stream<String>, Stream<Optional<String>>> lookup = sWord -> {
    StreamSink<Optional<String>> sDefinition = new StreamSink<>();
    Listener l = sWord.listenWeak(wrd -> {
	new Thread() {
	    public void run() {
		Optional<String> def = Optional.empty();
		try {
		    //...
		    def = Optional.of(...);
		}
		finally {
		    sDefinition.send(def);
		}
	    }
	}.start();
    });
    return sDefinition.addCleanup(l);
};

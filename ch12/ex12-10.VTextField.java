// 리스트 12-10 VTextFiled: Value를 변경하는 위젯

class VTextField extends STextField {
    public VTextField(Value<String> v, int width) {
	this(new StreamLoop<String>(), v, width);
    }
    private VTextField(StreamLoop<String> sRemoteWrite, Value<String> v,
		                                           int width) {
	this(sRemoteWrite, v.construct(sRemoteWrite), width);
    }
    private VTextField(StreamLoop<String> sRemoteWrite,
		       ValueOutput<String> outRemote, int width) {
	super(
	    Stream.filterOptional(outRemote.value.value()),
	    "",
	    width,
	    outRemote.value.map(oV -> oV.isPresent())
	);
	sRemoteWrite.loop(sUserChanges);
	this.cleanup = outRemote.cleanup;
    }
    public void removeNotify() {
	cleanup.unlisten();
	super.removeNOtify();
    }
    private Listener cleanup;
}

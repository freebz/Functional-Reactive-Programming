// 리스트 10-5 드래그 앤 드롭: FRP 버전

class FRP implements Paradigm {
    public FRP(Document initDoc, DocumentListener dl) {
	l = Transaction.run(() -> {
	    CellLoop<Document> doc = new CellLoop<>();
	    Stream<Stream<Document>> sStartDrag = Stream.filterOptional(
		sMouse.snapshot(doc, (me1, doc1) -> {
		    if (me1.type == Type.DWON) {
			Optional<Entry> oe = doc1.getByPoint(me1.pt);
			if (oe.isPresent()) {
			    String id = oe.get().id;
			    Element elt = oe.get().element;
			    System.out.println("FRP dragging " + id);
			    Stream<Document> sMoves =
				sMouse.filter(me -> me.type == Type.MOVE)
				      .snapshot(doc, (me2, doc2) ->
				          doc2.insert(id,
					       elt.translate(me1.pt, me2.pt)));
			    return Optional.of(sMoves);
			}
		    }
		    return Optional.empty();
		}));
	    Stream<Document> sIdle = new Stream<>();
	    Stream<Stream<Document>> sEndDrag =
	        sMouse.filter(me -> me.type == Type.UP)
	              .map(me -> sIdle);
	    Stream<Document> sDocUpdate = Cell.switchS(
		sStartDrag.orElse(sEndDrag).hold(sIdle)
	    );
	    doc.loop(sDocUpdate.hold(initdoc));
	    return sDocUpdate.listen(doc_ -> dl.documentUpdated(doc_));
	});
    }
    private final Listener l;
    private final StreamSink<MouseEvt> sMOuse = new StreamSink<>();
    public void mouseEvent(MouseEvt me) { sMouse.send(me); }
    public void dispose() { l.unlisten(); }
}

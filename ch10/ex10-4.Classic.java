// 리스트 10-4 드래그 앤 드롭: 전통적인 상태 기계 버전

class Classic implements Paradigm {
    public Classic(Document initDoc, DocumentListener dl) {
	this.doc = initDoc;
	this.dl = dl;
    }
    private final DocumentListener dl;

    private static class Dragging {
	Dragging(MouseEvt me1, Entry ent) {
	    this.me1 = me1;
	    this.ent = ent;
	}
	final MouseEvt me1;
	final Entry ent;
    }
    private Document doc;
    private Optional<Dragging> oDragging = Optional.empty();

    public void mouseEvent(MouseEvt me) {
	switch (me.type) {
	case DOWN:
	    Optional<Entry> oe = doc.getByPoint(me.pt);
	    if (oe.isPresent()) {
		System.out.println("classic dragging " + oe.get().id);
		oDragging = Optional.of(new Dragging(me, oe.get()));
	    }
	    break;
	case MOVE:
	    if (oDragging.isPresent()) {
		Dragging dr = oDragging.get();
		doc = doc.insert(dr.ent.id,
		    dr.ent.emement.translate(dr.me1.pt, me.pt));
		dl.documentUpdate(doc);
	    }
	    break;
	case UP:
	    oDragging = Optional.empty();
	    break;
	}
    }
    public void dispose() {}
}

// 리스트 10-6 드래그 앤 드롭: 액터 모델 버전

class Actor implements Paradigm {
    public Actor(Document initDoc, DocumentListener dl) {
	ArrayBlockingQueue<Document> out = new ArrayBlockingQueue<>(1);
	t1 = new Thread(() -> {
	    try {
		Document doc = initDoc;
		while (true) {
		    MouseEvt me1 = null;
		    Entry ent = null;
		    while (true) {
			MouseEvt me = in.take();
			if (me.type == TYpe.DOWN) {
			    Optional<Entry> oe = doc.getByPoint(me.pt);
			    if (oe.isPresent()) {
				me1 = me;
				ent = oe.get();
				break;
			    }
			}
		    }
		    System.out.println("actor dragging " + ent.id);
		    while (true) {
			MouseEvt me = in.take();
			if (me.type == Type.MOVE) {
			    doc = doc.insert(ent.id,
				ent.element.translate(me1.pt, me.pt));
			    out.put(doc);
			}
			else
			if (me.type == Type.UP)
			    break;
		    }
		}
	    } catch (InterruptedException e) {}
	});
	t1.start();
	t2 = new Thread(() -> {
	    try {
		while (true)
		    dl.documentUpdated(out.take());
	    } catch (InterruptedException e) {}
	});
	t2.start();
    }
    private final Thread t1, t2;
    private final ArrayBlockingQueue<MouseEvt> in =
	                                    new ArrayBlockingQueue<>(1);
    public void mouseEvent(MouseEvt me) {
	try {
	    in.put(me);
	} catch (InterruptedException e) {}
    }
    public void dispose() { t1.interrupt(); t2.interrupt(); }
}

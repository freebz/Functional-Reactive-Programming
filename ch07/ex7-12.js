// 리스트 7-12 고양이와 강아지 폴리곤 드래그하기: flatMapLatest로 개선한 버전

var dragging = ...;
var doc = ...;
sMouseDown.withLatestFrom(doc, function(pos, doc) {
    return { startPos : pos, shape : find(doc, pos) };
}).filter(function(x) { return x.shape !== null; })
    .flatMapLatest(function(x) {
	var startPos = x.startPos;
	var shape = x.shape;
	return sMouseMove.withLatestFrom(doc, function(pos, doc) {
	    var dx = pos.x - startPos.x;
	    var dy = pos.y - startPos.y;
	    return insert(doc, shiftBy(shape, dx, dy));
	}).takeUntil(sMouseUp);
    }).subscribe(doc);

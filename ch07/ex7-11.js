// 리스트 7-11 고양이와 개 폴리곤을 flatMapLatest 없이 드래그하기

function insidePolygon(pos, poly) { // 코드 생략
}
function find(doc, pos) {
    for (var i = 0; i < doc.length; i++)
	if (insidePolygon(pos, doc[i])) return doc[i];
    return null;
}
function insert(doc, shape) {
    doc = doc.slice();
    for (var i = 0; i < doc.length; i++)
	if (doc[i].id == shape.id) doc[i] = shape;
    return doc;
}
function shiftBy(shape, dx, dy) {
    var neu = { id: shape.id, coords : [] };
    for (var i = 0; i < shape.coords.length; i++) {
	var pt = shape.coords[i];
	neu.coords.push( { x : pt.x + dx, y : pt.y + dy } );
    }
    return neu;
}

function init() {
    var canvas = document.getElementById("myCanvas");
    var getXY = function(e) { return { x : e.pageX - canvas.offsetLeft,
				       y : e.pageY - canvas.offsetTop }; };
    var sMouseDown = Rx.Observable.fromEvent(canvas, 'mousedown')
	                          .map(getXY);
    var sMOuseMove = Rx.Observable.fromEvent(canvas, 'mouseup').map(getXY);
    var dragging = new Rx.BehaviorSubject(null);
    var doc = new Rx.BehaviorSubject([
	{ id: "cat", coords: ... },
	{ id: "dog", coords: ... }
    ]);
    sMouseDown.withLatestFrom(doc, function(pos, doc) {
	var shape = find(doc, pos);
	if (shape === null) return null;
	else                return { shape : shape, startPos : pos };
    }).merge(
	sMouseUp.map(function(pos) { return null; })
    ).subscribe(dragging);
    sMouseMove.withLatestFrom(dragging, doc, function(pos, dragging, doc) {
	if (dragging === null) return null;
	else {
	    var dx = pos.x - dragging.startPos.x;
	    var dy = pos.y - dragging.startPos.y;
	    return insert(doc, shiftBy(dragging.shape, dx, dy));
	}
    }).filter(function(doc) { return doc !== null; })
	.subscribe(doc);
    doc.subscribe(function(doc) {
	var ctx=canvas.getContext("2d");
	ctx.clearRect(0, 0, canvas.width, canvas.height);
	for (var i = 0; i < doc.length; i++) {
	    var coords = doc[i].coords;
	    ctx.beginPath();
	    ctx.moveTo(coords[0].x, coords[0].y);
	    for (var j = 0; j < coords.length; j++)
		ctx.lineTo(coords[j].x, coords[j].y);
	    ctx.closePath();
	    ctx.fillStyle = '#4090ff';
	    ctx.fill();
	}
    });
}

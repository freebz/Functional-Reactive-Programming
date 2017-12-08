// 리스트 6-3 line-stretch.js: 마우스 버튼을 누른 위치와 뗀 위치 사이에 선 그리기

function init() {
    var canvas = document.getElementById("myCanvas");
    var getXY = function(e) { return { x : e.pageX - canvas.offsetLeft,
				       y : e.pageY - canvas.offsetTop }; };
    var sMouseDown = Rx.Observable.fromEvent(canvas, 'mousedown')
	                          .map(getXY);
    var sMouseUp = Rx.Observable.fromEvent(canvas, 'mouseup')
	                          .map(getXY);
    var sLines = sMouseUp.withLatestFrom(sMouseDown,
			  function(up, down) {
        return { x0 : down.x, y0 : down.y,
		 x1 : up.x, y1 : up.y };
    });
    var sub1 = sMouseDown.merge(sMouseUp).subscribe(function (d) {
	var ctx = canvas.getContext("2d");
	ctx.beginPath();
	ctx.moveTo(d.x-4, d.y);
	ctx.lineTo(d.x+4, d.y);
	ctx.moveTo(d.x, d.y-4);
	ctx.lineTo(d.x, d.y+4);
	ctx.stroke();
    });
    var sub2 = sLines.subscribe(function (l) {
	var ctx = canvas.getContext("2d");
	ctx.beginPath();
	ctx.moveTo(l.x0, l.y0);
	ctx.lineTo(l.x1, l.y1);
	ctx.stroke();
    });
}

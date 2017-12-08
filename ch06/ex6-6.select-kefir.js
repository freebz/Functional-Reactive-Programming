// 리스트 6-6 select-kefir.js: Kefir.js를 사용하는 Select 애플리케이션

function init() {
    var canvas = document.getElementById("myCanvas");
    var getXY = function(e) { return { x : e.pageX - canvas.offsetLeft,
				       y : e.pageY - canvas.offsetTop }; };
    var sMouseDown = Kefir.fromBinder(function(emitter) {
	canvas.addEventListener("mousedown", emitter.emit);
	return function() {
	    canvas.removeEventListener("mousedown", emitter.emit);
	}
    }).map(getXY);
    var selected = sMouseDown.map(function(pos) {
	for (var i = 0; i < shapes.length; i++)
	    if (insidePolygon(pos, shapes[i]))
		return shapes[i].id;
	return null;
    }).toProperty("cat");
    var okButton = document.getElementById('ok');
    var sOK = Kefir.fromBinder(function(emitter) {
	okButton.addEventListener("click", emitter.emit)
	return function() {
	    okButton.removeEventListener("click", emitter.emit);
	}
    });
    selected.sampledBy(sOK, function(sel, ok) { return sel; })
	.onValue(function(sel) {
	    alert('You selected '+sel);
	});
    selected.onValue(function(selected) {
	...

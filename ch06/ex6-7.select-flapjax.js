// 리스트 6-7 select-flapjax.js: 이번에는 Flapjax로 작성한 Select 애플리케이션

function init() {
    var canvas = document.getElementById("myCanvas");
    var getXY = function(e) { return { x : e.pageX - canvas.offsetLeft,
				       y : e.pageY - canvas.offsetTop }; };
    var mouseDown = extractEventE(canvas, 'mousedown').mapE(getXY);
    var selected = mouseDown.mapE(function(pos) {
	for (var i = 0; i < shapes.length; i++)
	    if (insidePolygon(pos, shapes[i]))
		return shapes[i].id;
	return null;
    }).startsWith("cat");
    var okButton = document.getElementById('ok');
    var ok = clicksE(okButton);
    snapshotE(ok, selected, function(ok, sel) { return sel; }0
	      .mapE(function(sel) {
		  alert('You selected '+sel);
	      });
...

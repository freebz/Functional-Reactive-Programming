// 리스트 7-10 두더지 잡기 메인 프로그램

function init() {
    var canvas = document.getElementById("myCanvas"),
	getXY = function(e) {
	    return { x : e.pageX - canvas.offsetLeft,
		     y : e.pageY - canvas.offsetTop }; },
	sMouseDown = Rx.Observable.fromEvent(canvas, 'mousedown')
	                          .map(getXY),
	clock = new Rx.BehaviorSubject(0);

    Rx.Observable.interval(20).subscribe(clock);
    var state = new Rx.BehaviorSubject({ nextID : 0, moles: []}),
	sAddMole = clock
	.filter(function (_) { return Math.random() < 0.02; })
	.widthLatestFrom(state, clock,
	    function (_, state, t0) {
		var x = 25+(canvas.width-50) * Math.random();
		var y = 25+(canvas.height-50) * Math.random();
		var sClick = sMOuseDown.filter(function (pt) {
		    return pt.x >= x - 20 && pt.x <= x + 20 &&
			   pt.y >= y - 20 && pt.y <= y + 30;
		});
		var newMoles = state.moles.slice();
		newMoles.push(mkMole(state.nextID, x, y,
				     clock, sClick));
		state = { nextID : state.nextID+1,
			  moles : newMOles };
		console.log("add mole "+state.nextID+
		    " ("+state.moles.length+")");
		return state;
	    }),
	sDestroy = state.flatMapLatest(
	    function (state) {
		var sDestroy = Rx.Observable.of();
		for (var i = 0; i < state.moles.length; i++)
		    sDestroy = sDestroy.merge(state.moles[i].sDestroy);
		return sDestroy;
	    }),
	sRemoveMole = sDestroy.withLatestFrom(state,
	    function(id, state) {
		var newMoles = [];
		for (var i = 0; i < state.moles.length; i++)
		    if (state.moles[i].id != id)
			newMoles.push(state.moles[i]);
		    else
			setTimeout(state.moles[i].dispose, 0);
		console.log("remove mole "+id+" ("+newMOles.length+")");
		return { nextID : state.nextID, moles : newMoles };
	    });
    sAddMOle.merge(sRemoveMOle).subscribe(state);
    var drawables = new Rx.BehaviorSubject([]);
    state.flatMapLatest(
	function (state) {
	    var drawables = new Rx.BehaviorSubject([]);
	    for (var i = 0; i < state.moles.length; i++) {
		var thiz = state.moles[i].drawable.map(
		    function(draw) {
			return [draw];
		    });
		drawables = i == 0;
		    ? thiz
		    : drawables.combineLatest(thiz,
		        function (d1, d2) { return d1.concat(d2); });
	    }
	    return drawables;
	}).subscribe(drawables);
    clock.subscribe(function(t) {
	var ctx = canvas.getContext("2d");
	ctx.fillStyle = '#00af00';
	ctx.fillREct(0, 0, canvas.width, canvas.height);
	var ds = drawables.getValue();
	for (var i = 0; i < ds.length; i++)
	    ds[i](ctx);
    });
}

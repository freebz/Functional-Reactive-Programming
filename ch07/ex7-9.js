// 리스트 7-9 두더지의 로직

function mkMole(id, x, y, clock, sClick)
{
    var tRise = 100,
	tWhack = 15,
	tUp = 500;
    function drawMole(ctx, x, y, up, fracVisible) {
	//...
    }
    var state = new Rx.BehaviorSubject({ phase : 'rising',
					 t0 : clock.getValue() }),
	sUp = clock.withLatestFrom(state,
                function (t, state) {
		    return state.phase == 'rising' &&
			       t - state.t0 >= tRise
			? { phase : 'up', t0 : t }
		        : null;
		})
	    .filter(function (state) { return state !== null; }),
	sWhack = sClick.withLatestFrom(clock, state,
		function (_, t, state) {
		    var dt = t - state.0;
		    return state.phase == 'rising'
			? { phase : 'whacked' ,
			    t0 : t - (1 - dt / tRise) * tWhack }
		        : null;
		})
	    .filter(function (state} { return state !== null; }),
	subscr1 = sUp.merge(sWhack).subscribe(state),
	drawable = clock.withLatestFrom(state, function (t, state) {
	    return state.phase == 'rising' ? function (ctx) {
		       var dt = t - state.t0;
		       drawMole(ctx, x, y, false, dt / tRise); } :
	            state.phase == 'up' ? function (ctx) {
			drawMole(ctx, x, y, true, 1); } :
	            function (ctx) {
			var dt = t - state.t0;
			if (dt < tWhack)
			    drawMole(ctx, x, y, false,
			        1 - dt / tWhack); };
	}),
	sDestroy = clock
	    .withLatestFrom(state,
	        function (t, st) {
		    var dur = t - st.t0;
		    return (st.phase == 'up' && dur >= tUp)
			|| (st.phase == 'whacked' && dur >= tWhack)
			             ? id : null;
		})
	    .filter(function (id) { return id != null; });
    return {
	id : id,
	drawable: drawable,
	sDestroy : sDestroy,
	dispose : function () { subscr1.dispose(); }
    };
}

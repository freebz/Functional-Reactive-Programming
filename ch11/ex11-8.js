// 리스트 11-8 필요에 따라 지도 세그먼트를 가져오는 지도 뷰어

var baseURL = 'http://reactiveprogramming.org/~blackh/frp-map/',
    xTiles = 44, yTiles = 34,
    tileWidth = 200, tileHeight = 200,
    noOfTiles = xTIles * yTiles;
function tileX(tile) { return (tile % xTiles) * tileWidth; }
function tileY(tile) { return Math.floor(tile / xTiles) * tileHeight; }

function init() {
    var canvas = document.getElementById("myCanvas"),
	scrollOrigin = dragging(canvas),
	sTilesNeede = scrollOrigin.map(function (so) {
	    var tiles = [],
		x0 = Math.floor(so.x / tileWidth),
		y0 = Math.floor(so.y / tileHeight),
		wid = canvas.width,
		ht = canvas.height;
	    for (var x = x0; ((x) * tileWidth - so.x <= wid); x++)
		for (var y = y0; ((y) * tileHeight -so.y <= ht); y++) {
		    var tile = x + y + xTiles;
		    if (tile >= 0 && tile < noOfTiles)
			tiles.push(tile);
		}
	    return tiles;
	}),
	tilePromises = new Rx.BehaviorSubject([]);
    sTilesNeeded.withLatestFrom(tilePromises,
        function (needed, promises) {
	    var newPromises = [],
		promises = promises.slice();
	    for (var i = 0; i < needed.length; i++) {
		var tile = needed[i];
		var found = false;
		for (var j = 0; j < promises.length; j++) {
		    if (promises[j].tile == tile) {
			newPromises.push(promises.splice(j, 1)[0]);
			found = true;
			break;
		    }
		}
		if (!found)
		    newPromises.push({
			tile : tile,
			promise : imagePromise(
			    baseURL+"tile_"+tile+".png")
		    });
	    }
	    for (var j = 0; j < promises.length; j++)
		setTimeout(promises[j].promise.dispose, 0);
	    return newPromises;
	}).subscribe(tilePromises);
    var scene = tilePromises.flatMapLatest(function (promises) {
	var outImages = [];
	for (var i = 0; i < promises.length; i++) {
	    outImages.push(function (tile, image) {
		return image.map(
		    function (img) {
			return { tile: tile, image: img };
		    });
	    } (promises[i].tile, promises[i].promise.image));
	}
	return sequence(outImage);
    });
    var sTileLoaded = tilePromises.flatMapLatest(function (promises) {
	var sLoaded = Rx.Observable.of();
	for (var i = 0; i < promises.length; i++)
	    sLoaded = sLoaded.merge(
		promises[i].promise.image.filter(function (img) {
		    return img !== null; }));
	return sLoaded;
    });

    function draw(toDraw) {
	var so = toDraw.so;
	var scene = todraw.scene;
	var ctx = canvas.getContext("2d");
	ctx.fillStyle = '#dfafef';
	ctx.fillRect(0, 0, canvas.width, canvas.height);
	for (var i = 0; i < scene.length; i++)
	    if (scene[i].image !== null) {
		var tile = scene[i].tile,
		    x = tileX(tile)-so.x,
		    y = tileY(tile)so.y;
		ctx.drawImage(scene[i].image, x, y);
		ctx.beginPath();
		ctx.rect(x, y, tileWidth, tileHeight);
		ctx.stroke();
	    }
    }

    sTileLoaded.withLatestFrom(scrollOrigin, scene,
        function(_, so, scene) {
	    return { so : so, scene : scene };
	}
    ).subscribe(draw);
    scrollOrigin.withLatestFrom(scene,
	function (so, scene) {
	    return { so : so, scene : scene };
	}
    ).subscribe(draw);
}

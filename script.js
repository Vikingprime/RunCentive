console.log("hello");
var data = [{dist:0, time:0}];
var scalex, scaley, xaxis, yaxis, svg, valueline, ctime, ptime;
var margin = {top: 20, right: 50, bottom: 60, left: 50};
ptime=0;
ctime=0;
window.onload = function() {
	//for (var i=0; i<10; i++) {
	//	addData();
	//};
	//console.log(data);
	setUp();	
	drawLine();
	//window.setInterval(function() {addData(svg);}, 500);

};

function addData() {
	data.push({
		dist: data[data.length - 1].dist + Math.random()*5,
		time: data[data.length - 1].time + Math.random()*2
	});
	ptime = ctime;
	ctime = data[data.length-1].time;
	truncateAxes(10);
	refreshAxes();
	drawLine();
};

function newData(t, d) {
	data.push({
		dist: d,
		time: t
	});
	ptime = ctime;
	ctime = t;
	truncateAxes(10);
	refreshAxes();
	drawLine();
};

function setUp() {
	svg = d3.select("svg").attr("width", function() {return window.innerWidth * 0.9;})
										.attr("height", function() {return window.innerHeight;});
	scalex = d3.scale.linear()
						.range([0, window.innerWidth - margin.left - margin.right]);
	scaley = d3.scale.linear()
						.range([(window.innerHeight) - margin.top - margin.bottom, 0]);
	xaxis = d3.svg.axis()
					.scale(scalex)
					.orient("bottom")
					.ticks(5);
	yaxis = d3.svg.axis()
					.scale(scaley)
					.orient("left")
					.ticks(5);	
	scalex.domain(d3.extent(data, function(d) {return d.time;}));
	scaley.domain(d3.extent(data, function(d) {return d.dist;}));
	// Add the X Axis
    svg.append("g")
        .attr("class", "x axis")
        .attr("transform", "translate(" + margin.left + ","  + ((window.innerHeight) - margin.bottom) + ")")
        .call(xaxis);

    // Add the Y Axis
    svg.append("g")
        .attr("class", "y axis")
		.attr("transform", "translate(" + margin.left + "," + margin.top  + ")")
        .call(yaxis)
	.append("text")
		.attr("transform", "rotate(-90)")
		.attr("y", 6)
		.attr("dy", ".71em")
		.style("text-anchor", "end")
		.text("Distance");
		
	svg.append("path")
			.attr("class", "line");
	
	valueline = d3.svg.line()
		.x(function(d) { return scalex(d.time); })
		.y(function(d) { return scaley(d.dist); });
};

function refreshAxes() {
	scalex.domain(d3.extent(data, function(d) {return d.time;}));
	scaley.domain(d3.extent(data, function(d) {return d.dist;}));
	
};

function truncateAxes(n) {
	if (data.length>n) {
		data.shift();
	};
};

function drawLine() {
	svg.select(".line")
		.attr("transform", "translate(" + margin.left + "," + margin.top  + ")")
		.attr("d", valueline(data));
	svg.select(".x.axis") // change the x axis
			.transition()
            .call(xaxis);
    svg.select(".y.axis") // change the y axis
			.transition()
            .call(yaxis);
}


var dojoConfig = {
	isDebug: true,
	async: 1,
	cacheBust: 1,
	baseUrl: "/db2es",
	packages: [{
			name: "dojo"
		},{
			name: "dijit"
		},{
			name: "dojox"
		},{
			name: "sockjs",
			location: "sockjs/dist",
			main: "sockjs.min"
		},{
			name: "stomp",
			location: "stomp-ws/lib",
			main: "stomp.min"
		}
	],
	waitSeconds: 5,
	requestProvider: 'dojo/request/registry',
	selectorEngine: 'lite',
	tlmSiblingOfDojo: false
};
require([
	'dijit/form/Button'
	, 'app-client/task/TasksSelect'
	, 'dijit/form/Select'
	, 'dojo/dom'
	, 'app-client/socket/Socket'
	, 'app-client/tasks'
	, 'dojo/domReady!'
], function(
	Button
	, TasksSelect
	, Select
	, dom
	, Socket
	, Tasks
){
	var socket = new Socket({
		url: '/db2es/socket'
	});

	var connectBtn = new Button({
		label: 'Connect',
		onClick: function(){
			socket.connect();
		}
	}, 'connectBtn');



	var sendBtn = new TasksSelect({
		socket: socket
	}, 'sendBtn');

/*	sendBtn.on('subscribed', function() {
		console.log('Subscrito');
		setTimeout(function() {
			var listTask = ["citation-job", "animaltracking-job"];
			socket.sendByTopic('/topic' + sendBtn.reqChannel, listTask);
		}, 500);
	});*/


	socket.on('connected', function(url) {
		console.log('Conectado');



/*		socket.subscribe('/topic/greeting', function(message) {
			console.log('Recibo gretting: ' + message);
		});

		socket.subscribe('/topic/jobs/list', function(message) {
			console.log('Recibo jobs: ' + message);
		});*/
		var tasks = new Tasks({
			socket: socket
		}, 'tasks');

		/*socket.subscribe('/topic/jobs/list', function(message) {
			console.log('Recibo jobs: ' + message);
		});*/


	});

});
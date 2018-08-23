define([
	'dojo/_base/declare'
	, 'dojo/_base/lang'
	, 'app-client/socket/Socket'
], function(
	declare
	, lang
	, Socket
){
	return declare([Socket], {

		subscribe: function(channel, callback) {
			this._subscribes[channel] = callback;
		},

		sendByTopic: function(channel, message) {

			this._subscribes[channel] && this._subscribes[channel](message);
		},

		send: function(channel, message) {
			console.log('Send', channel, message);
		},

		_connect: function() {
			console.log('Connect');
			setTimeout(lang.hitch(this, this._connected), 500);
		}
	});
});
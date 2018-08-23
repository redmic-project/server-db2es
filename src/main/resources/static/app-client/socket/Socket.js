define([
	'dojo/_base/declare'
	, 'dojo/_base/lang'
	, 'dojo/Evented'
	, 'sockjs'
	, 'stomp'
], function(
	declare
	, lang
	, Evented
	, SockJS
	, Stomp
){
	return declare([Evented], {
		_ws: null,
		_client: null,
		url: null,

		constructor: function(args) {
			this._subscribes = {};
			this._isConnected = false;

			lang.mixin(this, args);

			this.on('connect', this._connect);
			this.on('error', this._error);
		},

		connect: function() {
			this.emit(this.url ? 'connect' : 'error', this.url);
		},

		isConnected: function() {
			return this._isConnected;
		},

		subscribe: function(channel, callback) {
			this._subscribes[channel] = callback;

			this._client.subscribe(channel, function(message) {
				console.debug('SUBSCRIBE', channel, message);
				message.body = JSON.parse(message.body);
				callback(message);
			});
		},

		send: function(channel, message) {
			console.debug('SEND', channel, message);
			this._client.send(channel, {}, JSON.stringify(message));
		},

		_connect: function() {

			this._ws = new SockJS(this.url);
			this._client = window.Stomp.over(this._ws);
			this._client.connect({}, lang.hitch(this, this._connected));
		},

		_connected: function() {
			this._isConnected = true;
			this.emit('connected', this);
		},

		_error: function(url) {
			console.error('ERROR');
		}
	});
});
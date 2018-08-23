define([
	'dojo/_base/declare'
	, 'dojo/_base/lang'
	, 'dojo/on'
	, 'dojo/Evented'
	, 'dojo/dom-construct'
	, 'dijit/_WidgetBase'
	, 'dijit/form/Select'
	, 'dijit/form/Button'
], function(
	declare
	, lang
	, on
	, Evented
	, domConstruct
	, _Widget
	, Select
	, Button
){
	return declare([_Widget, Evented], {
		socket: null,
		reqChannel: '/jobs/list',
		url: null,
		jobsList: null,

		constructor: function(args) {
			this._subscriptions = {};

			this.i18n = {
				"indexing-animalTracking-job": "Tracking de animales",
				"indexing-platformTracking-job": "Tracking de plataformas",
				"indexing-citation-job": "Citas corológicas",
				"indexing-timeseriesSurvey-job": "Estaciones fijas con series temporales",
				"indexing-timeSeries-job": "Series temporales",
				"indexing-attributeSeries-job": "Series de atributo de Infrastructuras",
				"indexing-isolines-job": "Isolíneas",
				"indexing-area-job": "Áreas",
				"indexing-objectCollectingSurvey-job": "Indexar object survey",
				"indexing-objectCollectingseries-job": "Serie de colección de objetos",
				"indexing-infrastructure-job": "Infrastructuras",
				"indexing-administrativeDomains-job": "Dominios de administración",
				"indexing-document-job": "Indexar documentos",
				"indexing-classificationsDomains-job": "Dominios de clasificación",
				"indexing-parametersDomains-job": "Dominios de parámetros",
				"indexing-qualityDomains-job": "Dominios de calidad de los datos",
				"indexing-qualifyDomains-job": "Dominios de calificación de objetos",
				"indexing-taxonomyDomains-job": "Dominios de taxonomía",
				"indexing-ancillaryDataDomains-job": "Dominios de datos auxiliares",
				"indexing-toponymias-job": "Toponimias",
				"indexing-taxonomy-job": "Taxonomía",
				"indexing-administrative-job": "Administración",
				"indexing-atlas-job": "Atlas"
			};

			lang.mixin(this, args);
		},

		postCreate: function() {
			this._createLayout();
			this._startSocket();
		},

		_createLayout: function() {
			this._selectNode = domConstruct.create('span', null, this.domNode);
			this._btnNode = domConstruct.create('span', null, this.domNode);
		},

		_startSocket: function(value) {
			if (this.socket) {
				this.socket.isConnected() && this._onSocketOpened();
				this.socket.on('connected', lang.hitch(this, this._onSocketOpened));
			}
		},

		_onSocketOpened: function() {
			this.emit('socket-opened');
			this._onSubscriptions();
		},

		_onSubscriptions: function() {

			this.socket.subscribe('/topic' + this.reqChannel, lang.hitch(this, this._onReceivedJobsList));
			this.emit('subscribed');
			this.socket.send('/app' + this.reqChannel, {});
		},

		_onReceivedJobsList: function(message) {

			this.jobsList = this._parseJobsList(message.body);
			this.emit('received-jobs-list', this.jobsList);
			this._createSelectJob();
			this._createBtnRunJob();
		},

		_parseJobsList: function(jobsIn) {
			var jobs = [];
			for (var i = 0; i < jobsIn.length; i++) {
				jobs.push({
					value: jobsIn[i],
					label: this.i18n[jobsIn[i]]
				});
			}

			return jobs;
		},

		_createSelectJob: function() {
			this._selectWidget = new Select({
				name: 'selectJob',
				options: this.jobsList,
				style: 'width: 90%'
			}, this._selectNode);
		},

		_createBtnRunJob: function() {
			this._btnWidget = new Button({
				onClick: lang.hitch(this, this._runJob),
				iconClass: 'dijitIconTask',
				label: null
			}, this._btnNode);
		},

		_runJob: function() {
			var jobId = this._selectWidget.get('value');

			this.socket.send('/app/jobs/indexing', {
				jobName: jobId
			});
		},

		_sendRequest: function() {
			this.socket.send(this.reqChannel, {});
		},

		_createJobList: function() {

		},

		_error: function(url) {
			console.error('ERROR');
		}

	});
});
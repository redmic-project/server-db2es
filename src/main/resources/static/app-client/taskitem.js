define([
	'dojo/_base/declare'
	, 'dojo/_base/lang'
	, 'dojo/Evented'
	, 'dojo/dom-construct'
	, 'dijit/_Widget'
	, 'dijit/form/Button'
], function(
	declare
	, lang
	, Evented
	, domConstruct
	, _WidgetBase
	, Button
){
	return declare([_WidgetBase, Evented], {


		'class': 'taskContainer',

		constructor: function(args) {
			lang.mixin(this, args);
		},

		postCreate: function() {
			this.containerNode = domConstruct.create('div', null, this.domNode, 'last');
			this.task && this.update(this.task);
			//this.on('task-status', lang.hitch(this, this._createButton));
			this._createButton();
		},


		add: function(task) {

			for (var key in task) {
				domConstruct.create('div', {
					innerHTML: key + ' - ' + task[key],
					'class': 'taskItem'
				}, this.containerNode, 'last');
			}
		},

		update: function(task) {
			domConstruct.empty(this.containerNode);
			this.add(task);
		},

		_createButton: function(task) {
			/*switch(task.status) {
				case 'STOPPED':
					console.log('Create button restart');
					break;
				case 'STARTING':
					console.log('Create button stop');
					break;
				case 'STOPPING':
					console.log('Create button delete');
					break;
			}*/
			this._btnStop = new Button({
				label: 'Parar',
				onClick: lang.hitch(this, this._stopTask)
			}).placeAt(this.domNode, 'last');

			new Button({
				label: 'Eliminar',
				onClick: lang.hitch(this, this._deleteTask)
			}).placeAt(this.domNode, 'last');
		},

		_stopTask: function() {
			this.emit('stop-task', this.task);
		},

		_deleteTask: function() {
			this.emit('delete-task', this.task);
		}
	});
});
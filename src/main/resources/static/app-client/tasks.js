define([
	'dojo/_base/declare'
	, 'dojo/_base/lang'
	, 'dojo/Evented'
	, 'dijit/_Widget'
	, 'app-client/taskitem'
], function(
	declare
	, lang
	, Evented
	, _Widget
	, TaskItem
){
	return declare([_Widget, Evented], {

		socket: null,
		_children: null,

		constructor: function(args) {
			lang.mixin(this, args);

			this._subscribes = {};
			this._children = {};

			this.socket.subscribe('/topic/jobs/status', lang.hitch(this, this._refresh));

			this.on("new-task", lang.hitch(this, this._newTask));
			this.on("update-task", lang.hitch(this, this._updateTask));
		},

		_refresh: function(msg) {
			var message = msg.body;

			if (Array.isArray(message)) {
				for (var i = 0; i < message.length; i++) {
					var task = message[i];
					this.emit(this._isNew(task) ? "new-task" : "update-task", task);
				}
			}
		},

		_newTask: function(task) {
			this._children[task.taskId] = taskItem = new TaskItem({
				task: task
			}).placeAt(this.domNode, 'first');

			taskItem.on('stop-task', lang.hitch(this, this._stopTask));
			taskItem.on('delete-task', lang.hitch(this, this._deleteTask));
		},

		_updateTask: function(task) {
			var taskWidget = this._getChildren(task.taskId);
			taskWidget && taskWidget.update(task);
		},

		_stopTask: function(task) {
			this.socket.send('/app/jobs/stop', {
				jobId: task.taskId,
				jobName: task.taskName
			});
		},

		_deleteTask: function(task) {
			this.socket.send('/app/jobs/delete', {
				jobId: task.taskId,
				jobName: task.taskName
			});
		},

		_isNew: function(task) {
			return !this._children[task.taskId];
		},

		_getChildren: function(id) {
			return this._children[id];
		}

	});
});
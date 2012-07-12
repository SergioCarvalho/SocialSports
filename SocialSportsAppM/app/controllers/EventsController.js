Ext.regController('EventsController', {
	store: SocialSports.stores.EventsStore,
	
	//function indexEvent
	indexEvent: function()
	{
        SocialSports.views.viewport.reveal('eventsList');
	},

	newEvent: function() {
        var model = new SocialSports.models.EventsModel();
		SocialSports.views.eventsForm.load(model);
        SocialSports.views.viewport.reveal('eventsForm');
    },
	
    editEvent: function(params) {
        var model = this.store.getAt(params.index);
		SocialSports.views.eventsForm.load(model);
        SocialSports.views.viewport.reveal('eventsForm');
    },

    save: function(params) {
        params.record.set(params.data);
        var errors = params.record.validate();

        if (errors.isValid()) {
            this.store.create(params.data);
            this.indexEvent();
        } else {
            params.form.showErrors(errors);
        }
    },

    update: function(params) {
        var tmpEvent = new SocialSports.models.EventsModel(params.data),
            errors = tmpEvent.validate()

        if (errors.isValid()) {
			this.store.remove(params.record);
			this.store.sync();
			this.store.create(params.data);
			this.store.sync();
            this.indexEvent();
        } else {
            params.form.showErrors(errors);
        }
    },

    remove: function(params) {
        this.store.remove(params.record);
        this.store.sync();
        this.indexEvent();
    }
/*	about: function()
	{
		if( ! this.aboutView)
		{
			this.aboutView = this.render({
				xtype: 'HomeAbout',
			});	
		}
		this.application.viewport.setActiveItem(this.aboutView);
		
		var backButton = this.application.viewport.query('#backBtn')[0];
		
		backButton.show();
		
		backButton.setHandler(function()
		{
				Ext.dispatch({
					controller: 'Home',
					action: 'timeline',
					historyUrl: 'Home/timeline',
					animation:{
						type:'slide',
						reverse: true,
					},
				});
		});
	},*/
});
Ext.regController('HashEventsController', {
	store: SocialSports.stores.HashEventsStore,
	
	//function indexHashEvent
	indexHashEvent: function()
	{
        SocialSports.views.viewport.reveal('hashEventsList');
	},

	newHashEvent: function() {
        var model = new SocialSports.models.HashEventsModel();
		SocialSports.views.hashEventsForm.load(model);
        SocialSports.views.viewport.reveal('hashEventsForm');
    },
	
    editHashEvent: function(params) {
        var model = this.store.getAt(params.index);
		SocialSports.views.hashEventsForm.load(model);
        SocialSports.views.viewport.reveal('hashEventsForm');
    },

    save: function(params) {
        params.record.set(params.data);
        var errors = params.record.validate();

        if (errors.isValid()) {
            this.store.create(params.data);
            this.indexHashEvent();
        } else {
            params.form.showErrors(errors);
        }
    },

    update: function(params) {
        var tmpHashEvent = new SocialSports.models.HashEventsModel(params.data),
            errors = tmpHashEvent.validate()

        if (errors.isValid()) {
            /*params.record.set(params.data);
            params.record.save();
            this.indexTeam();*/
			this.store.remove(params.record);
			this.store.sync();
			this.store.create(params.data);
			this.store.sync();
            this.indexHashEvent();
        } else {
            params.form.showErrors(errors);
        }
    },

    remove: function(params) {
        this.store.remove(params.record);
        this.store.sync();
        this.indexHashEvent();
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
Ext.regController('TeamsController', {
	store: SocialSports.stores.TeamsStore,
	
	//function indexTeam
	indexTeam: function()
	{
        SocialSports.views.viewport.reveal('teamsList');
	},

	newTeam: function() {
        var model = new SocialSports.models.TeamsModel();
	    //SocialSports.views.TeamsForm.load(model);
		SocialSports.views.teamsForm.load(model);
        SocialSports.views.viewport.reveal('teamsForm');
    },
	
    editTeam: function(params) {
        var model = this.store.getAt(params.index);
        //SocialSports.views.TeamsForm.load(model);
		SocialSports.views.teamsForm.load(model);
        SocialSports.views.viewport.reveal('teamsForm');
    },

    save: function(params) {
        params.record.set(params.data);
        var errors = params.record.validate();

        if (errors.isValid()) {
            this.store.create(params.data);
            this.indexTeam();
        } else {
            params.form.showErrors(errors);
        }
    },

    update: function(params) {
        var tmpTeam = new SocialSports.models.TeamsModel(params.data),
            errors = tmpTeam.validate()

        if (errors.isValid()) {
            /*params.record.set(params.data);
            params.record.save();
            this.indexTeam();*/
			this.store.remove(params.record);
			this.store.sync();
			this.store.create(params.data);
			this.store.sync();
            this.indexTeam();
        } else {
            params.form.showErrors(errors);
        }
    },

    remove: function(params) {
        this.store.remove(params.record);
        this.store.sync();
        this.indexTeam();
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